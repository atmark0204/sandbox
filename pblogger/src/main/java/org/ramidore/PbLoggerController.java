package org.ramidore;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.ramidore.bean.PbLogBean;
import org.ramidore.bean.PbStatTable;
import org.ramidore.controller.AbstractMainController;
import org.ramidore.core.PacketAnalyzeService;
import org.ramidore.logic.PbLoggerLogic;
import org.ramidore.logic.system.PointBattleLogic;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
 */
public class PbLoggerController extends AbstractMainController {

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
     * 開始/停止ボタン.
     */
    @FXML
    private ToggleButton startTb;

    /**
     * 1面クリアボタン.
     */
    @FXML
    private Button clear1b;

    /**
     * 2面クリアボタン.
     */
    @FXML
    private Button clear2b;

    /**
     * 3面クリアボタン.
     */
    @FXML
    private Button clear3b;

    /**
     * 4面クリアボタン.
     */
    @FXML
    private Button clear4b;

    /**
     * 5面クリアボタン.
     */
    @FXML
    private Button clear5b;

    /**
     * 過去データ読み込みボタン.
     */
    @FXML
    private Button loadPastDataB;

    /**
     * ステージ選択.
     */
    @FXML
    private ChoiceBox<String> stageCb;

    /**
     * ポイント戦用チャート.
     */
    @FXML
    private LineChart<Number, Number> pbChart;

    /**
     * 座標表示用.
     */
    @FXML
    private Label chartExtraLabel;

    /**
     * ポイント戦の得点データ保持.
     * <p>
     * リストの要素 : 1-5, 全面 マップ key : 系列名(id) value : 系列のSeries
     */
    private List<Map<String, XYChart.Series<Number, Number>>> pbDataMapList = new ArrayList<>(6);

    /**
     * 統計情報表示用テーブル.
     */
    @FXML
    private TableView<PbStatTable> pbStatTable;
    ;

    /**
     * 系列名.
     */
    @FXML
    private TableColumn<PbStatTable, String> statIdCol;

    /**
     * 1面点数.
     */
    @FXML
    private TableColumn<PbStatTable, String> statStage1Col;

    /**
     * 2面点数.
     */
    @FXML
    private TableColumn<PbStatTable, String> statStage2Col;

    /**
     * 3面点数.
     */
    @FXML
    private TableColumn<PbStatTable, String> statStage3Col;

    /**
     * 4面点数.
     */
    @FXML
    private TableColumn<PbStatTable, String> statStage4Col;

    /**
     * 5面点数.
     */
    @FXML
    private TableColumn<PbStatTable, String> statStage5Col;

    /**
     * 2面まで合計.
     */
    @FXML
    private TableColumn<PbStatTable, Integer> statStage2TotalCol;

    /**
     * 3面まで合計.
     */
    @FXML
    private TableColumn<PbStatTable, Integer> statStage3TotalCol;

    /**
     * 4面まで合計.
     */
    @FXML
    private TableColumn<PbStatTable, Integer> statStage4TotalCol;

    /**
     * 全面点数.
     */
    @FXML
    private TableColumn<PbStatTable, Integer> statStage5TotalCol;

    @Override
    public void concreteInitialize() {

        // サービス生成
        setService(new PacketAnalyzeService(new PbLoggerLogic(), getConfig(), PacketAnalyzeService.MODE_ONLINE));

        // データを初期化
        for (int i = 0; i < 6; i++) {
            pbDataMapList.add(new HashMap<>());
        }

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

        PbLoggerLogic logic = (PbLoggerLogic) getService().getLogic();

        statIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        statStage1Col.setCellValueFactory(new PropertyValueFactory<>("stage1"));
        statStage2Col.setCellValueFactory(new PropertyValueFactory<>("stage2"));
        statStage3Col.setCellValueFactory(new PropertyValueFactory<>("stage3"));
        statStage4Col.setCellValueFactory(new PropertyValueFactory<>("stage4"));
        statStage5Col.setCellValueFactory(new PropertyValueFactory<>("stage5"));
        statStage2TotalCol.setCellValueFactory(new PropertyValueFactory<>("point2Total"));
        statStage3TotalCol.setCellValueFactory(new PropertyValueFactory<>("point3Total"));
        statStage4TotalCol.setCellValueFactory(new PropertyValueFactory<>("point4Total"));
        statStage5TotalCol.setCellValueFactory(new PropertyValueFactory<>("pointTotal"));

        logic.getPointBattleLogic().setStatTable(pbStatTable);
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
        deviceCb.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> {

            getService().setDevice(newVal.intValue());

            addressCb.getItems().clear();
            addressCb.getItems().addAll(getService().getAddressList());
            addressCb.getSelectionModel().selectFirst();

            // 新しいIPアドレスを設定
            getService().setListenAddress(0);
        });

        // IPアドレス一覧を初期化
        addressCb.getItems().addAll(getService().getAddressList());

        // 初期設定を取得
        addressCb.getSelectionModel().select(getService().getCurrentListenAddressIndex());

