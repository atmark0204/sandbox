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

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * アイテム情報.
 *
 * @author atmark
 */
@Getter
@Setter
public class ItemBean {

    /**
     * アイテムID.
     * <p>
     * size : 2byte
     */
    private String id;

    /**
     * アイテム名.
     * <p>
     * size : 72byte
     */
    private String name;

    /**
     * 分類.
     * <p>
     * size : 1byte
     */
    private String groupId;

    // null 1byte

    /**
     * 装備可能職業.
     * <p>
     * size : 18byte
     */
    private String equipId;

    /**
     * 純価格.
     * <p>
     * size : 2byte
     */
    private String value;

    /**
     * 補正変動値.
     * <p>
     * size : 8byte
     */
    private String fluctuation;

    /**
     * 基本攻撃速度.
     * <p>
     * size : 2byte
     */
    private String attackSpeed;

    /**
     * 最小攻撃力.
     * <p>
     * size : 2byte
     */
    private String lowAP;

    /**
     * 最大攻撃力.
     * <p>
     * size : 2byte
     */
    private String highAP;

    /**
     * 耐久力型式.
     * <p>
     * size : 2byte
     */
    private String model;

    /**
     * 不明.
     * <p>
     * size : 6byte
     */
    private String unknown1;

    /**
     * 要求能力値.
     * <p>
     * size : 18byte
     */
    private String neededAbility;

    /**
     * 不明.
     * <p>
     * size : 10byte
     */
    private String unknown2;

    /**
     * 重ね置き上限.
     * <p>
     * size : 2byte
     */
    private String pileUp;

    /**
     * ドロップレベル.
     * <p>
     * size : 2byte
     */
    private String dropLv;

    /**
     * パラメータ.
     * <p>
     * size : 10byte * 16
     */
    private List<String> paramList;

    // TODO item.datの全情報が解析出来れば追加
}
