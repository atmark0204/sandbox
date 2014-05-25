package org.ramidore.core;

import lombok.Getter;
import lombok.Setter;
import org.ramidore.util.RamidoreUtil;

import java.util.Date;

/**
 * パケットモデル.
 *
 * @author atmark
 *
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
     * @param date
     *            受信時
     *
     * @param b
     *            hexバイト列
     */
    public PacketData(Date date, final byte[] b) {

        this.date = date;

        this.rawData = b;

        strData = RamidoreUtil.toHex(b);
    }
}
