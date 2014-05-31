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

import lombok.Getter;
import lombok.Setter;
import org.ramidore.util.RamidoreUtil;

import java.util.Date;

/**
 * パケットモデル.
 *
 * @author atmark
 */
@Getter
@Setter
public class PacketData {

    /**
     * 日付.
     */
    private Date date;

    /**
     * 生のバイト列.
     */
    private byte[] rawData;

    /**
     * 16進数に変換した文字列.
     */
    private String strData;

    /**
     * コンストラクタ.
     *
     * @param date 受信時
     * @param b    hexバイト列
     */
    public PacketData(Date date, final byte[] b) {

        this.date = date;

        this.rawData = b;

        strData = RamidoreUtil.toHex(b);
    }
}
