package org.ramidore.core;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ダンプファイル作成用.
 *
 * @author atmark
 *
 */
public class DumperTask extends OnlineTask {

    /**
     * . Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(DumperTask.class);

    /**
     * ダンパー.
     */
    private PcapDumper dumper = null;

    /**
     * コンストラクタ.
     *
     * @param device デバイス
     * @param listenAddress listenするアドレス.
     */
    public DumperTask(PcapIf device, PcapAddr listenAddress) {

        super(device, listenAddress);

        dumper = new PcapDumper();
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected JPacketHandler packetHandlerFactory() {
        return new JPacketHandler<PcapDumper>() {
            @Override
            public void nextPacket(JPacket packet, PcapDumper dumper) {
                dumper.dump(packet.getCaptureHeader(), packet);
            }
        };
    }

    @Override
    protected void hookOnTaskStart() {
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void concreteCall() {

        if (open() && setFilter()) {

            pcap.loop(Pcap.LOOP_INFINITE, packetHandlerFactory(), dumper);
        }
    }

    @Override
    protected boolean open() {

        if (!super.open()) {

            return false;
        }

        File f = saveFile("PCAP", "*.pcap");

        if (f != null) {
            dumper = pcap.dumpOpen(f.getAbsolutePath());

            LOG.trace("pcap open dump-file : " + f.getAbsolutePath());

            return true;
        } else {

            LOG.trace("pcap open dump-file failed");

            return false;
        }
    }

    @Override
    public void stop() {

        // キャプチャ開始前に終了する場合nullチェック
        if (pcap != null) {
            // pcap.loopを抜けてclose()を呼ぶ
            pcap.breakloop();

            close();

            pcap = null;
        }

    }

    @Override
    protected void close() {

        dumper.close();
        pcap.close();
    }

    /**
     * ファイル保存ダイアログを開く.
     *
     * @param kind ファイル種別
     * @param extensionPat 拡張子(*.foo)
     * @return File
     */
    private File saveFile(String kind, String extensionPat) {

        FileChooser fc = new FileChooser();
        fc.setTitle("select file");
        fc.setInitialDirectory(new File(new File(".").getAbsoluteFile().getParent()));
        fc.getExtensionFilters().add(new ExtensionFilter(kind, extensionPat));

        return fc.showSaveDialog(null);
    }

}
