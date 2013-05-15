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
public class RsMonitor extends Application {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(RsMonitor.class);

    /**
     * アプリケーション名.
     */
    private static final String APPLICATION_NAME = "Redstone Monitor";

    /**
     * FXMLのパス.
     */
    private static final String FXML = "/rsmon.fxml";

    /**
     * Controller.
     */
    private RsMonitorController controller;

    @Override
    public void start(final Stage stage) throws Exception {

        LOG.info("JavaFX " + System.getProperty("javafx.version"));

        stage.setTitle(APPLICATION_NAME);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.load();

        controller = (RsMonitorController) loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root, controller.getStageWidth(), controller.getStageHeight());

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
