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
 *
 */
public class ChatLoggerMain extends Application {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChatLoggerMain.class);

    /**
     * アプリケーション名.
     */
    private static final String APPLICATION_NAME = "ChatLogger";

    /**
     * FXMLのパス.
     */
    private static final String FXML = "/ChatLogger.fxml";

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

        controller = (ChatLoggerController) loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root);

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
     * @param args
     *            引数
     */
    public static void main(final String[] args) {

        launch(args);
    }
}
