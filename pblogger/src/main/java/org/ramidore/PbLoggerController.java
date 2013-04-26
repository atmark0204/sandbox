package org.ramidore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.ramidore.bean.PbChartBean;
import org.ramidore.bean.PbStatTable;
import org.ramidore.core.PacketAnalyzeService;
import org.ramidore.logic.PbLoggerLogic;
import org.ramidore.logic.system.PointBattleLogic;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
 *
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
     *
     * リストの要素 : 1-5, 全面 マップ key : 系列名(id) value : 系列のSeries
     */
    private List<Map<String, XYChart.Series<Number, Number>>> pbDataMapList = new ArrayList<Map<String, XYChart.Series<Number, Number>>>(6);

    /**
     * 統計情報表示用テーブル.
     */
    @FXML
    private TableView<PbStatTable> pbStatTable;;

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
     * 全面点数.
     */
    @FXML
    private TableColumn<PbStatTable, Integer> statStageTotalCol;

    @Override
    public void concreteInitialize() {

        // サービス生成
        setService(new PacketAnalyzeService(new PbLoggerLogic(), getConfig(), PacketAnalyzeService.MODE_ONLINE));

        // データを初期化
        for (int i = 0; i < 6; i++) {
            pbDataMapList.add(new HashMap<String, XYChart.Series<Number, Number>>());
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

        statIdCol.setCellValueFactory(new PropertyValueFactory<PbStatTable, String>("id"));
        statStage1Col.setCellValueFactory(new PropertyValueFactory<PbStatTable, String>("stage1"));
        statStage2Col.setCellValueFactory(new PropertyValueFactory<PbStatTable, String>("stage2"));
        statStage3Col.setCellValueFactory(new PropertyValueFactory<PbStatTable, String>("stage3"));
        statStage4Col.setCellValueFactory(new PropertyValueFactory<PbStatTable, String>("stage4"));
        statStage5Col.setCellValueFactory(new PropertyValueFactory<PbStatTable, String>("stage5"));
        statStageTotalCol.setCellValueFactory(new PropertyValueFactory<PbStatTable, Integer>("pointTotal"));

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
        deviceCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @SuppressWarnings("rawtypes")
            @Override
            public void changed(ObservableValue ov, Number oldVal, Number newVal) {

                getService().setDevice(newVal.intValue());

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
            @SuppressWarnings("rawtypes")
            @Override
            public void changed(ObservableValue ov, Number oldVal, Number newVal) {

                if (newVal.intValue() != -1) {
                    getService().setListenAddress(newVal.intValue());
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
                    // 開始
                    getService().restart();

                    clear1b.setDisable(false);
                } else {
                    // 停止
                    getService().stop();
                }
            }
        });
    }

    /**
     * チャート初期化.
     */
    @SuppressWarnings("rawtypes")
    private void initializeChart() {

        clear1b.setDisable(true);
        clear1b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                clear2b.setDisable(false);
                clear1b.setDisable(true);
                ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
            }
        });
        clear2b.setDisable(true);
        clear2b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                clear3b.setDisable(false);
                clear2b.setDisable(true);
                ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
            }
        });
        clear3b.setDisable(true);
        clear3b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                clear4b.setDisable(false);
                clear3b.setDisable(true);
                ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
            }
        });
        clear4b.setDisable(true);
        clear4b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                clear5b.setDisable(false);
                clear4b.setDisable(true);
                ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
            }
        });
        clear5b.setDisable(true);
        clear5b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                clear5b.setDisable(true);
                ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().addStageNo();
            }
        });

        loadPastDataB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                loadPastDataB.setDisable(true);
                ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic().loadPastData();
            }
        });

        List<AnimationTimer> timerList = new ArrayList<AnimationTimer>();

        for (int i = 0; i < 6; i++) {
            AnimationTimer timer = prepareTimeline(i + 1);
            timer.start();
            timerList.add(timer);
        }

        stageCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number oldVal, Number newVal) {

                display(newVal.intValue() + 1);
            }
        });

        pbChart.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double xStart = pbChart.getXAxis().getLocalToParentTransform().getTx();
                double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
                chartExtraLabel.setText(String.format("%d, %d (%d, %d); %d - %d", (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(), (int) mouseEvent.getX(), (int) mouseEvent.getY(),
                        (int) xStart, pbChart.getXAxis().getValueForDisplay(axisXRelativeMousePosition).intValue()));
            }
        });

        // Panning works via either secondary (right) mouse or primary with ctrl
        // held down
        ChartPanManager panner = new ChartPanManager(pbChart);
        panner.setMouseFilter(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!(mouseEvent.getButton() == MouseButton.SECONDARY || (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isShortcutDown()))) {
                    mouseEvent.consume();
                }
            }
        });
        panner.start();

        JFXChartUtil.setupZooming(pbChart, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown()) {
                    mouseEvent.consume();
                }
            }
        });

        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("コピー");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PbStatTable stat = pbStatTable.getSelectionModel().getSelectedItem();

                final Clipboard cb = Clipboard.getSystemClipboard();

                final ClipboardContent c = new ClipboardContent();

                c.putString(stat.toCopyStr());

                cb.setContent(c);

                event.consume();
            }
        });
        menu.getItems().addAll(item);

        pbStatTable.setContextMenu(menu);
    }

    /**
     * 指定ステージの全系列のデータを表示する.
     *
     * @param stageNo
     *            ステージ番号
     */
    private void display(int stageNo) {

        pbChart.getData().clear();

        Map<String, Series<Number, Number>> map = null;

        map = pbDataMapList.get(stageNo - 1);

        pbChart.getData().clear();
        for (String id : map.keySet()) {

            pbChart.getData().add(map.get(id));
        }
    }

    /**
     * チャートの更新アニメーションを定義する.
     *
     * @param stageNo
     *            ステージ番号
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
     * @param stageNo
     *            ステージ番号
     */
    private void addDataToSeries(int stageNo) {

        PointBattleLogic logic = ((PbLoggerLogic) getService().getLogic()).getPointBattleLogic();

        ConcurrentLinkedQueue<PbChartBean> dataQ = logic.getChartDataQList().get(stageNo - 1);

        Map<String, Series<Number, Number>> pbDataMap = pbDataMapList.get(stageNo - 1);

        if (dataQ.isEmpty()) {
            return;
        }

        PbChartBean data = dataQ.remove();

        // データ系列名
        String id = data.getId();

        Data<Number, Number> dataElement = null;
        if (stageNo < 6) {
            dataElement = data.toStageData();
        } else {
            dataElement = data.toData();
        }

        if (pbDataMap.containsKey(id)) {
            // 既にあるものにはデータを追加
            XYChart.Series<Number, Number> series = pbDataMap.get(id);

            series.getData().add(dataElement);

        } else {
            // なければ作る
            XYChart.Series<Number, Number> newSeries = new XYChart.Series<Number, Number>();

            newSeries.setName(data.getId());
            newSeries.getData().add(new Data<Number, Number>(0, 0));
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
