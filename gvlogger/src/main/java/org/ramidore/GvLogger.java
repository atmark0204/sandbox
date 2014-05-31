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

package org.ramidore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.ramidore.controller.GvLoggerController;

/**
 * . メインクラス
 *
 * @author atmark
 *
 */
public class GvLogger extends Application {

    /**
     * アプリケーション名.
     */
    private static final String APPLICATION_NAME = "gvlogger";

    /**
     * バージョン名.
     */
    private static final String VERSION = "0.1.1";

    /**
     * FXMLのパス.
     */
    private static final String FXML = "/fxml/gvlogger.fxml";

    /**
     * CSSファイルのパス.
     */
    private static final String CSS = "/styles/gvlogger.css";

    /**
     * Controller.
     */
    private GvLoggerController controller;

    @Override
    public void start(final Stage stage) throws Exception {

        stage.setTitle(APPLICATION_NAME + " " + VERSION);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.load();

        controller = (GvLoggerController) loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root, controller.getStageWidth(), controller.getStageHeight());

        scene.getStylesheets().add(CSS);

        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void stop() {

        controller.stop();
    }

    /**
     * . メイン
     *
     * @param args
     *            引数
     */
    public static void main(final String[] args) {

        System.loadLibrary("jnetpcap");

        launch(args);
    }
}
