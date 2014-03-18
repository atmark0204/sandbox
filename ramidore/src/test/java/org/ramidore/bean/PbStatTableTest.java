package org.ramidore.bean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;

public class PbStatTableTest {

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(PbStatTableTest.class.getName());
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("tearDownAfterClass");
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp");
    }

    /**
     * @throws java.lang.Exception
     *             例外
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
