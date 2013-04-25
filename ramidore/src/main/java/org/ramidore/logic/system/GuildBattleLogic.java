package org.ramidore.logic.system;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
import org.ramidore.core.PacketData;
import org.ramidore.util.RamidoreUtil;
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

        logDataQ = new ConcurrentLinkedQueue<GvLogTable>();

        reset();
    }

    /**
     * 必要オブジェクトをリセットする.
     */
    public void reset() {

        startDate = null;

        logDataQ.clear();
    }

    @Override
    public boolean execute(PacketData data) {

        if (startDate == null) {
            // 最初に受信したパケットのタイムスタンプを開始時刻とする
            startDate = data.getDate();

            // 0 vs 0のデータ追加
            GvLogTable log0 = new GvLogTable();
            log0.setDate(DATE_FORMAT.format(startDate));

            logDataQ.add(log0);

            // 開始時刻を書き込む
            LOG.info(DATE_FORMAT.format(startDate));
        }

        Matcher matcher = pattern.matcher(data.getStrData());

        if (matcher.matches()) {

            Matcher unitMatcher = unitPattern.matcher(data.getStrData());

            while (unitMatcher.find()) {

                int order = RamidoreUtil.intValueFromAscHexString(unitMatcher.group(1));
                int point = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(2));
                int point0 = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(3));
                int point1 = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(4));
                String srcName = RamidoreUtil.encode(unitMatcher.group(5), ENCODING);
                String dstName = RamidoreUtil.encode(unitMatcher.group(6), ENCODING);

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

                // 統計
                logDataQ.add(logRow);

                LOG.info(logRow.toLogFormat());

                pointChecker.check(logRow);
            }

            return true;
        }

        //LOG.info(DebugUtil.hexDump(data));

        return false;
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
            br = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath), ENCODING));

            List<String> list = IOUtils.readLines(br);

            // 0 vs 0のデータ追加
            GvLogTable log0 = new GvLogTable();
            log0.setDate(list.get(0));

            logDataQ.add(log0);

            for (int i = 1; i < list.size(); i++) {
                GvLogTable logRow = loadLine(list.get(i));

                if (logRow == null) {
                    continue;
                }

                logDataQ.add(logRow);
            }

        } catch (Exception e) {
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

    /**
     * 重複チェッカ.
     */
    private class DuplicateChecker {
        private static final String FORMAT = "%d:%d";
        private Set<String> set = new HashSet<String>();

        public DuplicateChecker() {
            set.add(String.format(FORMAT, 0, 0));
        }

        public boolean check(int p1, int p2) {
            String val = String.format(FORMAT, p1, p2);
            if (set.contains(val)) {
                LOG.warn("重複した点数情報を受信 : " + p1 + " - " + p2);
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

        private int[] p;
        private int[] diff;

        public PointChecker() {
            p = new int[]{0, 0};
            diff = new int[]{0, 0};
        }

        public void check(GvLogTable t) {

            this.p[t.getGuildName()] += t.getPoint();

            if (this.p[0] - t.getPoint0() != diff[0]) {
                diff[0] = this.p[0] - t.getPoint0();
                LOG.warn("先入れ側点数にズレが発生 : " + diff[0]);
            }
            if (this.p[1] - t.getPoint1() != diff[1]) {
                diff[1] = this.p[1] - t.getPoint1();
                LOG.warn("後入れ側点数にズレが発生 : " + diff[1]);
            }
        }
    }

    /**
     * getter.
     *
     * @return logDataQ
     */
    public ConcurrentLinkedQueue<GvLogTable> getLogDataQ() {
        return logDataQ;
    }
}
