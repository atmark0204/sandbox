package org.ramidore.bean;

import java.util.Date;

import javafx.scene.chart.XYChart.Data;

/**
 * ポイント戦チャート表示用データ.
 *
 * @author atmark
 *
 */
public class PointBatteleChartBean {

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
     * 時刻.
     */
    private Date date;

    /**
     * 点数.
     */
    private int point;

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
     * @param date
     *            時刻
     * @param point
     *            点数
     */
    public PointBatteleChartBean(String id, int sequentialNo, int stageNo, int stageSequentialNo, Date date, int point) {

        this.id = id;
        this.sequentialNo = sequentialNo;
        this.stageNo = stageNo;
        this.stageSequentialNo = stageSequentialNo;
        this.date = date;
        this.point = point;
    }

    public Data<Number, Number> toStageData() {

        return new Data<Number, Number>(stageSequentialNo, point);
    }

    public Data<Number, Number> toData() {

        return new Data<Number, Number>(sequentialNo, point);
    }

    /**
     * getter.
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * setter.
     *
     * @param date
     *            セットする date
     */
    public void setDate(Date date) {
        this.date = date;
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

}
