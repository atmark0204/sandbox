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

public class GvLogTableTest {

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(GvLogTableTest.class.getName());
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
     * toTimelineData No.01.
     */
    @Test
    public void toTimelineDataTest01() {

        GvLogTable target = new GvLogTable();
        target.setDate("22:00");
        target.setPoint0(100);
        target.setPoint1(500);

        @SuppressWarnings("unchecked")
        XYChart.Data<String, Number>[] actual = target.toTimelineData();

        assertThat(actual.length, is(2));
        assertThat(actual[0].getXValue(), is("22:00"));
        assertThat(actual[0].getYValue().intValue(), is(100));
        assertThat(actual[1].getXValue(), is("22:00"));
        assertThat(actual[1].getYValue().intValue(), is(500));
    }

    /**
     * toLogFormat No.01.
     */
    @Test
    public void toLogFormatTest01() {

        GvLogTable target = new GvLogTable();
        target.setDate("22:00");
        target.setSrcCharaName("src");
        target.setDstCharaName("dst");
        target.setGuildName(0);
        target.setPoint(200);
        target.setPoint0(100);
        target.setPoint1(500);

        String actual = target.toLogFormat();

        assertThat(actual, is("22:00\tsrc\tdst\t0\t200\t100\t500"));
    }

}
