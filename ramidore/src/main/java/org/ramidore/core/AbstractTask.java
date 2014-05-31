/*
 * Copyright 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 *
 * @author atmark
 */
public abstract class AbstractTask extends Task<Void> {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractTask.class);

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
    public AbstractTask() {

    }

    @Override
    protected Void call() {

        LOG.trace("call Task.call");

        hookOnTaskStart();

        concreteCall();

        hookOnTaskEnd();

        return null;
    }

    protected void hookOnTaskStart() {
    }

    protected abstract void concreteCall();

    protected void hookOnTaskEnd() {
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
        int optimize = 1;
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
    @SuppressWarnings("rawtypes")
    protected abstract JPacketHandler packetHandlerFactory();
}
