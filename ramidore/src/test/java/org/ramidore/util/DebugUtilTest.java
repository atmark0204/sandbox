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

package org.ramidore.util;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.ramidore.core.PacketData;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test.
 *
 * @author atmark
 */
public class DebugUtilTest {

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(DebugUtilTest.class.getName());
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
     * hexDump No.01.
     * <p>
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
     * <p>
     * size = 1
     */
    @Test
    public void hexDumpTest02() {

        byte[] b = new byte[]{(byte) 0x30};

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
     * <p>
     * size = 16
     */
    @Test
    public void hexDumpTest03() {

        byte[] b = new byte[]{
                (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38, (byte) 0x39,
                (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x44, (byte) 0x45, (byte) 0x46};

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
     * <p>
     * size = 17
     */
    @Test
    public void hexDumpTest04() {

        byte[] b = new byte[]{
                (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38, (byte) 0x39,
                (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x44, (byte) 0x45, (byte) 0x46, (byte) 0x61};

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
     * <p>
     * size = 1
     * NULL
     * <p>
     * TODO 制御文字どうする？
     */
    @Test
    @Ignore
    public void hexDumpTest05() {

        byte[] b = new byte[]{(byte) 0x00};

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
