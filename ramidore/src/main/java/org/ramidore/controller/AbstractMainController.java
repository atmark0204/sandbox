package org.ramidore.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.ramidore.core.IConfigurable;

/**
 * AbstractMainController.
 *
 * UI処理に関係ない部分をまとめる
 *
 * @author atmark
 *
 */
public abstract class AbstractMainController extends AbstractController implements IConfigurable {

    /**
     * 設定ファイル名.
     */
    private static final String CONFIG = "config.xml";

    /**
     * 設定.
     */
    private Properties config;

    /**
     * ステージの横幅.
     */
    private double stageWidth;

    /**
     * ステージの縦幅.
     */
    private double stageHeight;

    @Override
    public final void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        this.loadConfig();

        concreteInitialize();
    }

    @Override
    public void loadConfig() {

        getLOG().trace("load setting");

        // ファイルのロード 無い場合空のプロパティ
        config = new Properties();

        try {
            InputStream in = new FileInputStream(CONFIG);

            // 入力ストリームはloadFromXMLでクローズされる
            config.loadFromXML(in);
        } catch (FileNotFoundException e) {
            getLOG().debug(CONFIG + " is not found");
        } catch (InvalidPropertiesFormatException e) {
            getLOG().debug(CONFIG + " is invalid format");
        } catch (IOException e) {
            getLOG().debug("io error on loading " + CONFIG);
        }

        stageWidth = Double.parseDouble(config.getProperty("stageWidth", "640"));
        stageHeight = Double.parseDouble(config.getProperty("stageHeight", "480"));
    }

    @Override
    public void saveConfig() {

        config.setProperty("stageWidth", String.valueOf(stageWidth));
        config.setProperty("stageHeight", String.valueOf(stageHeight));

        getService().saveConfig(config);

        // ファイルのセーブ
        OutputStream os = null;
        try {
            // 出力ストリーム構築
            os = new FileOutputStream(CONFIG);
            // XML形式のプロパティファイルを出力
            config.storeToXML(os, null);
        } catch (IOException e) {
            getLOG().debug("io error on saving " + CONFIG);
        } finally {
            // 出力ストリームをクローズ
            IOUtils.closeQuietly(os);
        }

        getLOG().trace("save config");
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
     * @return stageWidth
     */
    public double getStageWidth() {
        return stageWidth;
    }

    /**
     * setter.
     *
     * @param stageWidth セットする stageWidth
     */
    public void setStageWidth(double stageWidth) {
        this.stageWidth = stageWidth;
    }

    /**
     * getter.
     *
     * @return stageHeight
     */
    public double getStageHeight() {
        return stageHeight;
    }

    /**
     * setter.
     *
     * @param stageHeight セットする stageHeight
     */
    public void setStageHeight(double stageHeight) {
        this.stageHeight = stageHeight;
    }

}
