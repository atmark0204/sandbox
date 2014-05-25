package org.ramidore.bean;

import javafx.scene.chart.XYChart;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * ギルド戦のログ.
 *
 * @author atmark
 *
 */
@Getter
@Setter
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
     * <p/>
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
     * 先入れ側ギルド名.
     */
    private String strictGuildName0 = "先入れ側";

    /**
     * 後入れ側ギルド名.
     */
    private String strictGuildName1 = "後入れ側";

    /**
     * チャート用データに変換
     * <p/>
     * length = 2
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

        Object[] array = new Object[]{
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
}