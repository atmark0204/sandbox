package org.ramidore.controller;

import javafx.fxml.Initializable;

import org.ramidore.core.PacketAnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractController.
 *
 * サブのコントローラ
 *
 * @author atmark
 *
 */
public abstract class AbstractController implements Initializable {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

    /**
     * パケット解析サービス.
     *
     * JavaFXアプリケーションスレッドとは異なるスレッドで実行される
     */
    private PacketAnalyzeService service;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        concreteInitialize();
    }

    /**
     * 実初期化メソッド.
     */
    abstract public void concreteInitialize();


    /**
     * getter.
     *
     * @return service
     */
    public PacketAnalyzeService getService() {
        return service;
    }

    /**
     * setter.
     *
     * @param service セットする service
     */
    public void setService(PacketAnalyzeService service) {
        this.service = service;
    }

    /**
     * getter.
     *
     * @return log
     */
    public static Logger getLOG() {
        return LOG;
    }
}
