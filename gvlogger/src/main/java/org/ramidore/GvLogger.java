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
public class GvLogger extends Application {

    /**
     * アプリケーション名.
     */
    private static final String APPLICATION_NAME = "GuildBatte Logger";

    /**
     * FXMLのパス.
     */
    private static final String FXML = "/gvlogger.fxml";

    /**
     * Controller.
     */
    private GvLoggerController controller;

    @Override
    public void start(final Stage stage) throws Exception {

        stage.setTitle(APPLICATION_NAME);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.load();

        controller = (GvLoggerController) loader.getController();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root);

        scene.getStylesheets().add("gvlogger.css");

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
