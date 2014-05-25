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
 *
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
     * @param data
     *            データ
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
     * @param logicList
     *            ChatLogicのリスト
     * @param data
     *            パケットデータ
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
