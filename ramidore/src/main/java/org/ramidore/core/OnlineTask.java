package org.ramidore.core;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.ramidore.logic.AbstractRedomiraLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * パケットキャプチャの開始～終了までのタスク.
 *
 * @author atmark
 *
 */
public class OnlineTask extends RamidoreTask {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(OnlineTask.class);

    /**
     * カレントネットワークデバイス.
     */
    private PcapIf device;

    /**
     * パケットハンドラ.
     */
    private JPacketHandler<AbstractRedomiraLogic> packetHandler;

    /**
     * ロジック.
     */
    private AbstractRedomiraLogic logic;

    /**
     * コンストラクタ.
     *
     * @param device PcapIfオブジェクト
     * @param listenAddress listenするアドレス
     * @param logic ロジック
     */
    public OnlineTask(PcapIf device, PcapAddr listenAddress, AbstractRedomiraLogic logic) {

        this.device = device;
        this.listenAddress = listenAddress;
        this.logic = logic;

        setHandler();
    }

    /**
     * コンストラクタ.
     *
     * @param device PcapIfオブジェクト
     * @param listenAddress listenするアドレス
     */
    public OnlineTask(PcapIf device, PcapAddr listenAddress) {

        this.device = device;
        this.listenAddress = listenAddress;
    }
    /**
     * . パケットのハンドラを設定
     */
    protected void setHandler() {

        // キャプチャしたパケットのハンドル
        packetHandler = new JPacketHandler<AbstractRedomiraLogic>() {

            private Tcp tcp = new Tcp();

            @Override
            public void nextPacket(JPacket p, AbstractRedomiraLogic logic) {

                try {

                    if (!p.hasHeader(tcp)) {
                        return;
                    }

                    // p.getByteArray(0, b); イーサネットフレームも含めたバイト列が返されてしまうので注意

                    // TCPペイロード(アプリケーション層のデータ)を取得している
                    byte[] b = tcp.getPayload();

                    PacketData data = new PacketData(new Date(p.getCaptureHeader().timestampInMillis()), b);

                    // パケットの判別ロジックは以下に集約
                    if (logic.execute(data)) {

                        // JavaFXのスレッド以外から更新する場合必ずupdateMessage経由でやる
                        OnlineTask.this.updateMessage(logic.getOshiraseMessage());
                    }

                } catch (Exception e) {

                    LOG.error(ExceptionUtils.getStackTrace(e));
                }
            }
        };
    }

    @Override
    protected Void call() {

        LOG.trace("call Task.call");

        if (open() && setFilter()) {

            pcap.loop(Pcap.LOOP_INFINITE, packetHandler, logic);
        }

        return null;
    }

    /**
     * キャプチャを停止する.
     */
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
    protected void updateMessage(String message) {

        super.updateMessage(message);
    }

    /**
     * カレントデバイスをオープンする.
     *
     * @return 正否
     */
    protected boolean open() {

        StringBuilder errbuf = new StringBuilder();

        int snaplen = 64 * 1024;
        int flags = Pcap.MODE_NON_PROMISCUOUS;
        int timeout = 10 * 1000; // 10秒

        pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

        if (pcap == null) {
            LOG.error("Error while opening device for capture: " + errbuf.toString());

            return false;
        } else {
            LOG.trace("pcap open");
        }

        return true;
    }
}
