package org.ramidore.logic.chat;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import org.ramidore.bean.ChatTable;
import org.ramidore.core.IConfigurable;
import org.ramidore.core.INoticeable;
import org.ramidore.core.PacketData;
import org.ramidore.logic.AbstractLogic;

/**
 * . チャット関連の基底クラス
 *
 * @author atmark
 *
 */
public abstract class AbstractChatLogic extends AbstractLogic implements INoticeable, IConfigurable {

    /**
     * . Controllerからセットされる
     */
    private TableView<ChatTable> table;

    /**
     * . お知らせ用フラグ
     */
    private boolean enabled = false;

    /**
     * 現在のデータ.
     */
    private ChatTable currentChatData;

    /**
     * . コンストラクタ
     */
    public AbstractChatLogic() {
    }

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

    /* アクセサ */

    /**
     * .
     *
     * @return TableView
     */
    public final TableView<ChatTable> getTable() {
        return table;
    }

    /**
     * .
     *
     *
     * @param tableView
     *            TableView
     */
    public final void setTable(final TableView<ChatTable> tableView) {
        this.table = tableView;
    }

    /**
     * .
     *
     * @param param
     *            boolean
     */
    public final void setEnabled(final boolean param) {
        this.enabled = param;
    }

    /**
     * . accessor
     *
     * @return currentChatData
     */
    public ChatTable getCurrentChatData() {
        return currentChatData;
    }

    /**
     * . accessor
     *
     * @param currentChatData セットする currentChatData
     */
    public void setCurrentChatData(ChatTable currentChatData) {
        this.currentChatData = currentChatData;
    }
}
