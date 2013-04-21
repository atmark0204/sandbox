package org.ramidore.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * ポイント戦統計表示用テーブル.
 *
 * @author atmark
 *
 */
public class PbStatTable {

    /**
     * 系列名.
     */
    private String id;

    /**
     * 1面点数.
     */
    private SimpleIntegerProperty point1;

    /**
     * 1面mob数.
     */
    private SimpleIntegerProperty mobCount1;

    /**
     * 1面表示.
     */
    private SimpleStringProperty stage1;

    /**
     * 2面点数.
     */
    private SimpleIntegerProperty point2;

    /**
     * 2面mob数.
     */
    private SimpleIntegerProperty mobCount2;

    /**
     * 2面表示.
     */
    private SimpleStringProperty stage2;

    /**
     * 3面点数.
     */
    private SimpleIntegerProperty point3;

    /**
     * 3面mob数.
     */
    private SimpleIntegerProperty mobCount3;

    /**
     * 3面表示.
     */
    private SimpleStringProperty stage3;

    /**
     * 4面点数.
     */
    private SimpleIntegerProperty point4;

    /**
     * 4面mob数.
     */
    private SimpleIntegerProperty mobCount4;

    /**
     * 4面表示.
     */
    private SimpleStringProperty stage4;

    /**
     * 5面点数.
     */
    private SimpleIntegerProperty point5;

    /**
     * 5面mob数.
     */
    private SimpleIntegerProperty mobCount5;

    /**
     * 5面表示.
     */
    private SimpleStringProperty stage5;

    /**
     * 合計点数.
     */
    private SimpleIntegerProperty pointTotal;

    /**
     * コンストラクタ.
     */
    public PbStatTable() {

        point1 = new SimpleIntegerProperty(0);
        point2 = new SimpleIntegerProperty(0);
        point3 = new SimpleIntegerProperty(0);
        point4 = new SimpleIntegerProperty(0);
        point5 = new SimpleIntegerProperty(0);
        pointTotal = new SimpleIntegerProperty(0);

        mobCount1 = new SimpleIntegerProperty(0);
        mobCount2 = new SimpleIntegerProperty(0);
        mobCount3 = new SimpleIntegerProperty(0);
        mobCount4 = new SimpleIntegerProperty(0);
        mobCount5 = new SimpleIntegerProperty(0);

        stage1 = new SimpleStringProperty();
        stage2 = new SimpleStringProperty();
        stage3 = new SimpleStringProperty();
        stage4 = new SimpleStringProperty();
        stage5 = new SimpleStringProperty();
    }

    @Override
    public String toString() {

        return getId() + "\t" + getStage1() + "\t" + getStage2() + "\t" + getStage3() + "\t" + getStage4() + "\t" + getStage5();
    }

    public String getStage1() {

        return stage1.get();
    }

    public String getStage2() {

        return stage2.get();
    }

    public String getStage3() {

        return stage3.get();
    }

    public String getStage4() {

        return stage4.get();
    }

    public String getStage5() {

        return stage5.get();
    }

    public void setStage1() {

        this.stage1.set(point1.get() + " (" + mobCount1.get() + ")");
    }

    public void setStage2() {

        this.stage2.set(point2.get() + " (" + mobCount2.get() + ")");
    }

    public void setStage3() {

        this.stage3.set(point3.get() + " (" + mobCount3.get() + ")");
    }

    public void setStage4() {

        this.stage4.set(point4.get() + " (" + mobCount4.get() + ")");
    }

    public void setStage5() {

        this.stage5.set(point5.get() + " (" + mobCount5.get() + ")");
    }

    /**
     * プロパティを返す.
     *
     * @return stage1
     */
    public SimpleStringProperty stage1Property() {
        return stage1;
    }

    /**
     * プロパティを返す.
     *
     * @return stage2
     */
    public SimpleStringProperty stage2Property() {
        return stage2;
    }

    /**
     * プロパティを返す.
     *
     * @return stage3
     */
    public SimpleStringProperty stage3Property() {
        return stage3;
    }

    /**
     * プロパティを返す.
     *
     * @return stage4
     */
    public SimpleStringProperty stage4Property() {
        return stage4;
    }

    /**
     * プロパティを返す.
     *
     * @return stage1
     */
    public SimpleStringProperty stage5Property() {
        return stage5;
    }

    /**
     * プロパティを返す.
     *
     * @return point1
     */
    public SimpleIntegerProperty point1Property() {
        return point1;
    }

    /**
     * プロパティを返す.
     *
     * @return point2
     */
    public SimpleIntegerProperty point2Property() {
        return point2;
    }

    /**
     * プロパティを返す.
     *
     * @return point3
     */
    public SimpleIntegerProperty point3Property() {
        return point3;
    }

