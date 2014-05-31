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

public class PbLogBeanTest {

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(PbLogBeanTest.class.getName());
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
     * toStageData No.01.
     */
    @Test
    public void toStageDataTest01() {

        PbLogBean target = new PbLogBean("id", 100, 1, 2, 100, 0);

        XYChart.Data<Number, Number> actual = target.toStageData();

        assertThat(actual.getXValue().intValue(), is(2));
        assertThat(actual.getYValue().intValue(), is(100));
    }

    /**
     * toStageData No.02.
     */
    @Test
    public void toStageDataTest02() {

        PbLogBean target = new PbLogBean("id", 100, 1, 2, 100, 1);

        XYChart.Data<Number, Number> actual = target.toStageData();

        assertThat(actual.getXValue().intValue(), is(2));
        assertThat(actual.getYValue().intValue(), is(99));
    }

    /**
     * toData No.01.
     */
    @Test
    public void toDataTest01() {

        PbLogBean target = new PbLogBean("id", 100, 1, 2, 100, 0);

        XYChart.Data<Number, Number> actual = target.toData();

        assertThat(actual.getXValue().intValue(), is(100));
        assertThat(actual.getYValue().intValue(), is(100));
    }

    /**
     * toData No.02.
     */
    @Test
    public void toDataTest02() {

        PbLogBean target = new PbLogBean("id", 100, 1, 2, 100, 1);

        XYChart.Data<Number, Number> actual = target.toData();

        assertThat(actual.getXValue().intValue(), is(100));
        assertThat(actual.getYValue().intValue(), is(100));
    }
}
