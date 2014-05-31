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

package org.ramidore;

/**
 * 定数.
 *
 * @author atmark
 */
public final class Const {

    /**
     * 0x00以外の16進文字列にマッチする正規表現パターン.
     */
    public static final String BASE_PATTERN = "((?:[1-9A-F]{2}|[1-9A-F]0|0[1-9A-F])+)";

    /**
     * . 日本語エンコードはWindows-31J
     * <p>
     * ※ SJISだと機種依存文字が化ける
     */
    public static final String ENCODING = "Windows-31J";

    /**
     * プライベートコンストラクタ.
     */
    private Const() {
    }
}
