package org.ramidore.core;

import javafx.concurrent.Task;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基底タスク.

 * @author atmark
 *
 */
public abstract class RamidoreTask extends Task<Void> {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RamidoreTask.class);

    /**
     * Pcapオブジェクト.
     */
    protected Pcap pcap;

    /**
     * Listenするアドレス.
     */
    protected PcapAddr listenAddress;

    /**
     * コンストラクタ.
     */
    public RamidoreTask() {

    }

    /**
     * フィルタをセットする.
     *
     * @return 正否
     */
    protected boolean setFilter() {

        PcapBpfProgram program = new PcapBpfProgram();

        String dstAddr = FormatUtils.ip(listenAddress.getAddr().getData());

        String expression = "tcp && (dst net " + dstAddr + ") && ((src port 54631) || (src port 56621))";
        int optimize = 0;
        int netmask = 0xFFFFFF00;

        if (pcap.compile(program, expression, optimize, netmask) != Pcap.OK) {

            LOG.error(pcap.getErr());

            return false;
        }

        if (pcap.setFilter(program) != Pcap.OK) {

            LOG.error(pcap.getErr());

            return false;
        }

        LOG.debug("set filter : " + expression);

        return true;
    }

    public abstract void stop();

    /**
     * カレントデバイスをクローズする.
     */
    protected void close() {

        pcap.close();

        LOG.trace("pcap close");
    }

    /**
     * パケットハンドラを生成するファクトリ.
     *
     * @return パケットハンドラ
     */
    protected abstract JPacketHandler packetHandlerFactory();
}
