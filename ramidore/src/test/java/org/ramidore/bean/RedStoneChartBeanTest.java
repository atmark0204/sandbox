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

import javafx.scene.chart.XYChart;
import org.junit.*;
import org.junit.runner.JUnitCore;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * RedStoneChartBean.
 *
 * @author atmark
 */
public class RedStoneChartBeanTest {

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(RedStoneChartBeanTest.class.getName());
    }

    /**
     * @throws java.lang.Exception 例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");
    }

    /**
     * @throws java.lang.Exception 例外
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("tearDownAfterClass");
    }

    /**
     * @throws java.lang.Exception 例外
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp");
    }

    /**
     * @throws java.lang.Exception 例外
     */
    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");
    }

    /**
     * toTenjoData No.01.
     */
    @Test
    public void toTenjoDataTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);
        target.setLocalTotalCount();

        XYChart.Data<Number, Number> actual = target.toTenjoData();

        assertThat(actual.getXValue().intValue(), is(6));
        assertThat(actual.getYValue().intValue(), is(1));
    }

    /**
     * toChikaData No.01.
     */
    @Test
    public void toChikaDataTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);
        target.setLocalTotalCount();

        XYChart.Data<Number, Number> actual = target.toChikaData();

        assertThat(actual.getXValue().intValue(), is(6));
        assertThat(actual.getYValue().intValue(), is(2));
    }

    /**
     * toAkumaData No.01.
     */
    @Test
    public void toAkumaDataTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);
        target.setLocalTotalCount();

        XYChart.Data<Number, Number> actual = target.toAkumaData();

        assertThat(actual.getXValue().intValue(), is(6));
        assertThat(actual.getYValue().intValue(), is(3));
    }

    /**
     * addTenjo No.01.
     */
    @Test
    public void addTenjoTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);

        target.addTenjo();

        int actual = target.getLocalTenjoCount();

        assertThat(actual, is(2));
    }

    /**
     * addChika No.01.
     */
    @Test
    public void addChikaTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);

        target.addChika();

        int actual = target.getLocalChikaCount();

        assertThat(actual, is(3));
    }

    /**
     * addAkuma No.01.
     */
    @Test
    public void addAkumaTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);

        target.addAkuma();

        int actual = target.getLocalAkumaCount();

        assertThat(actual, is(4));
    }

    /**
     * setLocalTotalCount No.01.
     */
    @Test
    public void setLocalTotalCountTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);

        target.setLocalTotalCount();

        int actual = target.getLocalTotalCount();

        assertThat(actual, is(6));
    }

    /**
     * setCurrentTotalCount No.01.
     */
    @Test
    public void setCurrentTotalCountTest01() {

        RedStoneChartBean target = new RedStoneChartBean();
        target.setLocalTenjoCount(1);
        target.setLocalChikaCount(2);
        target.setLocalAkumaCount(3);
        target.setCurrentTenjoCount(11);
        target.setCurrentChikaCount(22);
        target.setCurrentAkumaCount(33);

        target.setCurrentTotalCount();

        int actual = target.getCurrentTotalCount();

        assertThat(actual, is(66));
    }
}
