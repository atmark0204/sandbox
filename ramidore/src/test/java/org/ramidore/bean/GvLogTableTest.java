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

public class GvLogTableTest {

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(GvLogTableTest.class.getName());
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
