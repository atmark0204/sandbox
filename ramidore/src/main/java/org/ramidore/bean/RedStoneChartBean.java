/*
 * Copyright 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ramidore.bean;

import javafx.scene.chart.AreaChart;
import lombok.Getter;
import lombok.Setter;

/**
 * RedStone返却数チャート用のデータ.
 *
 * @author atmark
 */
@Getter
@Setter
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
}
