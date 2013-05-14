package org.ramidore.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import org.ramidore.bean.GvLogTable;
import org.ramidore.bean.GvStatTable;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
 *
 */
public class GvLoggerTabController extends AbstractController {

    /**
     * 時系列表示用チャート.
     */
    @FXML
    private LineChart<String, Number> timelineChart;

    /**
     * 個人成績選択.
     */
    @FXML
    private ChoiceBox<String> personChartCb;

    /**
     * 個人成績表示用チャート.
     */
    @FXML
    private BarChart<String, Number> personChart;

    /**
     * ログ表示用テーブル.
     */
    @FXML
    private TableView<GvLogTable> logTable;

    /**
     * 時刻.
     */
    @FXML
    private TableColumn<GvLogTable, String> logDateCol;

    /**
     * 倒したキャラ名.
     */
    @FXML
    private TableColumn<GvLogTable, String> logSrcCharaCol;

    /**
     * 倒されたキャラ名.
     */
    @FXML
    private TableColumn<GvLogTable, String> logDstCharaCol;

    /**
     * ギルド名(0 or 1).
     */
    @FXML
    private TableColumn<GvLogTable, Integer> logGuildCol;

    /**
     * 入った点数.
     */
    @FXML
    private TableColumn<GvLogTable, Integer> logPointCol;

    /**
     * 先入れ側点数.
     */
    @FXML
    private TableColumn<GvLogTable, Integer> logPoint0Col;

    /**
     * 後入れ側点数.
     */
    @FXML
    private TableColumn<GvLogTable, Integer> logPoint1Col;

    /**
     * 統計情報表示用テーブル.
     */
    @FXML
    private TableView<GvStatTable> statTable;

    /**
     * ギルド名(0 or 1).
     */
    @FXML
    private TableColumn<GvStatTable, Integer> statGuildCol;

    /**
     * キャラ名.
     */
    @FXML
    private TableColumn<GvStatTable, String> statCharaCol;

    /**
     * kill数.
     */
    @FXML
    private TableColumn<GvStatTable, Integer> statKillCountCol;

    /**
     * death数.
     */
    @FXML
    private TableColumn<GvStatTable, Integer> statDeathCountCol;

    /**
     * 得失点.
     */
    @FXML
    private TableColumn<GvStatTable, Integer> statPointCol;

    /**
     * 備考欄.
     */
    @FXML
    private TableColumn<GvStatTable, String> statNoteCol;

    /**
     * キャラ名と統計データのマップ.
     */
    private Map<String, GvStatTable> statMap = new ConcurrentHashMap<String, GvStatTable>();

    /**
     * データキュー.
     */
    private ConcurrentLinkedQueue<GvLogTable> logDataQ;

    @Override
    public void concreteInitialize() {

        initializeChart();

        initializeLogic();
    }

