package org.ramidore;

import java.util.Properties;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.ramidore.bean.ChatTable;
import org.ramidore.bean.ItemTable;
import org.ramidore.bean.RedStoneChartBean;
import org.ramidore.component.OshiraseJDialog;
import org.ramidore.controller.AbstractMainController;
import org.ramidore.core.PacketAnalyzeService;
import org.ramidore.logic.ChatLoggerLogic;
import org.ramidore.logic.system.RedstoneLogic;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
 *
 */
public class ChatLoggerController extends AbstractMainController {

    /**
     * 叫び 表示用テーブル.
     */
    @FXML
    private TableView<ChatTable> sakebiChatTable;

    /**
     * 叫び 日付のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> sakebiChatDate;

    /**
     * 叫び 名前のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> sakebiChatName;

    /**
     * 叫び 内容のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> sakebiChatContent;

    /**
     * 一般 表示用テーブル.
     */
    @FXML
    private TableView<ChatTable> normalChatTable;

    /**
     * 一般 日付のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> normalChatDate;

    /**
     * 一般 名前のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> normalChatName;

    /**
     * 一般 内容のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> normalChatContent;

    /**
     * PT 表示用テーブル.
     */
    @FXML
    private TableView<ChatTable> partyChatTable;

    /**
     * PT 日付のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> partyChatDate;

    /**
     * PT 名前のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> partyChatName;

    /**
     * PT 内容のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> partyChatContent;

    /**
     * ギルチャ 表示用テーブル.
     */
    @FXML
    private TableView<ChatTable> guildChatTable;

    /**
     * ギルチャ 日付のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> guildChatDate;

    /**
     * ギルチャ 名前のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> guildChatName;

    /**
     * ギルチャ 内容のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> guildChatContent;

    /**
     * 耳打ち 表示用テーブル.
     */
    @FXML
    private TableView<ChatTable> mimiChatTable;

    /**
     * 耳打ち 日付のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> mimiChatDate;

    /**
     * 耳打ち 名前のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> mimiChatName;

    /**
     * 耳打ち 内容のカラム.
     */
    @FXML
    private TableColumn<ChatTable, String> mimiChatContent;

    /**
     * アイテム 表示用テーブル.
     */
    @FXML
    private TableView<ItemTable> itemTable;

    /**
     * アイテム 日付のカラム.
     */
    @FXML
    private TableColumn<ItemTable, String> itemDate;

    /**
     * アイテム 名前のカラム.
     */
    @FXML
    private TableColumn<ItemTable, String> itemName;

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
     * クリアボタン.
     */
    @FXML
    private Button clearButton;

    /**
     * メニュー ウインドウ表示.
     */
    @FXML
    private CheckMenuItem oshiraseWindow;

    /**
     * メニュー 叫びON/OFF.
     */
    @FXML
    private CheckMenuItem sakebiMenu;

    /**
     * メニュー 通常チャットON/OFF.
     */
    @FXML
    private CheckMenuItem normalMenu;

    /**
     * メニュー PTチャットON/OFF.
     */
    @FXML
    private CheckMenuItem partyMenu;

    /**
     * メニュー ギルチャON/OFF.
     */
    @FXML
    private CheckMenuItem guildMenu;

    /**
     * メニュー 耳打ちON/OFF.
     */
    @FXML
    private CheckMenuItem mimiMenu;

    /**
     * 透明度変更用スライダ.
     */
    @FXML
    private Slider opacitySlider;

    /**
     * JFXPanel.
     */
    private JFXPanel jfxPanel;

    /**
     * お知らせウインドウ.
     *
     * SwingのJDialog拡張
     */
    private OshiraseJDialog oshiraseJDialog;

    /**
     * エリアチャート.
     */
    @FXML
    private AreaChart<Number, Number> areaChart;

    @Override
    public void concreteInitialize() {

        setService(new PacketAnalyzeService(new ChatLoggerLogic(), getConfig(), PacketAnalyzeService.MODE_ONLINE));

        loadConfig();

        initializeDeviceSetting();

        initializeOshirase();

        initializeChart();

        initializeLogic();
    }

