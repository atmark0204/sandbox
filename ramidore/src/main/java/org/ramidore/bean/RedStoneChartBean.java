package org.ramidore.bean;

import javafx.scene.chart.AreaChart;

/**
 * RedStone返却数チャート用のデータ.
 *
 * @author atmark
 *
 */
public class RedStoneChartBean {

    /**
     * 現在の総数.
     */
    private int currentTotalCount = 0;

    /**
     * 現在の天上数.
     */
    private int currentTenjoCount = 0;

    /**
     * 現在の地下数.
     */
    private int currentChikaCount = 0;

    /**
     * 現在の悪魔数.
     */
    private int currentAkumaCount = 0;

    /**
     * 起動時からの総数.
     */
    private int localTotalCount = 0;

    /**
     * 起動時からの天上数.
     */
    private int localTenjoCount = 0;

    /**
     * 起動時からの地下数.
     */
    private int localChikaCount = 0;

    /**
     * 起動時からの悪魔数.
     */
    private int localAkumaCount = 0;

    /**
     * 天上個数を加算.
     */
    public void addTenjo() {
        localTenjoCount++;
    }

    /**
     * 地下個数を加算.
     */
    public void addChika() {
        localChikaCount++;
    }

    /**
     * 悪魔個数を加算.
     */
    public void addAkuma() {
        localAkumaCount++;
    }

    /**
     * 総数計算.
     */
    public void setCurrentTotalCount() {
        this.currentTotalCount = currentTenjoCount + currentChikaCount + currentAkumaCount;
    }

    /**
     * 総数計算.
     */
    public void setLocalTotalCount() {
        this.localTotalCount = localTenjoCount + localChikaCount + localAkumaCount;
    }

    /**
     * データ変換.
     *
     * @return AreaChart.Data
     */
    public AreaChart.Data<Number, Number> toTenjoData() {

        return new AreaChart.Data<Number, Number>(localTotalCount, localTenjoCount);
    }

    /**
     * データ変換.
     *
     * @return AreaChart.Data
     */
    public AreaChart.Data<Number, Number> toChikaData() {

        return new AreaChart.Data<Number, Number>(localTotalCount, localChikaCount);
    }

    /**
     * データ変換.
     *
     * @return AreaChart.Data
     */
    public AreaChart.Data<Number, Number> toAkumaData() {

        return new AreaChart.Data<Number, Number>(localTotalCount, localAkumaCount);
    }

    /**
     * getter.
     *
     * @return currentTotalCount
     */
    public int getCurrentTotalCount() {
        return currentTotalCount;
    }

    /**
     * getter.
     *
     * @return currentTenjoCount
     */
    public int getCurrentTenjoCount() {
        return currentTenjoCount;
    }

    /**
     * setter.
     *
     * @param currentTenjoCount
     *            セットする currentTenjoCount
     */
    public void setCurrentTenjoCount(int currentTenjoCount) {
        this.currentTenjoCount = currentTenjoCount;
    }

    /**
     * getter.
     *
     * @return currentChikaCount
     */
    public int getCurrentChikaCount() {
        return currentChikaCount;
    }

    /**
     * setter.
     *
     * @param currentChikaCount
     *            セットする currentChikaCount
     */
    public void setCurrentChikaCount(int currentChikaCount) {
        this.currentChikaCount = currentChikaCount;
    }

    /**
     * getter.
     *
     * @return currentAkumaCount
     */
    public int getCurrentAkumaCount() {
        return currentAkumaCount;
    }

    /**
     * setter.
     *
     * @param currentAkumaCount
     *            セットする currentAkumaCount
     */
    public void setCurrentAkumaCount(int currentAkumaCount) {
        this.currentAkumaCount = currentAkumaCount;
    }

    /**
     * getter.
     *
     * @return localTotalCount
     */
    public int getLocalTotalCount() {
        return localTotalCount;
    }

    /**
     * getter.
     *
     * @return localTenjoCount
     */
    public int getLocalTenjoCount() {
        return localTenjoCount;
    }

    /**
     * setter.
     *
     * @param localTenjoCount
     *            セットする localTenjoCount
     */
    public void setLocalTenjoCount(int localTenjoCount) {
        this.localTenjoCount = localTenjoCount;
    }

    /**
     * getter.
     *
     * @return localChikaCount
     */
    public int getLocalChikaCount() {
        return localChikaCount;
    }

    /**
     * setter.
     *
     * @param localChikaCount
     *            セットする localChikaCount
     */
    public void setLocalChikaCount(int localChikaCount) {
        this.localChikaCount = localChikaCount;
    }

    /**
     * getter.
     *
     * @return localAkumaCount
     */
    public int getLocalAkumaCount() {
        return localAkumaCount;
    }

    /**
     * setter.
     *
     * @param localAkumaCount
     *            セットする localAkumaCount
     */
    public void setLocalAkumaCount(int localAkumaCount) {
        this.localAkumaCount = localAkumaCount;
    }

}
