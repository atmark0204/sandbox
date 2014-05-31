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

package org.ramidore.util;

import java.io.UnsupportedEncodingException;

/**
 * . 共通ロジック
 *
 * @author atmark
 */
public final class RamidoreUtil {

    /**
     * . コンストラクタ
     */
    private RamidoreUtil() {
    }

    /**
     * . 16進数文字列をエンコードする
     *
     * @param target 16進文字列
     * @return 文字列
     */
    public static String encode(String target, String encoding) {

        byte[] bytes = new byte[target.length() / 2];

        for (int index = 0; index < bytes.length; index++) {

            bytes[index] = (byte) Integer.parseInt(target.substring(index * 2, (index + 1) * 2), 16);
        }

        String result;

        try {

            result = new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {

            result = "エンコード失敗";
        }

        return result;
    }

    /**
     * . 16進バイト列を16進文字列に変換
     *
     * @param b 16進バイト列
     * @return 16進文字列
     */
    public static String toHex(final byte[] b) {

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < b.length; i++) {

            String hex = String.format("%02X", b[i]);

            buf.append(hex);
        }

        return buf.toString();
    }

    /**
     * . 昇順16進文字列からintに変換
     *
     * @param hexStr 16進文字列
     * @return int
     */
    public static int intValueFromAscHexString(final String hexStr) {

        return Integer.parseInt(hexStr, 16);
    }

    /**
     * . 降順16進文字列からintに変換
     *
     * @param hexStr 16進文字列
     * @return int
     */
    public static int intValueFromDescHexString(final String hexStr) {

        StringBuilder builder = new StringBuilder();

        for (int i = hexStr.length() - 2; i >= 0; i -= 2) {

            builder.append(hexStr.substring(i, i + 2));
        }

        return intValueFromAscHexString(builder.toString());
    }

    /**
     * 降順16進バイト列からintに変換.
     *
     * @param b バイト列
     * @return int
     */
    public static int intValueFromDescByteArray(byte[] b) {

        return intValueFromDescHexString(toHex(b));
    }
}
