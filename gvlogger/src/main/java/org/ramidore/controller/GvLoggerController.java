/*
 * Copyright 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ramidore.controller;

import com.sun.javafx.scene.control.skin.TabPaneSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.ramidore.bean.GvLogTable;
import org.ramidore.bean.GvStatTable;
import org.ramidore.core.PacketAnalyzeService;
import org.ramidore.logic.GvLoggerLogic;
import org.ramidore.logic.system.GuildBattleLogLogic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * . JavaFXコントローラクラス
 *
 * @author atmark
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
     * 統計情報保存ボタン.
     */
    @FXML
    private Button saveStatDataB;

    /**
     * TabPane.
     */
    @FXML
    private TabPane tabPane;

    /**
     * タブのFXMLファイルへのパス.
     */
    private static final String TAB_FXML = "/fxml/tab.fxml";

    /**
     * 各タブのコントローラマップ.
     * <p>
     * key : タブ名
     * value : コントローラ
     */
    private Map<String, Object> tabControllerMap;

    @Override
    public void concreteInitialize() {

        // サービス生成
        setService(new PacketAnalyzeService(new GvLoggerLogic(), getConfig()));

        loadConfig();

        initializeDeviceSetting();

        tabControllerMap = new HashMap<>();

        tabPane.getTabs().add(new Tab("realtime"));

        tabPane.getSelectionModel().clearSelection();

        // Add Tab ChangeListener
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                System.out.println("Tab selected: " + newValue.getText());
                if (newValue.getContent() == null) {
                    try {
                        // ここで初期化しないとだめ
                        FXMLLoader fXMLLoader = new FXMLLoader();

                        // Loading content on demand
                        Node root = fXMLLoader.load(this.getClass().getResource(TAB_FXML).openStream());
                        newValue.setContent(root);

                        // OPTIONAL : Store the controller if needed
                        GvLoggerTabController controller = fXMLLoader.getController();

                        controller.setService(getService());

                        ConcurrentLinkedQueue<GvLogTable> logDataQ;

                        if ("realtime".equals(newValue.getText())) {
                            logDataQ = ((GvLoggerLogic) getService().getLogic()).getGuildBattleLogic().getLogDataQ();
                        } else {
                            logDataQ = ((GvLoggerLogic) getService().getLogic()).getGuildBattleLogMap().get(newValue.getText()).getLogDataQ();
                        }
                        controller.setLogDataQ(logDataQ);

                        tabControllerMap.put(newValue.getText(), fXMLLoader.getController());

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
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

            // 新しいデバイスを設定
            getService().setDevice(newVal.intValue());

            // ipアドレス一覧をクリア後更新
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

        captureModeCb.getItems().addAll(PacketAnalyzeService.MODE);
        captureModeCb.getSelectionModel().selectFirst();
        captureModeCb.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> {

            if (newVal.intValue() != -1) {
                getService().setMode(newVal.intValue());
            }
        });

        // 開始ボタン押下
        startTb.setOnAction(event -> {
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
        });

        // 過去データ読み込みボタン押下
        loadPastDataB.setOnAction(ae -> {

            FileChooser fc = new FileChooser();
            fc.setTitle("select file");
            fc.setInitialDirectory(new File(new File(".").getAbsoluteFile().getParent()));
            fc.getExtensionFilters().add(new ExtensionFilter("LOG", "*.log"));

            File f = fc.showOpenDialog(null);

            if (f != null) {

                Tab tab = new Tab(f.getName());
                tab.setClosable(false);
                setClosableButton(tab);

                tabPane.getTabs().add(tab);

                ((GvLoggerLogic) getService().getLogic()).loadPastData(f);

                tabPane.getSelectionModel().select(tab);
            }
        });

        // 統計情報保存ボタン押下
        saveStatDataB.setOnAction(event -> {

            // タブ名
            String key = tabPane.getSelectionModel().selectedItemProperty().get().getText();

            GvLoggerTabController tabController = (GvLoggerTabController) tabControllerMap.get(key);

            FileChooser fc = new FileChooser();
            fc.setTitle("保存するファイル名を入力してください。");
            // デフォルトのファイル名は設定できない
            // JavaFX 3.x で修正予定らしい
            fc.setInitialDirectory(new File(new File(".").getAbsoluteFile().getParent()));
            fc.getExtensionFilters().add(new ExtensionFilter("TEXT", ".txt"));

            File f = fc.showSaveDialog(null);

            if (f != null) {
                ObservableList<GvStatTable> items = tabController.getStatTable().getItems();
                GuildBattleLogLogic.saveStatData(items, f);
            }
        });
    }

    /**
     * タブに閉じるボタンを追加する.
     *
     * @param tab Tab
     */
    private void setClosableButton(final Tab tab) {
        final StackPane closeBtn = new StackPane() {
            @Override
            protected void layoutChildren() {
                super.layoutChildren();
                // Setting the orientation of graphic(button) to the right side.
                getParent().setStyle("-fx-content-display:right;");
            }
        };
        closeBtn.getStyleClass().setAll(new String[]{"tab-close-button"});
        closeBtn.setStyle("-fx-cursor:hand;");
        closeBtn.setPadding(new Insets(0, 7, 0, 7));
        closeBtn.visibleProperty().bind(tab.selectedProperty());

        final EventHandler<ActionEvent> closeEvent = paramT -> ((TabPaneSkin) tabPane.getSkin()).getBehavior().closeTab(tab);

        // Handler for the close button.
        closeBtn.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent paramT) {
                // My logic to handle the close event or not.
                closeEvent.handle(null);
            }
        });

        // Showing the close button if the tab is selected.
        tab.selectedProperty().addListener((paramObservableValue, paramT1, isSelected) -> {
            if (isSelected) {
                tab.setGraphic(closeBtn);
            } else {
                tab.setGraphic(null);
            }
        });
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
