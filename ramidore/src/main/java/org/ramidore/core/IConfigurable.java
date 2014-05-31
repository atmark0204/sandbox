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

package org.ramidore.core;

import java.util.Properties;

/**
 * 設定ファイルを読み書きする為のインターフェース.
 *
 * @author atmark
 */
public interface IConfigurable {

    /**
     * 設定を読み込む.
     *
     * @param config プロパティ
     */
    void loadConfig(Properties config);

    /**
     * 設定を保存する.
     *
     * @param config プロパティ
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
