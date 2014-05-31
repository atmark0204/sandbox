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

package org.ramidore.logic.item;

import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.ramidore.bean.ItemBean;
import org.ramidore.bean.ItemTable;
import org.ramidore.core.IConfigurable;
import org.ramidore.core.PacketData;
import org.ramidore.logic.IPacketExecutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * アイテム取得パケットを処理する.
 *
 * @author atmark
 */
public class ItemLogic implements IPacketExecutable, IConfigurable {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ItemLogic.class);

    /**
     * 自動取得.
     * <p>
     * ex.) RS欠片等
     */
    private static final String PATTERM_AUTO_PICKUP = "^..002811CDCDCDCD010000002200A2110000........(....)01000000(....)....(....)....(....)....3C0000000000$";

    /**
     * 手動取得.
     */
    private static final String PATTERN_MANUAL_PICKUP = "^(?:.{2})*2200381100000000........(....)........(....)....(....)....(....)....3C000000..(?:.{2})*$";

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternAutoPickup = Pattern
            .compile(PATTERM_AUTO_PICKUP);

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternManualPickup = Pattern
            .compile(PATTERN_MANUAL_PICKUP);

    /**
     * 通知するか
     */
    private boolean isNotify;

    /**
     * 通知するアイテムコード
     */
    private Set<String> notifyItemCodes;

    /**
     * 通知するオプションコード
     */
    private Set<String> notifyOptionCodes;

    /**
     * item.datのデータ.
     * <p>
     * TODO Singletonにしてもいい
     */
    private ItemDatHolder itemDat;

    /**
     * 表示用テーブル.
     */
    @Getter
    @Setter
    private TableView<ItemTable> itemTable;

    /**
     * . コンストラクタ
     */
    public ItemLogic() {

        itemDat = new ItemDatHolder();
    }

    @Override
    public boolean execute(PacketData data) {

        Map<String, ItemBean> itemMap = itemDat.getItemMap();

        Matcher mAutoPickup = patternAutoPickup.matcher(data.getStrData());

        if (mAutoPickup.matches()) {

            String id = mAutoPickup.group(1);

            String name = "未知のアイテム" + id;

            if (itemMap.containsKey(id)) {
                name = itemMap.get(id).getName();
            }

            String opId1 = null;
            String opName1;
            try {
                opId1 = mAutoPickup.group(2);
                opName1 = getOptionName(opId1);
            } catch (IllegalStateException e) {
                opName1 = StringUtils.EMPTY;
            }

            String opId2 = null;
            String opName2;
            try {
                opId2 = mAutoPickup.group(3);
                opName2 = getOptionName(opId2);
            } catch (IllegalStateException e) {
                opName2 = StringUtils.EMPTY;
            }

            String opId3 = null;
            String opName3;
            try {
                opId3 = mAutoPickup.group(4);
                opName3 = getOptionName(opId3);
            } catch (IllegalStateException e) {
                opName3 = StringUtils.EMPTY;
            }

            String itemName = opName1 + opName2 + opName3 + name;

            itemTable.getItems().add(new ItemTable(data.getDate(), itemName));

            if (isNotify(id, opId1, opId2, opId3)) {
                notifySound();
            }

            LOG.info("自動取得 : " + itemName);

            return true;
        }

        Matcher mManualPickup = patternManualPickup.matcher(data.getStrData());

        if (mManualPickup.matches()) {

            String id = mManualPickup.group(1);

            String name = "未知のアイテム : " + id;

            if (itemMap.containsKey(id)) {
                name = itemMap.get(id).getName();
            }

            String opId1 = null;
            String opName1;
            try {
                opId1 = mAutoPickup.group(2);
                opName1 = getOptionName(opId1);
            } catch (IllegalStateException e) {
                opName1 = StringUtils.EMPTY;
            }

            String opId2 = null;
            String opName2;
            try {
                opId2 = mAutoPickup.group(3);
                opName2 = getOptionName(opId2);
            } catch (IllegalStateException e) {
                opName2 = StringUtils.EMPTY;
            }

            String opId3 = null;
            String opName3;
            try {
                opId3 = mAutoPickup.group(4);
                opName3 = getOptionName(opId3);
            } catch (IllegalStateException e) {
                opName3 = StringUtils.EMPTY;
            }

            String itemName = opName1 + opName2 + opName3 + name;

            itemTable.getItems().add(new ItemTable(data.getDate(), itemName));

            if (isNotify(id, opId1, opId2, opId3)) {
                notifySound();
            }

            LOG.info("拾得 : " + itemName);

            return true;
        }

        return false;
    }

    /**
     * 通知対象かの判別
     *
     * @param id    item id
     * @param opId1 option id1
     * @param opId2 option id2
     * @param opId3 option id3
     * @return true:通知対象
     */
    private boolean isNotify(String id, String opId1, String opId2, String opId3) {

        if (notifyItemCodes.contains(id)) {
            return true;
        }

        if ((opId1 != null && notifyOptionCodes.contains(opId1))
                || (opId2 != null && notifyOptionCodes.contains(opId2))
                || (opId3 != null && notifyOptionCodes.contains(opId3))) {
            return true;
        }

        return false;
    }

    private void notifySound() {

        if (isNotify) {
            Runnable runnable = (Runnable) Toolkit.getDefaultToolkit()
                    .getDesktopProperty("win.sound.default");

            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /**
     * オプション名を返す.
     *
     * @param id オプションID
     * @return オプション名
     */
    private String getOptionName(String id) {

        if (itemDat.getOptionMap().containsKey(id)) {

            return itemDat.getOptionMap().get(id).getName();
        }

        return StringUtils.EMPTY;
    }

    @Override
    public void loadConfig(Properties config) {

        isNotify = Boolean.parseBoolean(config.getProperty("item.notify.enabled", "true"));

        notifyItemCodes = new HashSet<>(Arrays.asList(config.getProperty(
                "item.notify.itemCode", "").split(",")));
        notifyOptionCodes = new HashSet<>(Arrays.asList(config.getProperty(
                "item.notify.optionCode", "").split(",")));
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("item.notify.enabled", String.valueOf(isNotify));

        config.setProperty("item.notify.itemCode",
                StringUtils.join(notifyItemCodes, ","));
        config.setProperty("item.notify.optionCode",
                StringUtils.join(notifyOptionCodes, ","));
    }

    @Override
    public void loadConfig() {
        // nop
    }

    @Override
    public void saveConfig() {
        // nop
    }

}
