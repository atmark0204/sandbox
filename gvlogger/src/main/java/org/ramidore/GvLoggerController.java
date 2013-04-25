package org.ramidore;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.ramidore.bean.GvLogTable;
import org.ramidore.bean.GvStatTable;
import org.ramidore.core.PacketAnalyzeService;
import org.ramidore.logic.GvLoggerLogic;
import org.ramidore.logic.system.GuildBattleLogic;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
 *
 */
public class GvLoggerController extends AbstractMainController {

    /**
     * ネットワークデバイスのセレクトボックス.
     */
    @FXML
    private ChoiceBox<String> deviceCb;

    /**
     * IPアドレスのセレクトボックス.
     */
    @FXML
    private ChoiceBox<String> addressCb;

    /**
     * キャプチャモード選択.
     */
    @FXML
    private ChoiceBox<String> captureModeCb;

    /**
     * 開始/停止ボタン.
     */
    @FXML
    private ToggleButton startTb;

    /**
     * 過去データ読み込みボタン.
     */
    @FXML
    private Button loadPastDataB;

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
     * キャラ名と統計データのマップ.
     */
    private Map<String, GvStatTable> statMap = new ConcurrentHashMap<String, GvStatTable>();

    @Override
    public void concreteInitialize() {

        // サービス生成
        setService(new PacketAnalyzeService(new GvLoggerLogic(), getConfig()));

        loadConfig();

        initializeDeviceSetting();

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

        statGuildCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("guildName"));
        statCharaCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, String>("charaName"));
        statKillCountCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("killCount"));
        statDeathCountCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("deathCount"));
        statPointCol.setCellValueFactory(new PropertyValueFactory<GvStatTable, Integer>("point"));
    }

    /**
     * ネットワークデバイスの初期化.
     */
    private void initializeDeviceSetting() {

        // デバイス一覧を初期化
        deviceCb.getItems().addAll(getService().getDeviceNameList());
        // 初期設定を取得
        deviceCb.getSelectionModel().select(getService().getCurrentDeviceIndex());

        // 使用デバイス選択
        deviceCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {

                // 新しいデバイスを設定
                getService().setDevice(newVal.intValue());

                // ipアドレス一覧をクリア後更新
                addressCb.getItems().clear();
                addressCb.getItems().addAll(getService().getAddressList());
                addressCb.getSelectionModel().selectFirst();

                // 新しいIPアドレスを設定
                getService().setListenAddress(0);
            }
        });

        // IPアドレス一覧を初期化
        addressCb.getItems().addAll(getService().getAddressList());

        // 初期設定を取得
        addressCb.getSelectionModel().select(getService().getCurrentListenAddressIndex());

        // ListenするIPアドレス選択
        addressCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {

                if (newVal.intValue() != -1) {
                    getService().setListenAddress(newVal.intValue());
                }
            }
        });

        captureModeCb.getItems().addAll(PacketAnalyzeService.MODE);
        captureModeCb.getSelectionModel().selectFirst();
        captureModeCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {

                if (newVal.intValue() != -1) {
                    getService().setMode(newVal.intValue());
                }
            }
        });

        // 開始ボタン押下
        startTb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // イベントの発生元を取得
                ToggleButton toggle = (ToggleButton) event.getSource();

                if (toggle.isSelected()) {

                    deviceCb.setDisable(true);
                    addressCb.setDisable(true);
                    captureModeCb.setDisable(true);
                    loadPastDataB.setDisable(true);

                    // 開始
                    getService().restart();

                } else {

                    deviceCb.setDisable(false);
                    addressCb.setDisable(false);
                    captureModeCb.setDisable(false);
                    loadPastDataB.setDisable(false);

                    // 停止
                    getService().stop();
                }
            }
        });

        loadPastDataB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {

                loadPastDataB.setDisable(true);

                FileChooser fc = new FileChooser();
                fc.setTitle("select file");
                fc.setInitialDirectory(new File(new File(".").getAbsoluteFile().getParent()));
                fc.getExtensionFilters().add(new ExtensionFilter("LOG", "*.log"));

                File f = fc.showOpenDialog(null);

                if (f != null) {

                    clearData();

                    ((GvLoggerLogic) getService().getLogic()).getGuildBattleLogic().loadPastData(f.getAbsolutePath());
                }

                loadPastDataB.setDisable(false);
            }
        });
    }

    /**
     * 描画に必要なオブジェクトをクリアする.
     */
    private void clearData() {
        timelineChart.getData().get(0).getData().clear();
        timelineChart.getData().get(1).getData().clear();
        personChart.getData().get(0).getData().clear();
        logTable.getItems().clear();
        statTable.getItems().clear();

        statMap.clear();
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

        GuildBattleLogic logic = ((GvLoggerLogic) getService().getLogic()).getGuildBattleLogic();

        ConcurrentLinkedQueue<GvLogTable> logDataQ = logic.getLogDataQ();

        if (logDataQ.isEmpty()) {
            return;
        }

        GvLogTable log = logDataQ.remove();

        if (log.getGuildName() == -1) {
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

    @Override
    public void loadConfig() {

        super.loadConfig();

        this.loadConfig(getConfig());
    }

    @Override
    public void saveConfig() {

        this.saveConfig(getConfig());

        super.saveConfig();
    }

    @Override
    public void loadConfig(Properties config) {
    }

    @Override
    public void saveConfig(Properties config) {
    }

    /**
     * Application.stopが呼び出された際にコールされる処理.
     */
    public void stop() {

        this.saveConfig();

        getService().stop();
    }
}
