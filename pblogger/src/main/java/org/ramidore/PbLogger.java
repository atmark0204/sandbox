package org.ramidore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * . メインクラス
 *
 * @author atmark
 *
 */
public class PbLogger extends Application {

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

        stage.setTitle(APPLICATION_NAME);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.load();

        controller = (PbLoggerController) loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root, controller.getStageWidth(), controller.getStageHeight());

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

        System.loadLibrary("jnetpcap");

        launch(args);
    }
}