    /**
     * パケット処理関連の業務処理の初期化.
     */
    private void initializeLogic() {

        // コントローラーのフィールドとデータクラスのプロパティーを紐付ける

        ChatLoggerLogic logic = (ChatLoggerLogic) getService().getLogic();

        sakebiChatDate.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("date"));
        sakebiChatName.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("name"));
        sakebiChatContent.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("content"));

        logic.getSakebiChatLogic().setTable(sakebiChatTable);

        normalChatDate.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("date"));
        normalChatName.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("name"));
        normalChatContent.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("content"));

        logic.getNormalChatLogic().setTable(normalChatTable);

        partyChatDate.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("date"));
        partyChatName.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("name"));
        partyChatContent.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("content"));

        logic.getPartyChatLogic().setTable(partyChatTable);

        guildChatDate.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("date"));
        guildChatName.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("name"));
        guildChatContent.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("content"));

        logic.getGuildChatLogic().setTable(guildChatTable);

        mimiChatDate.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("date"));
        mimiChatName.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("name"));
        mimiChatContent.setCellValueFactory(new PropertyValueFactory<ChatTable, String>("content"));

        logic.getMimiChatLogic().setTable(mimiChatTable);

        itemDate.setCellValueFactory(new PropertyValueFactory<ItemTable, String>("date"));
        itemName.setCellValueFactory(new PropertyValueFactory<ItemTable, String>("name"));

        logic.getItemLogic().setItemTable(itemTable);
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

                getService().setDevice(newVal.intValue());

                ObservableList<String> addrs = addressCb.getItems();
                addrs.clear();
                addrs.addAll(getService().getAddressList());

                addressCb.getSelectionModel().selectFirst();
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

        // 開始ボタン押下
        startTb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // イベントの発生元を取得
                ToggleButton toggle = (ToggleButton) event.getSource();

                if (toggle.isSelected()) {
                    // 開始
                    getService().restart();
                } else {
                    // 停止
                    getService().stop();
                }
            }
        });
    }

    /**
     * お知らせ用オブジェクトの初期化.
     */
    private void initializeOshirase() {

        jfxPanel = new JFXPanel();

        oshiraseJDialog = new OshiraseJDialog(getConfig());

        oshiraseJDialog.add(jfxPanel);

        oshiraseJDialog.addLabel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                //oshiraseJDialog.addLabel();

                jfxPanel.setScene(new Scene(new Group()));
            }
        });

        // 別スレッドが持つ値とラベルの値を同期させる
        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                oshiraseJDialog.getLabel().setText(newVal);
            }
        };

        getService().messageProperty().addListener(listener);

        // 初期設定取得
        boolean noticeable = ((ChatLoggerLogic) getService().getLogic()).isNoticeable();
        oshiraseWindow.setSelected(noticeable);
        opacitySlider.setDisable(!noticeable);
        oshiraseJDialog.setVisible(noticeable);
        //jfxPanel.setVisible(noticeable);
        oshiraseWindow.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                ((ChatLoggerLogic) getService().getLogic()).setNoticeable(oshiraseWindow.isSelected());

                // スライダーのenable/disable切り替え
                opacitySlider.setDisable(!oshiraseWindow.isSelected());
                oshiraseJDialog.setVisible(oshiraseWindow.isSelected());
                //jfxPanel.setVisible(oshiraseWindow.isSelected());
            }
        });

        sakebiMenu.setSelected(((ChatLoggerLogic) getService().getLogic()).getSakebiChatLogic().isNoticeable());
        sakebiMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ((ChatLoggerLogic) getService().getLogic()).getSakebiChatLogic().setEnabled(sakebiMenu.isSelected());
            }
        });

        normalMenu.setSelected(((ChatLoggerLogic) getService().getLogic()).getNormalChatLogic().isNoticeable());
        normalMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ((ChatLoggerLogic) getService().getLogic()).getNormalChatLogic().setEnabled(normalMenu.isSelected());
            }
        });

        partyMenu.setSelected(((ChatLoggerLogic) getService().getLogic()).getPartyChatLogic().isNoticeable());
        partyMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ((ChatLoggerLogic) getService().getLogic()).getPartyChatLogic().setEnabled(partyMenu.isSelected());
            }
        });

        guildMenu.setSelected(((ChatLoggerLogic) getService().getLogic()).getGuildChatLogic().isNoticeable());
        guildMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ((ChatLoggerLogic) getService().getLogic()).getGuildChatLogic().setEnabled(guildMenu.isSelected());
            }
        });

        mimiMenu.setSelected(((ChatLoggerLogic) getService().getLogic()).getMimiChatLogic().isNoticeable());
        mimiMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ((ChatLoggerLogic) getService().getLogic()).getMimiChatLogic().setEnabled(mimiMenu.isSelected());
            }
        });

        // クリアボタン押下
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                sakebiChatTable.getItems().clear();
                normalChatTable.getItems().clear();
                partyChatTable.getItems().clear();
                guildChatTable.getItems().clear();
                mimiChatTable.getItems().clear();
            }
        });

        // お知らせウインドウの透明度
        opacitySlider.setMin(0.0d);
        opacitySlider.setMax(1.0d);
        opacitySlider.setValue((double) oshiraseJDialog.getOpacity());
        opacitySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {

                oshiraseJDialog.setOpacity(newVal.floatValue());
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
            @Override public void handle(long now) {
                addDataToSeries();
            }
        } .start();
    }

    /**
     * 別スレッドで更新されているデータを取得し、チャートにデータを追加する.
     */
    private void addDataToSeries() {

        RedstoneLogic redstone = ((ChatLoggerLogic) getService().getLogic()).getRedstoneLogic();

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

        this.saveConfig(getConfig());

        double w = deviceCb.getScene().getWindow().getWidth();
        double h = deviceCb.getScene().getWindow().getHeight();

        setStageWidth(w);
        setStageHeight(h);

        super.saveConfig();
    }

    @Override
    public void loadConfig(Properties config) {

        // インスタンス生成時に設定を読み込む為不要
        //oshiraseJDialog.loadConfig(config);
    }

    @Override
    public void saveConfig(Properties config) {

        oshiraseJDialog.saveConfig(config);
    }

    /**
     * getter.
     *
     * @return oshiraseJDialog
     */
    public OshiraseJDialog getOshiraseJDialog() {
        return oshiraseJDialog;
    }

    /**
     * ステージにイベントを追加する.
     *
     * @param stage
     *            ステージ
     */
    public void setUpStage(Stage stage) {

        // 閉じた時 Swing on JavaFXな場合Application.stop()が呼ばれない

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {

                oshiraseJDialog.dispose();
            }
        });
    }

    /**
     * Application.stopが呼び出された際にコールされる処理.
     */
    public void stop() {

        double w = deviceCb.getScene().getWindow().getWidth();
        double h = deviceCb.getScene().getWindow().getHeight();

        setStageWidth(w);
        setStageHeight(h);

        this.saveConfig();

        getService().stop();
    }
}
