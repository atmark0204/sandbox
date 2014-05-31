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

package org.ramidore.bean;

import org.apache.commons.lang3.StringUtils;
import org.ramidore.logic.item.Equip;
import org.ramidore.logic.item.ItemGroupEnum;
import org.ramidore.util.RamidoreUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ItemBeanのデコレータ.
 * <p>
 * 表示用のデータを提供する.
 *
 * @author atmark
 */
public class ItemData {

    /**
     * 末尾NULL.
     */
    private static final String POSTFIX_NULL = "(00)*$";

    /**
     * 末尾NULLのパターン.
     */
    private static final Pattern PATTERN_POSTFIX_NULL = Pattern.compile(POSTFIX_NULL);

    /**
     * 生のアイテムデータ.
     */
    private ItemBean itemBean;

    /**
     * コンストラクタ.
     *
     * @param itemBean ItemBean
     */
    public ItemData(ItemBean itemBean) {

        this.itemBean = itemBean;
    }

    /**
     * グループ名を返す.
     *
     * @return グループ名
     */
    public String getGroupName() {

        return ItemGroupEnum.getName(itemBean.getGroupId());
    }

    /**
     * 装備可能職業を返す.
     *
     * @return 装備可能職業
     */
    public String getEquip() {

        return new Equip(itemBean.getEquipId()).toString();
    }

    /**
     * 純価格を返す.
     *
     * @return 純価格
     */
    public int getValue() {

        String value = itemBean.getValue();

        Matcher m = PATTERN_POSTFIX_NULL.matcher(value);

        value = m.replaceAll(StringUtils.EMPTY);

        if (value.isEmpty()) {
            return 0;
        } else {
            return RamidoreUtil.intValueFromDescHexString(value);
        }
    }

    /**
     * 変動価格を返す.
     *
     * @return
     */
    public String getFluctuation() {

        // TODO 未実装
        return itemBean.getFluctuation();
    }

    /**
     * 基本攻撃速度を返す.
     *
     * @return 基本攻撃速度
     */
    public String getAttackSpeed() {

        int attackSpeedInt = RamidoreUtil.intValueFromDescHexString(itemBean.getAttackSpeed());

        float attackSpeed = (float) attackSpeedInt / 100;

        return String.format("%.2f", attackSpeed);
    }

    /**
     * 最小攻撃力.
     *
     * @return 最小攻撃力
     */
    public int getLowAP() {

        return RamidoreUtil.intValueFromDescHexString(itemBean.getLowAP());
    }

    /**
     * 最小攻撃力.
     *
     * @return 最小攻撃力
     */
    public int getHighAP() {

        return RamidoreUtil.intValueFromDescHexString(itemBean.getHighAP());
    }

    /**
     * 耐久型式.
     *
     * @return 耐久型式
     */
    public int getModel() {

        return RamidoreUtil.intValueFromDescHexString(itemBean.getModel());
    }
}
