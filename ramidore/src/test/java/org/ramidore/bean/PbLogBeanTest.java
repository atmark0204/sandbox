package org.ramidore.bean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import javafx.scene.chart.XYChart;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;

public class PbLogBeanTest {

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(PbLogBeanTest.class.getName());
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
