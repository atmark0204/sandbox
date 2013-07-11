package org.ramidore.logic.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TableView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ramidore.bean.PbLogBean;
import org.ramidore.bean.PbStatTable;
import org.ramidore.core.PacketData;
import org.ramidore.util.RamidoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ポイント戦.
 *
 * @author atmark
 *
 */
public class PointBattleLogic extends AbstractSystemMessageLogic {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PointBattleLogic.class);

    /**
     * 得点情報にマッチする正規表現パターン.
     *
     */
    private static final String UNIT_PATTERN = "10005C1200000B00....0000(......)00";

    /**
     * パケット全体にマッチする正規表現パターン.
     *
     */
    private static final String PATTERN = "^(?:.{2})*(" + UNIT_PATTERN + ")(?:.{2})*$";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern unitPattern = Pattern.compile(UNIT_PATTERN);

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * JavaFXスレッドと同期するためのLinkedQueueのリスト.
     */
    private List<ConcurrentLinkedQueue<PbLogBean>> chartDataQList = new ArrayList<ConcurrentLinkedQueue<PbLogBean>>();

    /**
     * 統計表示用テーブル.
     */
    private TableView<PbStatTable> statTable;

    /**
     * 識別子.
     *
     * インスタンス生成時刻とする
     */
    private String id;

    /**
     * シーケンス番号.
     */
    private int sequentialNo = 1;

    /**
     * 現在の面番号.
     */
    private int currentStageNo = 0;

    /**
     * ステージシーケンス番号.
     */
    private int stageSequentialNo = 1;

    /**
     * 終了フラグ.
     */
    private boolean isEnd = false;

    /**
     * 現在のデータ.
     */
    private PbLogBean currentData = null;

    /**
     * 現在のポイントマップ.
     */
    private Map<Integer, Integer> pointMap = new HashMap<Integer, Integer>();

    /**
     * 現在の統計情報.
     */
    private PbStatTable currentStat = null;

    /**
     * 重複チェックオブジェクト.
     */
    private DuplicateChecker dupChecker;

    /**
     * コンストラクタ.
     */
    public PointBattleLogic() {

        currentStat = new PbStatTable();

        for (int i = 0; i < 6; i++) {
            chartDataQList.add(new ConcurrentLinkedQueue<PbLogBean>());
        }

        pointMap.put(0, 0);

        dupChecker = new DuplicateChecker();
    }

    @Override
    public boolean execute(PacketData data) {

        Matcher matcher = pattern.matcher(data.getStrData());

        if (!isEnd && currentStageNo < 6 && matcher.matches()) {

            if (currentStageNo == 0) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                this.id = sdf.format(new Date());

                LOG.info(id);

                currentStat.setId(id);

                addStageNo();
            }

            Matcher unitMatcher = unitPattern.matcher(data.getStrData());

            while(unitMatcher.find()) {

                ConcurrentLinkedQueue<PbLogBean> dataQ = chartDataQList.get(currentStageNo - 1);
                ConcurrentLinkedQueue<PbLogBean> allDataQ = chartDataQList.get(5);

                int point = RamidoreUtil.intValueFromDescHexString(unitMatcher.group(1));

                if (!dupChecker.check(point)) {
                    continue;
                }

                pointMap.put(currentStageNo, point);

                currentData = new PbLogBean(id, sequentialNo, currentStageNo, stageSequentialNo, point, pointMap.get(currentStageNo - 1));
                dataQ.add(currentData);
                allDataQ.add(currentData);

                // リアルタイム統計
                statRealTimeData();

                LOG.info(sequentialNo + "\t" + currentStageNo + "\t" + stageSequentialNo + "\t" + point);

                sequentialNo++;
                stageSequentialNo++;
            }

            return true;
        } else if (currentStageNo == 6) {

            isEnd = true;
        }

        return false;
    }

    /**
     * ステージ番号を加算する.
     */
    public void addStageNo() {

        currentStageNo++;
        stageSequentialNo = 1;
    }

    /**
     * 過去データを読み込む.
     */
    public void loadPastData() {

        Collection<File> fileList = FileUtils.listFiles(new File("./"), new String[] {"log"}, false);

        for (File file : fileList) {

            BufferedReader br = null;

            try {

                br = new BufferedReader(new FileReader(file));

                List<String> list = IOUtils.readLines(br);

                if (list.size() < 2) {
                    continue;
                }

                String id = list.get(0);

                if (id.equals(this.id)) {
                    // 記録中のファイルは無視
                    continue;
                }

                List<PbLogBean> dataList = new ArrayList<PbLogBean>();

                for (int i = 1; i < list.size(); i++) {
                    PbLogBean bean = loadLine(id, list.get(i));

                    dataList.add(bean);
                }

                statData(id, dataList);

            } catch (FileNotFoundException e) {
                LOG.error(ExceptionUtils.getStackTrace(e));
            } catch (IOException e) {
                LOG.error(ExceptionUtils.getStackTrace(e));
            } finally {
                IOUtils.closeQuietly(br);
            }
        }
    }

    /**
     * ログの1行を読込みチャート用データを返す.
     *
     * @param id
     *            識別子
     * @param line
     *            1行の文字列
     * @return PointBatteleChartBean
     */
    private PbLogBean loadLine(String id, String line) {

        String[] element = StringUtils.split(line, '\t');

        if (element.length != 4) {
            return null;
        }

        return new PbLogBean(id, Integer.valueOf(element[0]), Integer.valueOf(element[1]), Integer.valueOf(element[2]), Integer.valueOf(element[3]));
    }

    /**
     * 1系列について点数の統計を取る.
     *
     * @param id
     *            識別子
     * @param dataList
     *            データのリスト
     */
    private void statData(String id, List<PbLogBean> dataList) {

        // key : ステージ番号 value : 得点
        Map<Integer, Integer> pointMap = new HashMap<Integer, Integer>();
        // key : ステージ番号 value : 得点扱いになった数（死亡によるポイント減少含む）
        Map<Integer, Integer> mobCountMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < 7; i++) {
            // 0で初期化
            pointMap.put(i, 0);
            mobCountMap.put(i, 0);
        }

        for (PbLogBean data : dataList) {
            pointMap.put(data.getStageNo(), data.getPoint());
            mobCountMap.put(data.getStageNo(), data.getStageSequentialNo());
        }

        for (PbLogBean data : dataList) {

            data.setPointOffset(pointMap.get(data.getStageNo() - 1));

            ConcurrentLinkedQueue<PbLogBean> dataQ = chartDataQList.get(data.getStageNo() - 1);
            ConcurrentLinkedQueue<PbLogBean> allDataQ = chartDataQList.get(chartDataQList.size() - 1);

            dataQ.add(data);
            allDataQ.add(data);
        }

        PbStatTable row = new PbStatTable();
        row.setId(id);

        row.setPoint1(pointMap.get(1));
        row.setMobCount1(mobCountMap.get(1));
        row.setStage1();

        row.setPoint2(pointMap.get(2) - pointMap.get(1));
        row.setMobCount2((mobCountMap.get(2)));
        row.setStage2();

        row.setPoint3(pointMap.get(3) - pointMap.get(2));
        row.setMobCount3((mobCountMap.get(3)));
        row.setStage3();

        row.setPoint4(pointMap.get(4) - pointMap.get(3));
        row.setMobCount4(mobCountMap.get(4));
        row.setStage4();

        row.setPoint5(pointMap.get(5) - pointMap.get(4));
        row.setMobCount5(mobCountMap.get(5));
        row.setStage5();

        row.setPoint2Total(row.getPoint1() + row.getPoint2());
        row.setPoint3Total(row.getPoint2Total() + row.getPoint3());
        row.setPoint4Total(row.getPoint3Total() + row.getPoint4());
        row.setPointTotal(pointMap.get(5));

        statTable.getItems().add(row);

    }

    /**
     * 記録中のデータの統計をとる.
     */
    private void statRealTimeData() {

        if (!statTable.getItems().contains(currentStat)) {
            statTable.getItems().add(0, currentStat);
        }

        PbStatTable stat = null;

        for (PbStatTable t : statTable.getItems()) {

            if (this.id.equals(t.getId())) {

                stat = t;

                break;
            }
        }

        int p = currentData.getPoint();

        switch (currentStageNo) {
        case 1:
            stat.setMobCount1(currentData.getStageSequentialNo());
            stat.setPoint1(p);
            stat.setStage1();
            break;
        case 2:
            stat.setMobCount2(currentData.getStageSequentialNo());
            // 前面の最終点数をマイナスする
            stat.setPoint2(p - stat.getPoint1());
            stat.setStage2();
            break;
        case 3:
            stat.setMobCount3(currentData.getStageSequentialNo());
            stat.setPoint3(p - stat.getPoint1() - stat.getPoint2());
            stat.setStage3();
            break;
        case 4:
            stat.setMobCount4(currentData.getStageSequentialNo());
            stat.setPoint4(p - stat.getPoint1() - stat.getPoint2() - stat.getPoint3());
            stat.setStage4();
            break;
        case 5:
            stat.setMobCount5(currentData.getStageSequentialNo());
            stat.setPoint5(p - stat.getPoint1() - stat.getPoint2() - stat.getPoint3() - stat.getPoint4());
            stat.setStage5();
            break;
        }
        stat.setPointTotal(p);

    }

    /**
     * getter.
     *
     * @return chartDataQList
     */
    public List<ConcurrentLinkedQueue<PbLogBean>> getChartDataQList() {
        return chartDataQList;
    }

    /**
     * setter.
     *
     * @param chartDataQList
     *            セットする chartDataQList
     */
    public void setChartDataQList(List<ConcurrentLinkedQueue<PbLogBean>> chartDataQList) {
        this.chartDataQList = chartDataQList;
    }

    /**
     * getter.
     *
     * @return statTable
     */
    public TableView<PbStatTable> getStatTable() {
        return statTable;
    }

    /**
     * setter.
     *
     * @param statTable
     *            セットする statTable
     */
    public void setStatTable(TableView<PbStatTable> statTable) {
        this.statTable = statTable;
    }

    /**
     * 重複ポイント受信チェッカー.
     *
     * @author atmark
     *
     */
    private class DuplicateChecker {

        private Set<Integer> set;

        public DuplicateChecker() {
            set = new HashSet<Integer>();
        }

        public boolean check(int point) {
            if (set.contains(point)) {
                return false;
            }
            set.add(point);
            return true;
        }
    }
}