    /**
     * パケット処理関連の業務処理の初期化.
     */
    private void initializeLogic() {

        // コントローラーのフィールドとデータクラスのプロパティーを紐付ける

        logDateCol.setCellValueFactory(new PropertyValueFactory<GvLogTable, String>("date"));
        logSrcCharaCol.setCellValueFactory(new PropertyValueFactory<GvLogTable, String>("srcCharaName"));
        logDstCharaCol.setCellValueFactory(new PropertyValueFactory<GvLogTable, String>("dstCharaName"));
        logGuildCol.setCellValueFactory(new PropertyValueFactory<GvLogTable, Integer>("guildName"));
        logPointCol.setCellValueFactory(new PropertyValueFactory<GvLogTable, Integer>("point"));
        logPoint0Col.setCellValueFactory(new PropertyValueFactory<GvLogTable, Integer>("point0"));
        logPoint1Col.setCellValueFactory(new PropertyValueFactory<GvLogTable, Integer>("point1"));

        statTable.setEditable(true);
        statGuildCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("guildName"));
        statCharaCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, String>("charaName"));
        statKillCountCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("killCount"));
        statDeathCountCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("deathCount"));
        statPointCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("point"));
        statNoteCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, String>("note"));

        statNoteCol.setCellFactory(new Callback<TableColumn<GvStatTable, String>, TableCell<GvStatTable, String>>() {
            @Override
            public TableCell<GvStatTable, String> call(TableColumn<GvStatTable, String> arg0) {
                return new TextFieldTableCell<GvStatTable, String>(new DefaultStringConverter());
            }
        });

        statNoteCol.setOnEditCommit(new EventHandler<CellEditEvent<GvStatTable, String>>() {
            @Override
            public void handle(CellEditEvent<GvStatTable, String> t) {
                ((GvStatTable) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNote(t.getNewValue());
            }
        });
    }

    /**
     * チャート初期化.
     */
    @SuppressWarnings("unchecked")
    private void initializeChart() {

        XYChart.Series<String, Number> series0 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series0.setName("先入れ側");
        series1.setName("後入れ側");

        timelineChart.getData().add(series0);
        timelineChart.getData().add(series1);

        BarChart.Series<String, Number> series = new BarChart.Series<String, Number>();
        series.setName("個人成績");

        personChart.getData().setAll(series);

        personChartCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {

                display(newVal.intValue());
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                statData();
            }
        } .start();
    }

    /**
     * 個人成績チャートを表示する.
     *
     * @param guild ギルド(0 or 1)
     */
    private void display(int guild) {

        BarChart.Series<String, Number> series = personChart.getData().get(0);
        series.getData().clear();

        for (GvStatTable row : statTable.getItems()) {

            if (row.getGuildName() == guild) {

                //series.getData().add(new BarChart.Data<String, Number>(row.getCharaName(), row.getPoint()));
                series.getData().add(row.toBarChartData());
            }
        }
    }

    /**
     * キューからデータを取得し、統計・チャート用データ追加処理を行う.
     */
    @SuppressWarnings("unchecked")
    private void statData() {

        if (logDataQ.isEmpty()) {
            return;
        }

        GvLogTable log = logDataQ.remove();

        if (log.getGuildName() == -1) {

            timelineChart.getData().get(0).getData().add(log.toTimelineData()[0]);
            timelineChart.getData().get(1).getData().add(log.toTimelineData()[1]);

            return;
        }

        logTable.getItems().add(log);

        GvStatTable statSrc = null;
        if (statMap.containsKey(log.getSrcCharaName())) {

            statSrc = statMap.get(log.getSrcCharaName());

            statSrc.setKillCount(statSrc.getKillCount() + 1);
            statSrc.setPoint(statSrc.getPoint() + log.getPoint());

        } else {

            statSrc = new GvStatTable();

            statSrc.setGuildName(log.getGuildName());
            statSrc.setCharaName(log.getSrcCharaName());
            statSrc.setKillCount(1);
            statSrc.setPoint(log.getPoint());

            statMap.put(log.getSrcCharaName(), statSrc);

            statTable.getItems().add(statMap.get(log.getSrcCharaName()));
        }

        GvStatTable statDst = null;
        if (statMap.containsKey(log.getDstCharaName())) {

            statDst = statMap.get(log.getDstCharaName());

            statDst.addDeathCount();
            statDst.setPoint(statDst.getPoint() - log.getPoint());
        } else {

            statDst = new GvStatTable();

            statDst.setGuildName(log.getGuildName() == 0 ? 1 : 0);
            statDst.setCharaName(log.getDstCharaName());
            statDst.addDeathCount();
            statDst.setPoint(-log.getPoint());

            statMap.put(log.getDstCharaName(), statDst);

            statTable.getItems().add(statMap.get(log.getDstCharaName()));
        }

        timelineChart.getData().get(0).getData().add(log.toTimelineData()[0]);
        timelineChart.getData().get(1).getData().add(log.toTimelineData()[1]);
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
     * @param logDataQ セットする logDataQ
     */
    public void setLogDataQ(ConcurrentLinkedQueue<GvLogTable> logDataQ) {
        this.logDataQ = logDataQ;
    }

    /**
     * getter.
     *
     * @return statTable
     */
    public TableView<GvStatTable> getStatTable() {
        return statTable;
    }

    /**
     * setter.
     *
     * @param statTable セットする statTable
     */
    public void setStatTable(TableView<GvStatTable> statTable) {
        this.statTable = statTable;
    }
}