    /**
     * プロパティを返す.
     *
     * @return point4
     */
    public SimpleIntegerProperty point4Property() {
        return point4;
    }

    /**
     * プロパティを返す.
     *
     * @return point5
     */
    public SimpleIntegerProperty point5Property() {
        return point5;
    }

    /**
     * プロパティを返す.
     *
     * @return pointTotal
     */
    public SimpleIntegerProperty pointTotalProperty() {
        return pointTotal;
    }

    /**
     * プロパティを返す.
     *
     * @return mobCount1
     */
    public SimpleIntegerProperty mobCount1Property() {
        return mobCount1;
    }

    /**
     * プロパティを返す.
     *
     * @return mobCount2
     */
    public SimpleIntegerProperty mobCount2Property() {
        return mobCount2;
    }

    /**
     * プロパティを返す.
     *
     * @return mobCount3
     */
    public SimpleIntegerProperty mobCount3Property() {
        return mobCount3;
    }

    /**
     * プロパティを返す.
     *
     * @return mobCount4
     */
    public SimpleIntegerProperty mobCount4Property() {
        return mobCount4;
    }

    /**
     * プロパティを返す.
     *
     * @return mobCount5
     */
    public SimpleIntegerProperty mobCount5Property() {
        return mobCount5;
    }

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
     * @param id
     *            セットする id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter.
     *
     * @return point1
     */
    public int getPoint1() {
        return point1.get();
    }

    /**
     * setter.
     *
     * @param point1
     *            セットする point1
     */
    public void setPoint1(int point1) {
        this.point1.set(point1);
    }

    /**
     * getter.
     *
     * @return point2
     */
    public int getPoint2() {
        return point2.get();
    }

    /**
     * setter.
     *
     * @param point2
     *            セットする point2
     */
    public void setPoint2(int point2) {
        this.point2.set(point2);
    }

    /**
     * getter.
     *
     * @return point3
     */
    public int getPoint3() {
        return point3.get();
    }

    /**
     * setter.
     *
     * @param point3
     *            セットする point3
     */
    public void setPoint3(int point3) {
        this.point3.set(point3);
    }

    /**
     * getter.
     *
     * @return point4
     */
    public int getPoint4() {
        return point4.get();
    }

    /**
     * setter.
     *
     * @param point4
     *            セットする point4
     */
    public void setPoint4(int point4) {
        this.point4.set(point4);
    }

    /**
     * getter.
     *
     * @return point5
     */
    public int getPoint5() {
        return point5.get();
    }

    /**
     * setter.
     *
     * @param point5
     *            セットする point5
     */
    public void setPoint5(int point5) {
        this.point5.set(point5);
    }

    /**
     * getter.
     *
     * @return pointTotal
     */
    public int getPointTotal() {
        return pointTotal.get();
    }

    /**
     * setter.
     *
     * @param pointTotal
     *            セットする pointTotal
     */
    public void setPointTotal(int pointTotal) {
        this.pointTotal.set(pointTotal);
    }

    /**
     * getter.
     *
     * @return mobCount1
     */
    public int getMobCount1() {
        return mobCount1.get();
    }

    /**
     * setter.
     *
     * @param mobCount1
     *            セットする mobCount1
     */
    public void setMobCount1(int mobCount1) {
        this.mobCount1.set(mobCount1);
    }

    /**
     * getter.
     *
     * @return mobCount2
     */
    public int getMobCount2() {
        return mobCount2.get();
    }

    /**
     * setter.
     *
     * @param mobCount2
     *            セットする mobCount2
     */
    public void setMobCount2(int mobCount2) {
        this.mobCount2.set(mobCount2);
    }

    /**
     * getter.
     *
     * @return mobCount3
     */
    public int getMobCount3() {
        return mobCount3.get();
    }

    /**
     * setter.
     *
     * @param mobCount3
     *            セットする mobCount3
     */
    public void setMobCount3(int mobCount3) {
        this.mobCount3.set(mobCount3);
    }

    /**
     * getter.
     *
     * @return mobCount4
     */
    public int getMobCount4() {
        return mobCount4.get();
    }

    /**
     * setter.
     *
     * @param mobCount4
     *            セットする mobCount4
     */
    public void setMobCount4(int mobCount4) {
        this.mobCount4.set(mobCount4);
    }

    /**
     * getter.
     *
     * @return mobCount5
     */
    public int getMobCount5() {
        return mobCount5.get();
    }

    /**
     * setter.
     *
     * @param mobCount5
     *            セットする mobCount5
     */
    public void setMobCount5(int mobCount5) {
        this.mobCount5.set(mobCount5);
    }

}
