package org.ramidore.core;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.ramidore.logic.AbstractRedomiraLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * オフラインキャプチャを行うクラス.
 *
 * @author atmark
 *
 */
public class OfflineTask extends OnlineTask {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(OfflineTask.class);

    /**
     * コンストラクタ.
     *
     * @param listenAddress
     * @param logic
     */
    public OfflineTask(PcapAddr listenAddress, AbstractRedomiraLogic logic) {

        super(null, listenAddress, logic);
    }

    /**
     * オフラインファイルをオープンする.
     *
     * @return 正否
     */
    @Override
    protected boolean open() {

        StringBuilder errbuf = new StringBuilder();

        pcap = Pcap.openOffline("dump.pcap", errbuf);

        if (pcap == null) {
            LOG.error("Error while opening offline file for capture: " + errbuf.toString());

            return false;
        } else {
            LOG.trace("pcap open");
        }

        return true;
    }
}
