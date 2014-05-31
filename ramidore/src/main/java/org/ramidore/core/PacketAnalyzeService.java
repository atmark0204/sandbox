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

/**
 *
 */
package org.ramidore.core;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.format.FormatUtils;
import org.ramidore.logic.AbstractMainLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * パケット解析サービス.
 *
 * @author atmark
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

    public static final List<String> MODE = Collections.unmodifiableList(new ArrayList<String>() {{
        add("on-line cap");
        add("off-line cap");
        add("dump");
    }});

    /**
     * 動作モード.
     */
    @Getter
    @Setter
    private int mode = MODE_ONLINE;

    /**
     * . Logger
     */
    @Getter
    private static Logger LOG = LoggerFactory.getLogger(PacketAnalyzeService.class);

    /**
     * . パケット解析ロジック
     */
    @Getter
    @Setter
    private AbstractMainLogic logic;

    /**
     * . ネットワークインターフェースのリスト
     */
    @Getter
    private List<PcapIf> devices = new ArrayList<>();

    /**
     * . カレントインターフェース
     */
    @Getter
    private PcapIf currentDevice;

    /**
     * . カレントデバイスの持つIPアドレス一覧
     */
    @Getter
    private List<PcapAddr> addresses;

    /**
     * . ListenするIPアドレス
     */
    @Getter
    private PcapAddr listenAddress;

    /**
     * カレントタスク.
     */
    @Getter
    private AbstractTask currentTask;

    /**
     * コンストラクタ.
     *
     * @param logic  ロジック
     * @param config 設定
     */
    public PacketAnalyzeService(AbstractMainLogic logic, Properties config) {

        this.logic = logic;

        initialize();

        loadConfig(config);
    }

    /**
     * コンストラクタ.
     *
     * @param logic  ロジック
     * @param config 設定
     * @param mode   動作モード
     */
    public PacketAnalyzeService(AbstractMainLogic logic, Properties config, int mode) {

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

        LOG.trace("Task created");

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
     * @param i デバイスのインデックス
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
     * 表示用デバイス名一覧を取得.
     *
     * @return list
     */
    public List<String> getDeviceNameList() {

        List<String> deviceNameList = new ArrayList<>();

        for (PcapIf device : devices) {

            deviceNameList.add(device.getDescription());
        }

        return deviceNameList;
    }

    /**
     * 表示用IPアドレス一覧を取得.
     *
     * @return list
     */
    public List<String> getAddressList() {

        List<String> addressList = new ArrayList<>();

        for (PcapAddr pcapAddr : addresses) {

            addressList.add(FormatUtils.ip(pcapAddr.getAddr().getData()));
        }

        return addressList;
    }

    public int getCurrentDeviceIndex() {

        return devices.indexOf(currentDevice);
    }

    public int getCurrentListenAddressIndex() {

        return addresses.indexOf(listenAddress);
    }

    /**
     * . ListenするIPアドレスを設定する
     *
     * @param i IPアドレスのインデックス
     */
    public void setListenAddress(final int i) {

        listenAddress = addresses.get(i);
    }
}