package org.ramidore.bean;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * . 各種チャットのデータモデル
 *
 * @author atmark
 *
 */
@Getter
@Setter
public class ChatTable {

    /**
     * . 日付
     */
    private String date;

    /**
     * prefix.
     */
    private String prefix = "";

    /**
     * . 発言者
     */
    private String name;

    /**
     * . 発言内容
     */
    private String content;

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
    public ChatTable(final Date date, final String prefix, final String name, final String content) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        this.date = df.format(date);
        this.name = name;
        this.content = content;

        this.prefix = prefix;
    }

    /**
     * . コンストラクタ
     *
     * @param name
     *            発言者
     * @param content
     *            発言内容
     */
    public ChatTable(final String name, final String content) {

        this(new Date(), "", name, content);
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
    public ChatTable(final Date date, final String name, final String content) {

        this(date, "", name, content);
    }

    @Override
    public String toString() {

        return prefix + "【" + name + "】 " + content;
    }
}
