package org.ramidore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javafx.fxml.Initializable;

import org.apache.commons.io.IOUtils;
import org.ramidore.core.IConfigurable;
import org.ramidore.core.PacketAnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractMainController.
 *
 * UI処理に関係ない部分をまとめる
 *
 * @author atmark
 *
 */
public abstract class AbstractMainController implements Initializable, IConfigurable {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMainController.class);

    /**
     * 設定ファイル名.
     */
    private static final String CONFIG = "config.xml";

    /**
     * 設定.
     */
    private Properties config;

    /**
     * パケット解析サービス.
     *
     * JavaFXアプリケーションスレッドとは異なるスレッドで実行される
     */
    private org.ramidore.core.PacketAnalyzeService service;

    @Override
    public final void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        this.loadConfig();

        concreteInitialize();
    }

    /**
     * 実初期化メソッド.
     */
    abstract void concreteInitialize();

    @Override
    public void loadConfig() {

        LOG.trace("load setting");

        // ファイルのロード 無い場合空のプロパティ
        config = new Properties();

        try {
            InputStream in = new FileInputStream(CONFIG);

            // 入力ストリームはloadFromXMLでクローズされる
            config.loadFromXML(in);
        } catch (FileNotFoundException e) {
            LOG.debug(CONFIG + " is not found");
        } catch (InvalidPropertiesFormatException e) {
            LOG.debug(CONFIG + " is invalid format");
        } catch (IOException e) {
            LOG.debug("io error on loading " + CONFIG);
        }
    }

    @Override
    public void saveConfig() {

        service.saveConfig(config);

        // ファイルのセーブ
        OutputStream os = null;
        try {
            // 出力ストリーム構築
            os = new FileOutputStream(CONFIG);
            // XML形式のプロパティファイルを出力
            config.storeToXML(os, null);
        } catch (IOException e) {
            LOG.debug("io error on saving " + CONFIG);
        } finally {
            // 出力ストリームをクローズ
            IOUtils.closeQuietly(os);
        }

        LOG.trace("save config");
    }

    /**
     * getter.
     *
     * @return config
     */
    public Properties getConfig() {
        return config;
    }

    /**
     * setter.
     *
     * @param config
     *            セットする config
     */
    public void setConfig(Properties config) {
        this.config = config;
    }

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
    public static Logger getLog() {
        return LOG;
    }

}
