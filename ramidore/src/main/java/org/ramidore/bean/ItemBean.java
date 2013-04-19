package org.ramidore.bean;

import java.util.List;

/**
 * アイテム情報.
 *
 * @author atmark
 *
 */
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

    /**
     * getter.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * setter.
     *
     * @param id セットする id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter.
     *
     * @param name セットする name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter.
     *
     * @return groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * setter.
     *
     * @param groupId セットする groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * getter.
     *
     * @return equipId
     */
    public String getEquipId() {
        return equipId;
    }

    /**
     * setter.
     *
     * @param equipId セットする equipId
     */
    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    /**
     * getter.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * setter.
     *
     * @param value セットする value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * getter.
     *
     * @return fluctuation
     */
    public String getFluctuation() {
        return fluctuation;
    }

    /**
     * setter.
     *
     * @param fluctuation セットする fluctuation
     */
    public void setFluctuation(String fluctuation) {
        this.fluctuation = fluctuation;
    }

    /**
     * getter.
     *
     * @return attackSpeed
     */
    public String getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * setter.
     *
     * @param attackSpeed セットする attackSpeed
     */
    public void setAttackSpeed(String attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    /**
     * getter.
     *
     * @return lowAP
     */
    public String getLowAP() {
        return lowAP;
    }

    /**
     * setter.
     *
     * @param lowAP セットする lowAP
     */
    public void setLowAP(String lowAP) {
        this.lowAP = lowAP;
    }

    /**
     * getter.
     *
     * @return highAP
     */
    public String getHighAP() {
        return highAP;
    }

    /**
     * setter.
     *
     * @param highAP セットする highAP
     */
    public void setHighAP(String highAP) {
        this.highAP = highAP;
    }

    /**
     * getter.
     *
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * setter.
     *
     * @param model セットする model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * getter.
     *
     * @return unknown1
     */
    public String getUnknown1() {
        return unknown1;
    }

    /**
     * setter.
     *
     * @param unknown1 セットする unknown1
     */
    public void setUnknown1(String unknown1) {
        this.unknown1 = unknown1;
    }

    /**
     * getter.
     *
     * @return neededAbility
     */
    public String getNeededAbility() {
        return neededAbility;
    }

    /**
     * setter.
     *
     * @param neededAbility セットする neededAbility
     */
    public void setNeededAbility(String neededAbility) {
        this.neededAbility = neededAbility;
    }

    /**
     * getter.
     *
     * @return unknown2
     */
    public String getUnknown2() {
        return unknown2;
    }

    /**
     * setter.
     *
     * @param unknown2 セットする unknown2
     */
    public void setUnknown2(String unknown2) {
        this.unknown2 = unknown2;
    }

    /**
     * getter.
     *
     * @return pileUp
     */
    public String getPileUp() {
        return pileUp;
    }

    /**
     * setter.
     *
     * @param pileUp セットする pileUp
     */
    public void setPileUp(String pileUp) {
        this.pileUp = pileUp;
    }

    /**
     * getter.
     *
     * @return dropLv
     */
    public String getDropLv() {
        return dropLv;
    }

    /**
     * setter.
     *
     * @param dropLv セットする dropLv
     */
    public void setDropLv(String dropLv) {
        this.dropLv = dropLv;
    }

    /**
     * getter.
     *
     * @return paramList
     */
    public List<String> getParamList() {
        return paramList;
    }

    /**
     * setter.
     *
     * @param paramList セットする paramList
     */
    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

}
