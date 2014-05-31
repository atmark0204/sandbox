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

import org.junit.*;
import org.junit.runner.JUnitCore;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PbStatTableTest {

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(PbStatTableTest.class.getName());
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
     * toBarChartData No.01.
     */
    @Test
    public void toBarChartDataTest01() {

        PbStatTable target = new PbStatTable();
        target.setId("id");
        target.setPoint1(1);
        target.setMobCount1(11);
        target.setPoint2(2);
        target.setMobCount2(22);
        target.setPoint3(3);
        target.setMobCount3(33);
        target.setPoint4(4);
        target.setMobCount4(44);
        target.setPoint5(5);
        target.setMobCount5(55);
        target.setPointTotal(6);

        target.setStage1();
        target.setStage2();
        target.setStage3();
        target.setStage4();
        target.setStage5();

        String actual = target.toCopyStr();

        assertThat(actual, is("id\t1(11)\t2(22)\t3(33)\t4(44)\t5(55)\t6"));
    }
}
