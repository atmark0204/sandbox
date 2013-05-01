package org.ramidore.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.BarChart;

import org.apache.commons.lang3.StringUtils;

public class GvStatTable {

    private SimpleIntegerProperty guildName;

    private SimpleStringProperty charaName;

    private SimpleIntegerProperty killCount;

    private SimpleIntegerProperty deathCount;

    private SimpleIntegerProperty point;

    private SimpleStringProperty note;

    public GvStatTable() {

        guildName = new SimpleIntegerProperty();
        charaName = new SimpleStringProperty();
        killCount = new SimpleIntegerProperty(0);
        deathCount = new SimpleIntegerProperty(0);
        point = new SimpleIntegerProperty(0);
        note = new SimpleStringProperty(StringUtils.EMPTY);
    }

    public SimpleIntegerProperty guildNameProperty() {
        return guildName;
    }

    public SimpleStringProperty charaNameProperty() {
        return charaName;
    }

    public SimpleIntegerProperty pointProperty() {
        return point;
    }

    public SimpleIntegerProperty killCountProperty() {
        return killCount;
    }

    public SimpleIntegerProperty deathCountProperty() {
        return deathCount;
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void addDeathCount() {

        this.deathCount.set(getDeathCount() + 1);
    }

    public BarChart.Data<String, Number> toBarChartData() {

        return new BarChart.Data<String, Number>(charaName.get(), point.get());
    }

    /**
     * getter.
     *
     * @return guildName
     */
    public int getGuildName() {
        return guildName.get();
    }

    /**
     * setter.
     *
     * @param guildName セットする guildName
     */
    public void setGuildName(int guildName) {
        this.guildName.set(guildName);
    }

    /**
     * getter.
     *
     * @return charaName
     */
    public String getCharaName() {
        return charaName.get();
    }

    /**
     * setter.
     *
     * @param charaName セットする charaName
     */
    public void setCharaName(String charaName) {
        this.charaName.set(charaName);
    }

    /**
     * getter.
     *
     * @return killCount
     */
    public int getKillCount() {
        return killCount.get();
    }

    /**
     * setter.
     *
     * @param killCount セットする killCount
     */
    public void setKillCount(int killCount) {
        this.killCount.set(killCount);
    }

    /**
     * getter.
     *
     * @return deathCount
     */
    public int getDeathCount() {
        return deathCount.get();
    }

    /**
     * setter.
     *
     * @param deathCount セットする deathCount
     */
    public void setDeathCount(int deathCount) {
        this.deathCount.set(deathCount);
    }

    /**
     * getter.
     *
     * @return point
     */
    public int getPoint() {
        return point.get();
    }

    /**
     * setter.
     *
     * @param point セットする point
     */
    public void setPoint(int point) {
        this.point.set(point);
    }

    /**
     * getter.
     *
     * @return note
     */
    public String getNote() {
        return note.get();
    }

    /**
     * setter.
     *
     * @param note セットする note
     */
    public void setNote(String note) {
        this.note.set(note);
    }
}
