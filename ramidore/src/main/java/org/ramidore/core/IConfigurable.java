package org.ramidore.core;

import java.util.Properties;

/**
 * 設定ファイルを読み書きする為のインターフェース.
 *
 * @author atmark
 *
 */
public interface IConfigurable {

    /**
     * 設定を読み込む.
     *
     * @param config
     *            プロパティ
     */
    void loadConfig(Properties config);

    /**
     * 設定を保存する.
     *
     * @param config
     *            プロパティ
     */
    void saveConfig(Properties config);

    // TODO 以下ルートでしか使用しないため別IF化したほうがいいかも

    /**
     * 設定ファイルを読み込む.
     */
    void loadConfig();

    /**
     * 設定ファイルを保存する.
     */
    void saveConfig();
}
