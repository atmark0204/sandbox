package org.ramidore.core;

import java.util.Date;

/**
 * パケットモデル.
 *
 * @author atmark
 *
 */
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
     * @param date
     *            受信時
     *
     * @param b
     *            hexバイト列
     */
    public PacketData(Date date, final byte[] b) {

        this.date = date;

        this.rawData = b;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < b.length; i++) {

            builder.append(String.format("%02X", b[i]));
        }

        strData = builder.toString();
    }

    /**
     * getter.
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * setter.
     *
     * @param date
     *            セットする date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * getter.
     *
     * @return rawData
     */
    public byte[] getRawData() {
        return rawData;
    }

    /**
     * setter.
     *
     * @param rawData
     *            セットする rawData
     */
    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    /**
     * getter.
     *
     * @return strData
     */
    public String getStrData() {
        return strData;
    }

    /**
     * setter.
     *
     * @param strData
     *            セットする strData
     */
    public void setStrData(String strData) {
        this.strData = strData;
    }

}
