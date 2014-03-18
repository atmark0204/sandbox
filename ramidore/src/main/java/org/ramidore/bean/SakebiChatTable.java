package org.ramidore.bean;

import java.util.Date;

/**
 * . 各種チャットのデータモデル
 *
 * @author atmark
 *
 */
public class SakebiChatTable extends ChatTable {

    /**
     * . コンストラクタ
     *
     * @param date
     *            日付
     * @param prefix
     *            プレフィックス
     * @param name
     *            発言者
     * @param content
     *            発言内容
     */
    public SakebiChatTable(final Date date, final String prefix, final String name, final String content) {
    	super(date, prefix, name, content);
    }

    /**
     * . コンストラクタ
     *
     * @param name
     *            発言者
     * @param content
     *            発言内容
     */
    public SakebiChatTable(final String name, final String content) {
    	super(name, content);
    }

    /**
     * . コンストラクタ
     *
     * @param date
     *            日付
     * @param name
     *            発言者
     * @param content
     *            発言内容
     */
    public SakebiChatTable(final Date date, final String name, final String content) {
    	super(date, name, content);
    }

    @Override
    public String toString() {

        return "<font color=\"#FFD700\">&nbsp;&nbsp&lt;!&gt;</font>" +
        		"<font color=\"#32CD32\">&nbsp;" + getName() + "</font>" +
        		"<font color=\"#FFFFFF\">&nbsp;" + getContent() + "</font>";
    }
}
