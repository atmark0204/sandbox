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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * . メインクラス
 *
 * @author atmark
 */
public class ChatLogger extends Application {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChatLogger.class);

    /**
     * アプリケーション名.
     */
    private static final String APPLICATION_NAME = "ChatLogger";

    /**
     * FXMLのパス.
     */
    private static final String FXML = "/chatLogger.fxml";

    /**
     * Controller.
     */
    private ChatLoggerController controller;

    @Override
    public void start(final Stage stage) throws Exception {

        LOG.info("JavaFX " + System.getProperty("javafx.version"));

        stage.setTitle(APPLICATION_NAME);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.load();

        controller = loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root, controller.getStageWidth(), controller.getStageHeight());

        scene.getStylesheets().add("chatlogger.css");

        stage.setScene(scene);

        controller.setUpStage(stage);

        stage.show();
    }

    @Override
    public void stop() {

        LOG.trace("call Main.stop");

        controller.stop();
    }

    /**
     * . メイン
     *
     * @param args 引数
     */
    public static void main(final String[] args) {

        try {
            launch(args);
        } catch (Exception e) {

            LOG.debug(e.getMessage(), e);
        }
    }
}
