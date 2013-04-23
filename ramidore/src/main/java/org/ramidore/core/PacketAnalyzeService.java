/**
 *
 */
package org.ramidore.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.format.FormatUtils;
import org.ramidore.logic.AbstractRedomiraLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * パケット解析サービス.
 *
 * @author atmark
 *
 */
public class PacketAnalyzeService extends Service<Void> implements IConfigurable {

    /**
     * リアルタイムキャプチャ用.
     */
    public static final int MODE_ONLINE = 0;

    /**
     * オフラインファイル読み込み用.
     */
    public static final int MODE_OFFLINE = 1;

    /**
     * ダンプ出力用.
     */
    public static final int MODE_DUMP = 2;

    public static final List<String> MODE = Collections.unmodifiableList(new ArrayList<String>(){{
        add("on-line cap");
        add("off-line cap");
        add("dump");
    }});

    /**
     * 動作モード.
     */
    private int mode = MODE_ONLINE;

    /**
     * . Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(PacketAnalyzeService.class);

    /**
     * . パケット解析ロジック
     */
    private AbstractRedomiraLogic logic;

    /**
     * . ネットワークインターフェースのリスト
     */
    private List<PcapIf> devices = new ArrayList<PcapIf>();

    /**
     * . カレントインターフェース
     */
    private PcapIf currentDevice;

    /**
     * . カレントデバイスの持つIPアドレス一覧
     */
    private List<PcapAddr> addresses;

    /**
     * . ListenするIPアドレス
     */
    private PcapAddr listenAddress;

    /**
     * カレントタスク.
     */
    private RamidoreTask currentTask;

    /**
     * コンストラクタ.
     *
     * @param logic
     *            ロジック
     * @param config
     *            設定
     */
    public PacketAnalyzeService(AbstractRedomiraLogic logic, Properties config) {

        this.logic = logic;

        initialize();

        loadConfig(config);
    }

    /**
     * コンストラクタ.
     *
     * @param logic
     *            ロジック
     * @param config
     *            設定
     * @param mode
     *            動作モード
     */
    public PacketAnalyzeService(AbstractRedomiraLogic logic, Properties config, int mode) {

        this.logic = logic;
        this.mode = mode;

        initialize();

        loadConfig(config);
    }

    @Override
    public void loadConfig(Properties config) {

        // 初期選択するデバイス
        int defaultDevice = Integer.parseInt(config.getProperty("defaultDevice", "0"));

        setDevice(defaultDevice);

        // 初期選択するIPアドレス
        int defaultListenAddress = Integer.parseInt(config.getProperty("defaultListenAddress", "0"));
        this.listenAddress = addresses.get(defaultListenAddress);

        setListenAddress(defaultListenAddress);

        // 保持するロジックの設定をロード
        logic.loadConfig(config);
    }

    @Override
    public void saveConfig(Properties config) {

        // 初期選択するデバイス
        int defaultDevice = devices.indexOf(currentDevice);
        config.setProperty("defaultDevice", String.valueOf(defaultDevice));
        // 初期選択するIPアドレス
        int defaultListenAddress = addresses.indexOf(listenAddress);
        config.setProperty("defaultListenAddress", String.valueOf(defaultListenAddress));

        // 保持するロジックの設定をセーブ
        logic.saveConfig(config);
    }

    @Override
    public void loadConfig() {
        // nop
    }

    @Override
    public void saveConfig() {
        // nop
    }

    @Override
    protected Task<Void> createTask() {

        if (mode == MODE_ONLINE) {
            // 通常のパケットキャプチャ
            currentTask = new OnlineTask(currentDevice, listenAddress, logic);
        } else if (mode == MODE_OFFLINE) {
            // オフラインファイル読込用のタスクを生成
            currentTask = new OfflineTask(listenAddress, logic);
        } else if (mode == MODE_DUMP) {
            // オフラインファイルダンプ用のタスクを生成
            currentTask = new DumperTask(currentDevice, listenAddress);
        }

        LOG.trace("RedomiraTask created");

        return currentTask;
    }

    @Override
    public void restart() {

        LOG.trace("call Service.restart");

        super.restart();
    }

    /**
     * キャプチャを停止する.
     */
    public void stop() {

        if (currentTask != null) {
            currentTask.stop();
        }
    }

    @Override
    public void succeeded() {

        // State = SUCCEEDED で呼び出される
        LOG.trace("call Service.succeeded");

        super.succeeded();
    }

    @Override
    public void cancelled() {

        // State = CANCELLED で呼び出される
        LOG.trace("call Service.cancelled");
    }

    @Override
    public void failed() {

        // State = FAILED で呼び出される
        LOG.trace("call Service.failed");
    }

    /**
     * . 初期化
     *
     * @return 成功/失敗
     */
    public boolean initialize() {

        StringBuilder errbuf = new StringBuilder();

        // デバイス取得

        int r = Pcap.findAllDevs(devices, errbuf);

        if (r == Pcap.ERROR || devices.isEmpty()) {

            LOG.error("Can't read list of devices, error is " + errbuf.toString());

            return false;
        }

        LOG.trace("Network devices found");

        int i = 0;
        for (PcapIf device : devices) {

            LOG.trace(i++ + ":" + device.getName() + "[" + device.getDescription() + "]");
        }

        return true;
    }

    /**
     * . デバイスを設定する
     *
     * @param i
     *            デバイスのインデックス
     */
    public void setDevice(final int i) {

        currentDevice = devices.get(i);

        LOG.trace("Choosing " + currentDevice.getDescription() + " on your behalf");

        addresses = currentDevice.getAddresses();

        for (PcapAddr addr : addresses) {
            LOG.trace("ipaddress : " + FormatUtils.ip(addr.getAddr().getData()));
        }
    }

    /**
     * . ListenするIPアドレスを設定する
     *
     * @param i
     *            IPアドレスのインデックス
     */
    public void setListenAddress(final int i) {

        listenAddress = addresses.get(i);
    }

    /**
     * getter.
     *
     * @return logic
     */
    public AbstractRedomiraLogic getLogic() {
        return logic;
    }

    /**
     * getter.
     *
     * @return devices
     */
    public List<PcapIf> getDevices() {
        return devices;
    }

    /**
     * getter.
     *
     * @return currentDevice
     */
    public PcapIf getCurrentDevice() {
        return currentDevice;
    }

    /**
     * getter.
     *
     * @return addresses
     */
    public List<PcapAddr> getAddresses() {
        return addresses;
    }

    /**
     * getter.
     *
     * @return listenAddress
     */
    public PcapAddr getListenAddress() {
        return listenAddress;
    }

    /**
     * getter.
     *
     * @return currentTask
     */
    public RamidoreTask getCurrentTask() {
        return currentTask;
    }

    /**
     * setter.
     *
     * @param logic
     *            セットする logic
     */
    public void setLogic(AbstractRedomiraLogic logic) {
        this.logic = logic;
    }

    /**
     * getter.
     *
     * @return mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * setter.
     *
     * @param mode セットする mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }
}
