package org.ramidore.logic.item;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.ramidore.bean.ItemBean;
import org.ramidore.bean.ItemData;
import org.ramidore.bean.OptionBean;
import org.ramidore.util.RedomiraUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * . item.datをパースしデータを保持する
 *
 * @author atmark
 *
 */
public class ItemDatHolder {

    /**
     * 文字コード.
     */
    private static final String ENCODING = "Windows-31J";

    /**
     * . Logger
     */
    private static Logger log = LoggerFactory.getLogger(ItemDatHolder.class);

    /**
     * . itemId/Nameのマップ
     */
    private Map<String, ItemBean> itemMap;

    /**
     * . optionId/Nameのマップ
     */
    private Map<String, OptionBean> optionMap;

    /**
     * . コンストラクタ
     */
    public ItemDatHolder() {

        itemMap = new HashMap<String, ItemBean>();

        optionMap = new HashMap<String, OptionBean>();

        loadData();
    }

    /**
     * . item.datをロードする
     */
    private void loadData() {

        BufferedInputStream bis = null;

        try {
            bis = new BufferedInputStream(new FileInputStream("item.dat"));

            byte[] header = new byte[8];

            IOUtils.read(bis, header);

            byte[] itemCounByte = ArrayUtils.subarray(header, 0, 2);

            int itemCount = RedomiraUtil.intValueFromDescByteArray(itemCounByte);

            log.debug("itemCount: " + itemCount);

            for (int i = 0; i < itemCount; i++) {

                byte[] itemDataBuffer = new byte[426];

                IOUtils.read(bis, itemDataBuffer);

                ItemBean item = getItemData(itemDataBuffer);

                itemMap.put(item.getId(), item);

                ItemData itemData = new ItemData(item);

                log.info(item.getId() + " : " + item.getName() + " : " + item.getFluctuation() + " : " + itemData.getModel());
            }

            byte[] optionCountByte = new byte[2];

            IOUtils.read(bis, optionCountByte);

            int optionCount = RedomiraUtil.intValueFromDescByteArray(optionCountByte);

            log.debug("optionCount: " + optionCount);

            for (int i = 0; i < optionCount; i++) {

                byte[] optionDataBuffer = new byte[160];

                IOUtils.read(bis, optionDataBuffer);

                OptionBean option = getOptionData(optionDataBuffer);

                optionMap.put(option.getId(), option);

                log.info(option.getId() + " : " + option.getName());
            }

        } catch (FileNotFoundException e) {

            log.error(e.getMessage());
        } catch (IOException e) {

            log.error(e.getMessage());
        } finally {

            IOUtils.closeQuietly(bis);
        }
    }

    /**
     * アイテムデータを切り出す.
     *
     * @param buf バッファ
     * @return アイテムデータ
     */
    private ItemBean getItemData(byte[] buf) {

        ItemBean item = new ItemBean();

        String id = RedomiraUtil.toHex(ArrayUtils.subarray(buf, 0, 2));

        item.setId(id);

        byte[] nameBuffer = ArrayUtils.subarray(buf, 4, 76);

        byte[] nameArray = ArrayUtils.subarray(nameBuffer, 0, ArrayUtils.indexOf(nameBuffer, (byte) 0x00));

        String itemName = RedomiraUtil.encode(RedomiraUtil.toHex(nameArray), ENCODING);

        item.setName(itemName);

        byte[] groupBuf = ArrayUtils.subarray(buf, 76, 77);

        String groupId = RedomiraUtil.toHex(groupBuf);

        item.setGroupId(groupId);

        byte[] equipBuf = ArrayUtils.subarray(buf, 78, 96);

        String equipId = RedomiraUtil.toHex(equipBuf);

        item.setEquipId(equipId);

        byte[] valueBuf = ArrayUtils.subarray(buf, 96, 98);

        String value = RedomiraUtil.toHex(valueBuf);

        item.setValue(value);

        byte[] fluctuationBuf = ArrayUtils.subarray(buf, 98, 106);

        String fluctuation = RedomiraUtil.toHex(fluctuationBuf);

        item.setFluctuation(fluctuation);

        byte[] attackSpeedBuf = ArrayUtils.subarray(buf, 106, 108);

        String attackSpeed = RedomiraUtil.toHex(attackSpeedBuf);

        item.setAttackSpeed(attackSpeed);

        byte[] lowAPBuf = ArrayUtils.subarray(buf, 108, 110);

        String lowAP = RedomiraUtil.toHex(lowAPBuf);

        item.setLowAP(lowAP);

        byte[] highAPBuf = ArrayUtils.subarray(buf, 110, 112);

        String highAP = RedomiraUtil.toHex(highAPBuf);

        item.setHighAP(highAP);

        byte[] modelBuf = ArrayUtils.subarray(buf, 112, 114);

        String model = RedomiraUtil.toHex(modelBuf);

        item.setModel(model);

        return item;
    }

    /**
     * オプションデータを切り出す.
     *
     * @param buf バッファ
     * @return オプションデータ
     */
    private OptionBean getOptionData(byte[] buf) {

        OptionBean option = new OptionBean();

        String id = RedomiraUtil.toHex(ArrayUtils.subarray(buf, 2, 4));

        option.setId(id);

        byte[] nameBuffer = ArrayUtils.subarray(buf, 18, 38);

        byte[] nameArray = ArrayUtils.subarray(nameBuffer, 0, ArrayUtils.indexOf(nameBuffer, (byte) 0x00));

        String name = RedomiraUtil.encode(RedomiraUtil.toHex(nameArray), ENCODING);

        option.setName(name);

        return option;
    }

    /**
     * . accessor
     *
     * @return itemMap
     */
    public Map<String, ItemBean> getItemMap() {
        return itemMap;
    }

    /**
     * . accessor
     *
     * @param itemMap セットする itemMap
     */
    public void setItemMap(final Map<String, ItemBean> itemMap) {
        this.itemMap = itemMap;
    }

    /**
     * . accessor
     *
     * @return optionMap
     */
    public Map<String, OptionBean> getOptionMap() {
        return optionMap;
    }

    /**
     * . accessor
     *
     * @param optionMap セットする optionMap
     */
    public void setOptionMap(final Map<String, OptionBean> optionMap) {
        this.optionMap = optionMap;
    }
}
