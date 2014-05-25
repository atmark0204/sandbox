package org.ramidore;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import org.ramidore.bean.RedStoneChartBean;
import org.ramidore.controller.AbstractMainController;
import org.ramidore.core.PacketAnalyzeService;
import org.ramidore.logic.RsMonitorLogic;
import org.ramidore.logic.system.RedstoneLogic;

import java.util.Properties;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
 *
 */
public class RsMonitorController extends AbstractMainController implements Initializable {

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
     * チャート.
     */
    @FXML
    AreaChart<Number, Number> areaChart;

    @Override
    public void concreteInitialize() {

        setService(new PacketAnalyzeService(new RsMonitorLogic(), getConfig(), PacketAnalyzeService.MODE_ONLINE));

        loadConfig();

        initializeDeviceSetting();

        initializeChart();
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

                ObservableList<String> addrs = addressCb.getItems();
                addrs.clear();
                addrs.addAll(getService().getAddressList());

                addressCb.getSelectionModel().selectFirst();
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

            } else {
                // 停止
                getService().stop();
            }
        });
    }

    /**
     * チャート初期化.
     */
    private void initializeChart() {

        areaChart.getXAxis().setLabel("起動時からの返却総数");
        areaChart.getYAxis().setLabel("各勢力への返却数");

        XYChart.Series<Number, Number> seriesTenjo = new XYChart.Series<Number, Number>();
        seriesTenjo.setName("天上");

        XYChart.Series<Number, Number> seriesChika = new XYChart.Series<Number, Number>();
        seriesChika.setName("地下");

        XYChart.Series<Number, Number> seriesAkuma = new XYChart.Series<Number, Number>();
        seriesAkuma.setName("悪魔");

        areaChart.getData().add(seriesTenjo);
        areaChart.getData().add(seriesChika);
        areaChart.getData().add(seriesAkuma);

        prepareTimeline();
    }

    /**
     * チャートの更新アニメーションを定義する.
     */
    private void prepareTimeline() {
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    /**
     * 別スレッドで更新されているデータを取得し、チャートにデータを追加する.
     */
    private void addDataToSeries() {

        RedstoneLogic redstone = ((RsMonitorLogic) getService().getLogic()).getRedstoneLogic();

        if (redstone.getChartDataQ().isEmpty()) {
            return;
        }

        RedStoneChartBean bean = redstone.getChartDataQ().remove();

        areaChart.getData().get(0).getData().add(bean.toTenjoData());
        areaChart.getData().get(1).getData().add(bean.toChikaData());
        areaChart.getData().get(2).getData().add(bean.toAkumaData());

        areaChart.getData().get(0).setName("天上(" + bean.getCurrentTenjoCount() + ")");
        areaChart.getData().get(1).setName("地下(" + bean.getCurrentChikaCount() + ")");
        areaChart.getData().get(2).setName("悪魔(" + bean.getCurrentAkumaCount() + ")");
    }

    @Override
    public void loadConfig() {

        super.loadConfig();

        this.loadConfig(getConfig());
    }

    @Override
    public void saveConfig() {

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
}
