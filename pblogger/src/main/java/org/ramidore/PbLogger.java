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
public class PbLogger extends Application {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(PbLogger.class);

    /**
     * アプリケーション名.
     */
    private static final String APPLICATION_NAME = "PointBatte Logger";

    /**
     * FXMLのパス.
     */
    private static final String FXML = "/pblogger.fxml";

    /**
     * Controller.
     */
    private PbLoggerController controller;

    @Override
    public void start(final Stage stage) throws Exception {

        LOG.info("JavaFX " + System.getProperty("javafx.version"));

        stage.setTitle(APPLICATION_NAME);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.load();

        controller = (PbLoggerController) loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root);

        scene.getStylesheets().add("pblogger.css");

        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void stop() {

        controller.saveConfig();

        controller.getService().stop();
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
