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
 *
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
