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

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapBpfProgram;
import org.ramidore.logic.AbstractMainLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * オフラインキャプチャを行うクラス.
 *
 * @author atmark
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
    public OfflineTask(PcapAddr listenAddress, AbstractMainLogic logic) {

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

        File f = selectFile("PCAP", "*.pcap");

        if (f != null) {
            pcap = Pcap.openOffline(f.getAbsolutePath(), errbuf);
        } else {
            return false;
        }

        if (pcap == null) {
            LOG.error("Error while opening offline file for capture: " + errbuf.toString());

            return false;
        } else {
            LOG.trace("pcap open offline-file : " + f.getAbsolutePath());
        }

        return true;
    }

    /**
     * ファイルを選択する.
     *
     * @param kind         種別
     * @param extensionPat 拡張子(*.foo)
     * @return File
     */
    private File selectFile(String kind, String extensionPat) {

        FileChooser fc = new FileChooser();
        fc.setTitle("読み込むファイルを選択してください。");
        fc.setInitialDirectory(new File(new File(".").getAbsoluteFile().getParent()));
        fc.getExtensionFilters().add(new ExtensionFilter(kind, extensionPat));

        return fc.showOpenDialog(null);
    }

    @Override
    protected boolean setFilter() {

        PcapBpfProgram program = new PcapBpfProgram();

        String expression = "tcp && ((src port 54631) || (src port 56621))";
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
}
