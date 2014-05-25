package org.ramidore.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * アイテム情報.
 *
 * @author atmark
 *
 */
@Getter
@Setter
public class ItemBean {

    /**
     * アイテムID.
     *
     * size : 2byte
     */
    private String id;

    /**
     * アイテム名.
     *
     * size : 72byte
     */
    private String name;

    /**
     * 分類.
     *
     * size : 1byte
     */
    private String groupId;

    // null 1byte

    /**
     * 装備可能職業.
     *
     * size : 18byte
     */
    private String equipId;

    /**
     * 純価格.
     *
     * size : 2byte
     */
    private String value;

    /**
     * 補正変動値.
     *
     * size : 8byte
     */
    private String fluctuation;

    /**
     * 基本攻撃速度.
     *
     * size : 2byte
     */
    private String attackSpeed;

    /**
     * 最小攻撃力.
     *
     * size : 2byte
     */
    private String lowAP;

    /**
     * 最大攻撃力.
     *
     * size : 2byte
     */
    private String highAP;

    /**
     * 耐久力型式.
     *
     * size : 2byte
     */
    private String model;

    /**
     * 不明.
     *
     * size : 6byte
     */
    private String unknown1;

    /**
     * 要求能力値.
     *
     * size : 18byte
     */
    private String neededAbility;

    /**
     * 不明.
     *
     * size : 10byte
     */
    private String unknown2;

    /**
     * 重ね置き上限.
     *
     * size : 2byte
     */
    private String pileUp;

    /**
     * ドロップレベル.
     *
     * size : 2byte
     */
    private String dropLv;

    /**
     * パラメータ.
     *
     * size : 10byte * 16
     */
    private List<String> paramList;

    // TODO item.datの全情報が解析出来れば追加
}
