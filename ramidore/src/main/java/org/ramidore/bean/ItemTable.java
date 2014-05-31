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

package org.ramidore.bean;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拾得アイテム表示用テーブル.
 *
 * @author atmark
 */
@Getter
@Setter
public class ItemTable {

    /**
     * 日付のフォーマット.
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 日付.
     */
    private String date;

    /**
     * アイテム名.
     */
    private String name;

    /**
     * コンストラクタ.
     *
     * @param date 日付
     * @param name アイテム名
     */
    public ItemTable(Date date, String name) {

        this.date = DATE_FORMAT.format(date);
        this.name = name;
    }
}
