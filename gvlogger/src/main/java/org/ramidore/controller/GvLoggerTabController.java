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

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.ramidore.bean.GvLogTable;
import org.ramidore.bean.GvStatTable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static javafx.scene.chart.XYChart.Series;

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
    @Getter
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
    private Map<String, GvStatTable> statMap = new ConcurrentHashMap<>();

    /**
     * データキュー.
     */
    @Setter
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

        logDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        logSrcCharaCol.setCellValueFactory(new PropertyValueFactory<>("srcCharaName"));
        logDstCharaCol.setCellValueFactory(new PropertyValueFactory<>("dstCharaName"));
        logGuildCol.setCellValueFactory(new PropertyValueFactory<>("guildName"));
        logPointCol.setCellValueFactory(new PropertyValueFactory<>("point"));
        logPoint0Col.setCellValueFactory(new PropertyValueFactory<>("point0"));
        logPoint1Col.setCellValueFactory(new PropertyValueFactory<>("point1"));

        statTable.setEditable(true);
        statGuildCol.setCellValueFactory(new PropertyValueFactory<>("guildName"));
        statCharaCol.setCellValueFactory(new PropertyValueFactory<>("charaName"));
        statKillCountCol.setCellValueFactory(new PropertyValueFactory<>("killCount"));
        statDeathCountCol.setCellValueFactory(new PropertyValueFactory<>("deathCount"));
        statPointCol.setCellValueFactory(new PropertyValueFactory<>("point"));
        statNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        statNoteCol.setCellFactory(arg0 -> new TextFieldTableCell<>(new DefaultStringConverter()));

        statNoteCol.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setNote(t.getNewValue()));
    }

    /**
     * チャート初期化.
     */
    @SuppressWarnings("unchecked")
    private void initializeChart() {

        Series<String, Number> series0 = new Series<>();
        Series<String, Number> series1 = new Series<>();
        series0.setName("先入れ側");
        series1.setName("後入れ側");

        timelineChart.getData().add(series0);
        timelineChart.getData().add(series1);

        Series<String, Number> series = new Series<>();
        series.setName("個人成績");

        personChart.getData().setAll(series);

        personChartCb.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> display(newVal.intValue()));

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

        Series<String, Number> series = personChart.getData().get(0);
        series.getData().clear();

        statTable.getItems().stream().filter(row -> row.getGuildName() == guild).forEach(row -> series.getData().add(row.toBarChartData()));
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

            timelineChart.getData().get(0).setName(log.getStrictGuildName0());
            timelineChart.getData().get(1).setName(log.getStrictGuildName1());

            personChartCb.getItems().set(0, log.getStrictGuildName0());
            personChartCb.getItems().set(1, log.getStrictGuildName1());

            return;
        }

        logTable.getItems().add(log);

        GvStatTable statSrc;
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

        GvStatTable statDst;
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
}