        // ListenするIPアドレス選択
        addressCb.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> {

            if (newVal.intValue() != -1) {
                getService().setListenAddress(newVal.intValue());
            }
        });

        // 開始ボタン押下
        startTb.setOnAction(event -> {
            // イベントの発生元を取得
            ToggleButton toggle = (ToggleButton) event.getSource();

            if (toggle.isSelected()) {
                // 開始
                getService().restart();

                clear1b.setDisable(false);
            } else {
                // 停止
                getService().stop();
            }
        });
    }

    /**
     * チャート初期化.
     */
    @SuppressWarnings("rawtypes")
    private void initializeChart() {

        clear1b.setDisable(true);
        clear1b.setOnAction(ae -> {
            clear2b.setDisable(false);
            clear1b.setDisable(true);
            ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
        });
        clear2b.setDisable(true);
        clear2b.setOnAction(ae -> {
            clear3b.setDisable(false);
            clear2b.setDisable(true);
            ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
        });
        clear3b.setDisable(true);
        clear3b.setOnAction(ae -> {
            clear4b.setDisable(false);
            clear3b.setDisable(true);
            ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
        });
        clear4b.setDisable(true);
        clear4b.setOnAction(ae -> {
            clear5b.setDisable(false);
            clear4b.setDisable(true);
            ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
        });
        clear5b.setDisable(true);
        clear5b.setOnAction(ae -> {
            clear5b.setDisable(true);
            ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
        });

        loadPastDataB.setOnAction(ae -> {
            loadPastDataB.setDisable(true);
            ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().loadPastData();
        });

        List<AnimationTimer> timerList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            AnimationTimer timer = prepareTimeline(i + 1);
            timer.start();
            timerList.add(timer);
        }

        stageCb.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> display(newVal.intValue() + 1));

        pbChart.setOnMouseMoved(mouseEvent -> {
            double xStart = pbChart.getXAxis().getLocalToParentTransform().getTx();
            double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
            chartExtraLabel.setText(String.format("%d, %d (%d, %d); %d - %d", (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(), (int) mouseEvent.getX(), (int) mouseEvent.getY(),
                    (int) xStart, pbChart.getXAxis().getValueForDisplay(axisXRelativeMousePosition).intValue()));
        });

        // Panning works via either secondary (right) mouse or primary with ctrl
        // held down
        ChartPanManager panner = new ChartPanManager(pbChart);
        panner.setMouseFilter(mouseEvent -> {
            if (!(mouseEvent.getButton() == MouseButton.SECONDARY || (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isShortcutDown()))) {
                mouseEvent.consume();
            }
        });
        panner.start();

        JFXChartUtil.setupZooming(pbChart, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown()) {
                mouseEvent.consume();
            }
        });

        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("コピー");
        item.setOnAction(event -> {
            PbStatTable stat = pbStatTable.getSelectionModel().getSelectedItem();

            final Clipboard cb = Clipboard.getSystemClipboard();

            final ClipboardContent c = new ClipboardContent();

            c.putString(stat.toCopyStr());

            cb.setContent(c);

            event.consume();
        });
        menu.getItems().addAll(item);

        pbStatTable.setContextMenu(menu);
    }

    /**
     * 指定ステージの全系列のデータを表示する.
     *
     * @param stageNo ステージ番号
     */
    private void display(int stageNo) {

        pbChart.getData().clear();

        Map<String, Series<Number, Number>> map;

        map = pbDataMapList.get(stageNo - 1);

        pbChart.getData().clear();
        for (String id : map.keySet()) {

            pbChart.getData().add(map.get(id));
        }
    }

    /**
     * チャートの更新アニメーションを定義する.
     *
     * @param stageNo ステージ番号
     * @return AnimationTimer
     */
    private AnimationTimer prepareTimeline(final int stageNo) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries(stageNo);
            }
        };
    }

    /**
     * 別スレッドで更新されているデータを取得し、チャートにデータを追加する.
     *
     * @param stageNo ステージ番号
     */
    private void addDataToSeries(int stageNo) {

        PointBattleLogic logic = ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic();

        ConcurrentLinkedQueue<PbLogBean> dataQ = logic.getChartDataQList().get(stageNo - 1);

        Map<String, Series<Number, Number>> pbDataMap = pbDataMapList.get(stageNo - 1);

        if (dataQ.isEmpty()) {
            return;
        }

        PbLogBean data = dataQ.remove();

        // データ系列名
        String id = data.getId();

        Data<Number, Number> dataElement = stageNo < 6 ? data.toStageData() : data.toData();

        if (pbDataMap.containsKey(id)) {
            // 既にあるものにはデータを追加
            XYChart.Series<Number, Number> series = pbDataMap.get(id);

            series.getData().add(dataElement);

        } else {
            // なければ作る
            XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();

            newSeries.setName(data.getId());
            newSeries.getData().add(new Data<>(0, 0));
            newSeries.getData().add(dataElement);

            pbDataMap.put(id, newSeries);
        }
    }

    @Override
    public void loadConfig() {

        super.loadConfig();

        this.loadConfig(getConfig());
    }

    @Override
    public void saveConfig() {

        this.saveConfig(getConfig());

        double w = deviceCb.getScene().getWindow().getWidth();
        double h = deviceCb.getScene().getWindow().getHeight();

        setStageWidth(w);
        setStageHeight(h);

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
