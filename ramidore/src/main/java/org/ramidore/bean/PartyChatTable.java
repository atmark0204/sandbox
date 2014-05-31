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

import java.util.Date;

/**
 * . 各種チャットのデータモデル
 *
 * @author atmark
 */
public class PartyChatTable extends ChatTable {

    /**
     * . コンストラクタ
     *
     * @param date    日付
     * @param prefix  プレフィックス
     * @param name    発言者
     * @param content 発言内容
     */
    public PartyChatTable(final Date date, final String prefix, final String name, final String content) {
        super(date, prefix, name, content);
    }

    /**
     * . コンストラクタ
     *
     * @param name    発言者
     * @param content 発言内容
     */
    public PartyChatTable(final String name, final String content) {
        super(name, content);
    }

    /**
     * . コンストラクタ
     *
     * @param date    日付
     * @param name    発言者
     * @param content 発言内容
     */
    public PartyChatTable(final Date date, final String name, final String content) {
        super(date, name, content);
    }

    @Override
    public String toString() {

        return "<font color=\"#32CD32\">&nbsp;" + getName() + "</font>" +
                "<font color=\"#9400D3\">&nbsp;" + getContent() + "</font>";
    }
}
