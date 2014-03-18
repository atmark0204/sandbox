package org.ramidore.util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.ramidore.core.PacketData;

/**
 * Test.
 *
 * @author atmark
 *
 */
public class DebugUtilTest {

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(DebugUtilTest.class.getName());
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
     * hexDump No.01.
     *
     * size = 0
     */
    @Test
    public void hexDumpTest01() {

        byte[] b = new byte[]{};

        PacketData data = new PacketData(new Date(), b);

        String actual = DebugUtil.hexDump(data);

        String expected =
                "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n" +
                "         +-------------------------------------------------+-----------------+\n" +
                "00000000 |                                                 | \n" +
                "\n" +
                "\n";

        assertThat(actual, is(expected));
    }

    /**
     * hexDump No.02.
     *
     * size = 1
     */
    @Test
    public void hexDumpTest02() {

        byte[] b = new byte[]{(byte)0x30};

        PacketData data = new PacketData(new Date(), b);

        String actual = DebugUtil.hexDump(data);

        String expected =
                "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n" +
                "         +-------------------------------------------------+-----------------+\n" +
                "00000000 | 30                                              | 0\n" +
                "\n" +
                "\n";

        assertThat(actual, is(expected));
    }

    /**
     * hexDump No.03.
     *
     * size = 16
     */
    @Test
    public void hexDumpTest03() {

        byte[] b = new byte[]{
                (byte)0x30, (byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34, (byte)0x35, (byte)0x36, (byte)0x37, (byte)0x38, (byte)0x39,
                (byte)0x41, (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46};

        PacketData data = new PacketData(new Date(), b);

        String actual = DebugUtil.hexDump(data);

        String expected =
                "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n" +
                "         +-------------------------------------------------+-----------------+\n" +
                "00000000 | 30 31 32 33 34 35 36 37 38 39 41 42 43 44 45 46 | 0123456789ABCDEF\n" +
                "\n" +
                "\n";

        assertThat(actual, is(expected));
    }

    /**
     * hexDump No.04.
     *
     * size = 17
     */
    @Test
    public void hexDumpTest04() {

        byte[] b = new byte[]{
                (byte)0x30, (byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34, (byte)0x35, (byte)0x36, (byte)0x37, (byte)0x38, (byte)0x39,
                (byte)0x41, (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x61};

        PacketData data = new PacketData(new Date(), b);

        String actual = DebugUtil.hexDump(data);

        String expected =
                "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n" +
                "         +-------------------------------------------------+-----------------+\n" +
                "00000000 | 30 31 32 33 34 35 36 37 38 39 41 42 43 44 45 46 | 0123456789ABCDEF\n" +
                "00000010 | 61                                              | a\n" +
                "\n" +
                "\n";

        assertThat(actual, is(expected));
    }

    /**
     * hexDump No.05.
     *
     * size = 1
     * NULL
     *
     * TODO 制御文字どうする？
     */
    @Test
    @Ignore
    public void hexDumpTest05() {

        byte[] b = new byte[]{(byte)0x00};

        PacketData data = new PacketData(new Date(), b);

        String actual = DebugUtil.hexDump(data);
/*
        String expected1 =
                "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n" +
                "         +-------------------------------------------------+-----------------+\n" +
                "00000000 | 00                                              | \n" +
                "\n" +
                "\n";
*/
        String expected2 =
                "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n" +
                "         +-------------------------------------------------+-----------------+\n" +
                "00000000 | 00                                              | \0";

        assertThat(actual, is(expected2));
    }

}
