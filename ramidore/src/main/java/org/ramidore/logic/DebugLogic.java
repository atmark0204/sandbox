package org.ramidore.logic;

import org.ramidore.core.PacketData;
import org.ramidore.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * デバッグ用.
 *
 * @author atmark
 *
 */
public class DebugLogic {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(DebugLogic.class);

    /**
     * パケットデータをデバッグ出力する.
     *
     * @param data
     *            パケットデータ
     */
    public void execute(PacketData data) {

        //LOG.debug(FormatUtils.hexdump(data.getRawData()));
        LOG.debug(DebugUtil.hexDump(data));
    }
}
