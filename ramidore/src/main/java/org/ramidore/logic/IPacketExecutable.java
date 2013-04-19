package org.ramidore.logic;

import org.ramidore.core.PacketData;

/**
 * パケット処理のためのインターフェース.
 *
 * @author atmark
 *
 */
public interface IPacketExecutable {

    /**
     * パケットを処理する.
     *
     * @param data パケットデータ
     * @return 正否
     */
    boolean execute(PacketData data);
}
