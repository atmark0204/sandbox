package org.ramidore.logic.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ramidore.bean.GvLogTable;
import org.ramidore.bean.GvTimelineChartBean;
import org.ramidore.core.PacketData;
import org.ramidore.util.RedomiraUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GVの得点状況を解析する.
 *
 * @author atmark
 *
 */
public class GuildBattleLogic extends AbstractSystemMessageLogic {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GuildBattleLogic.class);

    /**
     * 得点情報にマッチするパターン.
     *
     * ※1パケット中に複数回現れる場合がある
     */
    private static final String UNIT_PATTERN = "380069120000(..)00(....)0000(....)0000(....)0000" + BASE_PATTERN + "00(?:CC)+" + BASE_PATTERN + "00(?:CC)+";

    /**
     * パケット全体にマッチする正規表現パターン.
     *
     */
    private static final String PATTERN = "^(?:.{2})+(" + UNIT_PATTERN + ")(?:.{2})*$";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern unitPattern = Pattern.compile(UNIT_PATTERN);

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * 開始時刻.
     */
    private Date startDate = null;

    /**
     * 重複チェッカー.
     */
    private DuplicateChecker dupChecker;

    /**
     * ポイントチェッカー.
     */
    private PointChecker pointChecker;

    /**
     * チャート表示用のデータを流し込むキュー.
     */
    private ConcurrentLinkedQueue<GvTimelineChartBean> timelineChartDataQ;

    /**
     * ログデータを流し込むキュー.
     *
     * コントローラ側で統計処理する
     */
    private ConcurrentLinkedQueue<GvLogTable> logDataQ;

    /**
     * 時刻のフォーマット.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * コンストラクタ.
     */
    public GuildBattleLogic() {

        dupChecker = new DuplicateChecker();
        pointChecker = new PointChecker();

        timelineChartDataQ = new ConcurrentLinkedQueue<GvTimelineChartBean>();
        logDataQ = new ConcurrentLinkedQueue<GvLogTable>();

        reset();
    }

    /**
     * 必要オブジェクトをリセットする.
     */
    public void reset() {

        startDate = null;

        timelineChartDataQ.clear();
        logDataQ.clear();
    }

    @Override
    public boolean execute(PacketData data) {

        if (startDate == null) {
            // 最初に受信したパケットのタイムスタンプを開始時刻とする
            startDate = data.getDate();
            setStart();
        }

        Matcher matcher = pattern.matcher(data.getStrData());

        if (matcher.matches()) {

            Matcher unitMatcher = unitPattern.matcher(data.getStrData());

            while (unitMatcher.find()) {

                int order = RedomiraUtil.intValueFromAscHexString(unitMatcher.group(1));
                int point = RedomiraUtil.intValueFromDescHexString(unitMatcher.group(2));
                int point0 = RedomiraUtil.intValueFromDescHexString(unitMatcher.group(3));
                int point1 = RedomiraUtil.intValueFromDescHexString(unitMatcher.group(4));
                String srcName = RedomiraUtil.encode(unitMatcher.group(5), ENCODING);
                String dstName = RedomiraUtil.encode(unitMatcher.group(6), ENCODING);

                if (!dupChecker.check(point0, point1)) {

                    continue;
                }

                // ログ
                GvLogTable logRow = new GvLogTable();

                logRow.setDate(DATE_FORMAT.format(data.getDate()));
                logRow.setSrcCharaName(srcName);
                logRow.setDstCharaName(dstName);
                logRow.setGuildName(order);
                logRow.setPoint(point);
                logRow.setPoint0(point0);
                logRow.setPoint1(point1);

                // 時系列チャート
                GvTimelineChartBean timelineBean = new GvTimelineChartBean();
                timelineBean.setDate(DATE_FORMAT.format(data.getDate()));
                timelineBean.setSeries(order);
                if (order == 0) {
                    timelineBean.setPoint(point0);
                } else if (order == 1) {
                    timelineBean.setPoint(point1);
                }
                timelineChartDataQ.add(timelineBean);

                // 統計
                logDataQ.add(logRow);

                writeLog(logRow);

                pointChecker.add(logRow.getGuildName(), logRow.getPoint());
                pointChecker.check(logRow.getPoint0(), logRow.getPoint1());
            }

            return true;
        }

        //LOG.info(DebugUtil.hexDump(data));

        return false;
    }

    public void setStart() {

        // x = 0, y = 0のデータを生成
        GvTimelineChartBean startTimeline0 = new GvTimelineChartBean();
        startTimeline0.setDate(DATE_FORMAT.format(startDate));
        startTimeline0.setSeries(0);

        timelineChartDataQ.add(startTimeline0);

        GvTimelineChartBean startTimeline1 = new GvTimelineChartBean();
        startTimeline1.setDate(DATE_FORMAT.format(startDate));
        startTimeline1.setSeries(1);

        timelineChartDataQ.add(startTimeline1);

        // 開始時刻を書き込む
        LOG.info(DATE_FORMAT.format(startDate));
    }

    /**
     * 過去ログを読み込む.
     *
     * @param absolutePath
     */
    public void loadPastData(String absolutePath) {

        LOG.trace("load " + absolutePath);

        reset();

        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(absolutePath));

            List<String> list = IOUtils.readLines(br);

            // x = 0, y = 0のデータを追加
            GvTimelineChartBean startTimeline0 = new GvTimelineChartBean();
            startTimeline0.setDate(list.get(0));
            startTimeline0.setSeries(0);

            timelineChartDataQ.add(startTimeline0);

            GvTimelineChartBean startTimeline1 = new GvTimelineChartBean();
            startTimeline1.setDate(list.get(0));
            startTimeline1.setSeries(1);

            timelineChartDataQ.add(startTimeline1);

            for (int i = 1; i < list.size(); i++) {
                GvLogTable logRow = loadLine(list.get(i));

                if (logRow == null) {
                    continue;
                }

                logDataQ.add(logRow);

                GvTimelineChartBean timelineBean = new GvTimelineChartBean();
                timelineBean.setDate(logRow.getDate());
                timelineBean.setSeries(logRow.getGuildName());
                if (logRow.getGuildName() == 0) {
                    timelineBean.setPoint(logRow.getPoint0());
                } else if (logRow.getGuildName() == 1) {
                    timelineBean.setPoint(logRow.getPoint1());
                }
                timelineChartDataQ.add(timelineBean);
            }

        } catch (FileNotFoundException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        } finally {
            IOUtils.closeQuietly(br);
        }

    }

    /**
     * ログ1行を読み込む
     *
     * @param line 文字列
     * @return GvLogTable
     */
    private GvLogTable loadLine(String line) {

        String[] elem = StringUtils.split(line, '\t');

        if (elem.length != 7) {

            return null;
        }

        GvLogTable row = new GvLogTable();

        row.setDate(elem[0]);
        row.setSrcCharaName(elem[1]);
        row.setDstCharaName(elem[2]);
        row.setGuildName(Integer.valueOf(elem[3]));
        row.setPoint(Integer.valueOf(elem[4]));
        row.setPoint0(Integer.valueOf(elem[5]));
        row.setPoint1(Integer.valueOf(elem[6]));

        return row;
    }

    private void writeLog(GvLogTable row) {

        LOG.info(row.getDate() + "\t" + row.getSrcCharaName() + "\t" + row.getDstCharaName() + "\t" + row.getGuildName() + "\t" + row.getPoint() + "\t" + row.getPoint0() + "\t" + row.getPoint1());
    }

    /**
     * 重複チェッカ.
     */
    private class DuplicateChecker {
        private static final String FORMAT = "%d:%d";
        private int p1;
        private int p2;
        private Set<String> set = new HashSet<String>();

        public DuplicateChecker() {
            p1 = 0;
            p2 = 0;
            set.add(String.format(FORMAT, p1, p2));
        }

        public boolean check(int p1, int p2) {

            String val = String.format(FORMAT, p1, p2);

            if (set.contains(val)) {
                return false;
            } else {
                set.add(val);
                return true;
            }
        }
    };

    /**
     * ポイントのズレをチェック.
     *
     * @author atmark
     *
     */
    private class PointChecker {

        private int p0;
        private int p1;

        private int diff0;
        private int diff1;

        public PointChecker() {

            p0 = 0;
            p1 = 0;
            diff0 = 0;
            diff1 = 0;
        }

        public void add(int order, int p) {

            if (order == 0) {
                this.p0 += p;
            } else if (order == 1) {
                this.p1 += p;
            }
        }

        public void check(int p0, int p1) {

            if (this.p0 - p0 != diff0) {
                diff0 = this.p0 - p0;
                LOG.warn("先入れ側点数にズレが発生 : " + diff0);
            }
            if (this.p1 - p1 != diff1) {
                diff1 = this.p1 - p1;
                LOG.warn("後入れ側点数にズレが発生 : " + diff1);
            }
        }
    }
    /**
     * getter.
     *
     * @return timelineChartDataQ
     */
    public ConcurrentLinkedQueue<GvTimelineChartBean> getTimelineChartDataQ() {
        return timelineChartDataQ;
    }

    /**
     * setter.
     *
     * @param timelineChartDataQ
     *            セットする timelineChartDataQ
     */
    public void setTimelineChartDataQ(ConcurrentLinkedQueue<GvTimelineChartBean> timelineChartDataQ) {
        this.timelineChartDataQ = timelineChartDataQ;
    }

    /**
     * getter.
     *
     * @return logDataQ
     */
    public ConcurrentLinkedQueue<GvLogTable> getLogDataQ() {
        return logDataQ;
    }

    /**
     * setter.
     *
     * @param logDataQ
     *            セットする logDataQ
     */
    public void setLogDataQ(ConcurrentLinkedQueue<GvLogTable> logDataQ) {
        this.logDataQ = logDataQ;
    }
}
