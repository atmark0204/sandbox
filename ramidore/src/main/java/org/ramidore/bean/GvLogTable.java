package org.ramidore.bean;

import javafx.scene.chart.XYChart;

import org.apache.commons.lang3.StringUtils;

public class GvLogTable {

    /**
     * 日付.
     */
    private String date;

    /**
     * 倒したキャラ名.
     */
    private String srcCharaName = StringUtils.EMPTY;

    /**
     * 倒されたキャラ名.
     */
    private String dstCharaName = StringUtils.EMPTY;

    /**
     * ギルド名
     *
     * 0 : 先入れ側
     * 1 : 後入れ側
     */
    private int guildName = -1;

    /**
     * 入った点数.
     */
    private int point = 0;

    /**
     * 先入れ側点数.
     */
    private int point0 = 0;

    /**
     * 後入れ側点数.
     */
    private int point1 = 0;

    /**
     * チャート用データに変換
     *
     *  length = 2
     *
     * @return XYChart.Data[]
     */
    @SuppressWarnings("rawtypes")
    public XYChart.Data[] toTimelineData() {

        XYChart.Data<String, Number> data0 = new XYChart.Data<String, Number>(date, point0);
        XYChart.Data<String, Number> data1 = new XYChart.Data<String, Number>(date, point1);

        return new XYChart.Data[]{data0, data1};
    }

    public String toLogFormat() {

        Object[] array = new Object[] {
                this.date,
                this.srcCharaName,
                this.dstCharaName,
                this.guildName,
                this.point,
                this.point0,
                this.point1
        };

        return StringUtils.join(array, '\t');
    }

    /**
     * getter.
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * setter.
     *
     * @param date セットする date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * getter.
     *
     * @return srcCharaName
     */
    public String getSrcCharaName() {
        return srcCharaName;
    }

    /**
     * setter.
     *
     * @param srcCharaName セットする srcCharaName
     */
    public void setSrcCharaName(String srcCharaName) {
        this.srcCharaName = srcCharaName;
    }

    /**
     * getter.
     *
     * @return dstCharaName
     */
    public String getDstCharaName() {
        return dstCharaName;
    }

    /**
     * setter.
     *
     * @param dstCharaName セットする dstCharaName
     */
    public void setDstCharaName(String dstCharaName) {
        this.dstCharaName = dstCharaName;
    }

    /**
     * getter.
     *
     * @return guildName
     */
    public int getGuildName() {
        return guildName;
    }

    /**
     * setter.
     *
     * @param guildName セットする guildName
     */
    public void setGuildName(int guildName) {
        this.guildName = guildName;
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
     * @param point セットする point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * getter.
     *
     * @return point0
     */
    public int getPoint0() {
        return point0;
    }

    /**
     * setter.
     *
     * @param point0 セットする point0
     */
    public void setPoint0(int point0) {
        this.point0 = point0;
    }

    /**
     * getter.
     *
     * @return point1
     */
    public int getPoint1() {
        return point1;
    }

    /**
     * setter.
     *
     * @param point1 セットする point1
     */
    public void setPoint1(int point1) {
        this.point1 = point1;
    }
}
