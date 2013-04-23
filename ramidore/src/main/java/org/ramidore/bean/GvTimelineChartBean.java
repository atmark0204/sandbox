package org.ramidore.bean;

import javafx.scene.chart.XYChart;

public class GvTimelineChartBean {

    /**
     * 0 : 先入れ側
     * 1 : 後入れ側
     */
    private int series;

    private String date;

    private int point = 0;

    public XYChart.Data<String, Number> toChartData() {

        return new XYChart.Data<String, Number>(date, point);
    }

    /**
     * getter.
     *
     * @return series
     */
    public int getSeries() {
        return series;
    }

    /**
     * setter.
     *
     * @param series セットする series
     */
    public void setSeries(int series) {
        this.series = series;
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
}
