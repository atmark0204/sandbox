package org.ramidore.core;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

/**
 * ダンプファイル作成用.
 *
 * バグあり
 *
 * @author atmark
 *
 */
@Deprecated
public class DumperTask extends OnlineTask {

    /**
     * ダンパー.
     */
    private PcapDumper dumper;

    /**
     * ハンドラ.
     */
    private PcapPacketHandler<PcapDumper> dumpHandler;

    /**
     * コンストラクタ.
     *
     * @param device デバイス
     * @param listenAddress listenするアドレス.
     */
    public DumperTask(PcapIf device, PcapAddr listenAddress) {

        super(device, listenAddress);

        setHandler();
    }

    /**
     * ハンドラを設定する.
     */
    protected void setHandler() {
        dumpHandler = new PcapPacketHandler<PcapDumper>() {
            @Override
            public void nextPacket(PcapPacket packet, PcapDumper dumper) {
                dumper.dump(packet.getCaptureHeader(), packet);
            }
        };
    }

    @Override
    protected Void call() {

        if (open() && setFilter()) {

            pcap.loop(Pcap.LOOP_INFINITE, dumpHandler, dumper);
        }

        return null;
    }

    @Override
    protected boolean open() {

        boolean result = super.open();

        dumper = pcap.dumpOpen("dump.pcap");

        return result;
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

}
