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

package org.ramidore.logic.chat;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ramidore.bean.ChatTable;
import org.ramidore.core.IConfigurable;
import org.ramidore.core.INoticeable;
import org.ramidore.core.PacketData;
import org.ramidore.logic.AbstractLogic;

import java.util.List;

/**
 * . チャット関連の基底クラス
 *
 * @author atmark
 */
@NoArgsConstructor
public abstract class AbstractChatLogic extends AbstractLogic implements INoticeable, IConfigurable {

    /**
     * . Controllerからセットされる
     */
    @Getter
    @Setter
    private TableView<ChatTable> table;

    /**
     * . お知らせ用フラグ
     */
    @Setter
    private boolean enabled = false;

    /**
     * 現在のデータ.
     */
    @Getter
    @Setter
    private ChatTable currentChatData;

    /**
     * . 表示用テーブルにデータを追加する
     *
     * @param data データ
     */
    public void addData(final ChatTable data) {

        ObservableList<ChatTable> list = table.getItems();

        if (enabled) {
            currentChatData = data;
        }

        list.add(data);
    }

    /**
     * . 各ChatLogicをまとめて実行する いずれかが実行されれば戻る
     *
     * @param logicList ChatLogicのリスト
     * @param data      パケットデータ
     */
    public static void executeAll(final List<AbstractChatLogic> logicList, final PacketData data) {

        for (AbstractChatLogic logic : logicList) {

            if (logic.execute(data)) {

                return;
            }
        }
    }

    @Override
    public String getOshiraseMessage() {

        if (currentChatData == null) {

            return "";
        } else {

            return currentChatData.toString();
        }
    }

    @Override
    public final boolean isNoticeable() {
        return enabled;
    }
}
