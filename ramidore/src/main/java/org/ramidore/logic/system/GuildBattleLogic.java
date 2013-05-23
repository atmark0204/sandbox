package org.ramidore.logic.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.ramidore.Const;
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
    private static final String UNIT_PATTERN = "380069120000(..)00(....)0000(....)0000(....)0000" + Const.BASE_PATTERN + "00(?:CC)*" + Const.BASE_PATTERN + "00(?:CC)*";

    /**
     * パケット全体にマッチする正規表現パターン.
     *
     */
    private static final String PATTERN = "^(?:.{2})+(" + UNIT_PATTERN + ")(?:.{2})*$";

    /**
     * 開始情報にマッチする正規表現パターン.
     */
    private static final String START_PATTERN = "^..002811CDCDCDCD..00000008001012000001FF58006A120000" + Const.BASE_PATTERN + "00(?:CC)+" + Const.BASE_PATTERN + "00(?:CC)+0000000000000000CDCDCCCC(?:.{2})*$";

    /**
     * 結果情報にマッチする正規表現パターン.
     */
    private static final String RESULT_PATTERN = "4400F1110000(....)" + Const.BASE_PATTERN + "00(?:.{2})+(?:CC)+(?:....)CCCC....0000..00(..)00(..)00(..)00(....)(..)00..00CCCC";

    /**
     * キャラクタ名にマッチする正規表現パターン.
     */
    private static final String NAME_PATTERN = "FFFFFFFFCCCC"  + Const.BASE_PATTERN + "00(?:CC)+";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern unitPattern = Pattern.compile(UNIT_PATTERN);

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern startPattern = Pattern.compile(START_PATTERN);

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern resultPattern = Pattern.compile(RESULT_PATTERN);

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern namePattern = Pattern.compile(NAME_PATTERN);

    /**
     * 開始時刻.
     */
    private Date startDate = null;

    /**
     * 名前のセット.
     */
    private Set<String> nameSet;

    private Set<String> killDeathNameSet;

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

        logDataQ = new ConcurrentLinkedQueue<GvLogTable>();

        nameSet = new HashSet<String>();
        killDeathNameSet = new HashSet<String>();

        logDataQ.clear();

        dupChecker = new DuplicateChecker();
        pointChecker = new PointChecker();
    }

    @Override
    public boolean execute(PacketData data) {

        Matcher matcher = pattern.matcher(data.getStrData());

        if (matcher.matches()) {

            Matcher unitMatcher = unitPattern.matcher(data.getStrData());

            while (unitMatcher.find()) {

                int order = RamidoreUtil.intValueFromAscHexString(unitMatcher.group(1));
                int point = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(2));
                int point0 = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(3));
                int point1 = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(4));
                String srcName = RamidoreUtil.encode(unitMatcher.group(5), Const.ENCODING);
                String dstName = RamidoreUtil.encode(unitMatcher.group(6), Const.ENCODING);

                if (!dupChecker.check(data.getDate(), point0, point1)) {

                    continue;
                }

                // 倒すか倒された場合除去する
                killDeathNameSet.add(srcName);
                killDeathNameSet.add(dstName);

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

                pointChecker.check(data.getDate(), logRow);
            }

            return true;
        }

        Matcher startMatcher = startPattern.matcher(data.getStrData());

        if (startMatcher.matches() && startDate == null) {

            // 0 vs 0のデータ追加
            GvLogTable log0 = new GvLogTable();

            startDate = data.getDate();
            log0.setDate(DATE_FORMAT.format(startDate));

            String gName0 = RamidoreUtil.encode(startMatcher.group(1), Const.ENCODING);
            String gName1 = RamidoreUtil.encode(startMatcher.group(2), Const.ENCODING);

            log0.setStrictGuildName0(gName0);
            log0.setStrictGuildName1(gName1);

            logDataQ.add(log0);

            LOG.info(DATE_FORMAT.format(startDate) + "\t開始 : " + gName0 + " vs " + gName1);

            return true;
        }

        Matcher nameMatcher = namePattern.matcher(data.getStrData());

        if (startDate != null && nameMatcher.find()) {

            String name = RamidoreUtil.encode(nameMatcher.group(1), Const.ENCODING);

            nameSet.add(name);
        }

        Matcher resultMatcher = resultPattern.matcher(data.getStrData());

        if (resultMatcher.find()) {

            String gCode = resultMatcher.group(1);
            String gName = RamidoreUtil.encode(resultMatcher.group(2), Const.ENCODING);
            int winCnt = RamidoreUtil.intValueFromAscHexString(resultMatcher.group(3));
            int loseCnt = RamidoreUtil.intValueFromAscHexString(resultMatcher.group(4));
            int drawCnt = RamidoreUtil.intValueFromAscHexString(resultMatcher.group(5));
            int winPoint = RamidoreUtil.intValueFromDescHexString(resultMatcher.group(6));
            String resultCode = resultMatcher.group(7);

            String result = StringUtils.EMPTY;

            if ("00".equals(resultCode)) {
                result = "勝利しました。";
            } else
            if ("01".equals(resultCode)) {
                result = "敗北しました。";
            } else
            if ("02".equals(resultCode)) {
                result = "引き分けです。";
            }

            LOG.info(DATE_FORMAT.format(data.getDate()) + "\t終了 : 【ギルドコード[" + gCode + "]】は【" + gName + "】との対戦で" + result);
            LOG.info(winCnt + "勝 " + loseCnt + "敗 " + drawCnt + "分 勝ち点 " + winPoint);

            // 取得できた全キャラ名からkill/deathに名前の載ったキャラ名を除去
            nameSet.removeAll(killDeathNameSet);

            LOG.info("0死0LA一覧(記録キャラから観測できたキャラのみ)");
            if (nameSet.isEmpty()) {
                LOG.info("無し");
            } else {
                for (String name : nameSet) {

                    LOG.info(name);
                }
            }

            return true;
        }

        return false;
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

        public boolean check(Date date, int p1, int p2) {
            String val = String.format(FORMAT, p1, p2);
            if (set.contains(val)) {
                LOG.warn(DATE_FORMAT.format(date) + "\t重複した点数情報を受信 : " + p1 + " - " + p2);
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

        public void check(Date date, GvLogTable t) {

            this.p[t.getGuildName()] += t.getPoint();

            if (this.p[0] - t.getPoint0() != diff[0]) {
                diff[0] = this.p[0] - t.getPoint0();
                LOG.warn(DATE_FORMAT.format(date) + "\t先入れ側点数にズレが発生 : " + diff[0]);
            } else
            if (this.p[1] - t.getPoint1() != diff[1]) {
                diff[1] = this.p[1] - t.getPoint1();
                LOG.warn(DATE_FORMAT.format(date) + "\t後入れ側点数にズレが発生 : " + diff[1]);
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
