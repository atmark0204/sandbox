package org.ramidore.bean;

import javafx.scene.chart.XYChart.Data;

/**
 * ポイント戦チャート表示用データ.
 *
 * @author atmark
 *
 */
public class PbLogBean {

    /**
     * 識別子.
     */
    private String id;

    /**
     * シーケンス番号.
     */
    private int sequentialNo;

    /**
     * 面番号.
     */
    private int stageNo;

    /**
     * ステージごとのシーケンス番号.
     */
    private int stageSequentialNo;

    /**
     * 点数.
     */
    private int point;

    /**
     * 前ステージのポイント.
     */
    private int pointOffset = 0;

    /**
     * コンストラクタ.
     *
     * @param id
     *            識別子
     * @param sequentialNo
     *            シーケンス番号
     * @param stageNo
     *            ステージ番号
     * @param stageSequentialNo
     *            ステージシーケンス番号
     * @param point
     *            点数
     * @param pointOffset
     *            前面の最終点数
     */
    public PbLogBean(String id, int sequentialNo, int stageNo, int stageSequentialNo, int point, int pointOffset) {

        this.id = id;
        this.sequentialNo = sequentialNo;
        this.stageNo = stageNo;
        this.stageSequentialNo = stageSequentialNo;
        this.point = point;
        this.pointOffset = pointOffset;
    }

    /**
     * コンストラクタ(pointOffset無し).
     *
     * @param id
     *            識別子
     * @param sequentialNo
     *            シーケンス番号
     * @param stageNo
     *            ステージ番号
     * @param stageSequentialNo
     *            ステージシーケンス番号
     * @param point
     *            点数
     */
    public PbLogBean(String id, int sequentialNo, int stageNo, int stageSequentialNo, int point) {

        this.id = id;
        this.sequentialNo = sequentialNo;
        this.stageNo = stageNo;
        this.stageSequentialNo = stageSequentialNo;
        this.point = point;
    }

    /**
     * ステージ中のチャート表示用データを返す.
     *
     * @return チャート表示用データ
     */
    public Data<Number, Number> toStageData() {

        return new Data<Number, Number>(stageSequentialNo, point - pointOffset);
    }

    /**
     * 全体のチャート表示用データを返す.
     *
     * @return チャート表示用データ
     */
    public Data<Number, Number> toData() {

        return new Data<Number, Number>(sequentialNo, point);
    }

    /**
     * getter.
     *
     * @return point
     */
    public int getPoint() {
        return point;
    }

    /**
     * setter.
     *
     * @param point
     *            セットする point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * getter.
     *
     * @return stageNo
     */
    public int getStageNo() {
        return stageNo;
    }

    /**
     * setter.
     *
     * @param stageNo
     *            セットする stageNo
     */
    public void setStageNo(int stageNo) {
        this.stageNo = stageNo;
    }

    /**
     * getter.
     *
     * @return stageSequentialNo
     */
    public int getStageSequentialNo() {
        return stageSequentialNo;
    }

    /**
     * setter.
     *
     * @param stageSequentialNo
     *            セットする stageSequentialNo
     */
    public void setStageSequentialNo(int stageSequentialNo) {
        this.stageSequentialNo = stageSequentialNo;
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
     * @return pointOffset
     */
    public int getPointOffset() {
        return pointOffset;
    }

    /**
     * setter.
     *
     * @param pointOffset
     *            セットする pointOffset
     */
    public void setPointOffset(int pointOffset) {
        this.pointOffset = pointOffset;
    }
}
