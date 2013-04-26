package org.ramidore.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.ramidore.logic.AbstractLogic;
import org.ramidore.util.RamidoreUtil;

/**
 * Test.
 *
 * @author atmark
 *
 */
public class RamidoreUtilTest {

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(RamidoreUtilTest.class.getName());
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
     * constructor No01.
     *
     */
    void constructorTest01() throws Exception {

        // TODO 実行する方法探す
    }

    /**
     * intValueFromDescHexString No01.
     */
    @Test
    public void intValueFromDescHexStringTest01() {

        String hexStr = "00";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(0));
    }

    /**
     * intValueFromDescHexString No02.
     */
    @Test
    public void intValueFromDescHexStringTest02() {

        String hexStr = "01";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(1));
    }

    /**
     * intValueFromDescHexString No03.
     */
    @Test
    public void intValueFromDescHexStringTest03() {

        String hexStr = "0F";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(15));
    }

    /**
     * intValueFromDescHexString No04.
     */
    @Test
    public void intValueFromDescHexStringTest04() {

        String hexStr = "0000";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(0));
    }

    /**
     * intValueFromDescHexString No05.
     */
    @Test
    public void intValueFromDescHexStringTest05() {

        String hexStr = "0100";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(1));
    }

    /**
     * intValueFromDescHexString No06.
     */
    @Test
    public void intValueFromDescHexStringTest06() {

        String hexStr = "0F00";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(15));
    }

    /**
     * intValueFromDescHexString No07.
     */
    @Test
    public void intValueFromDescHexStringTest07() {

        String hexStr = "0001";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(256));
    }

    /**
     * intValueFromDescHexString No08.
     */
    @Test
    public void intValueFromDescHexStringTest08() {

        String hexStr = "000F";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(3840));
    }

    /**
     * intValueFromDescHexString No09.
     */
    @Test
    public void intValueFromDescHexStringTest09() {

        String hexStr = "ABCD";

        int actual = RamidoreUtil.intValueFromDescHexString(hexStr);

        assertThat(actual, is(52651));
    }

    /**
     * encode No01.
     */
    @Test
    public void encodeTest01() {

        String hexStr = "82A0";

        String actual = RamidoreUtil.encode(hexStr, AbstractLogic.ENCODING);

        assertThat(actual, is("あ"));
    }

    /**
     * encode No02.
     */
    @Test
    public void encodeTest02() {

        String hexStr = "82A0";

        String actual = RamidoreUtil.encode(hexStr, "NotDefinedEncoding");

        assertThat(actual, is("エンコード失敗"));
    }

    /**
     * toHex No01.
     */
    @Test
    public void toHexTest01() {

        byte[] bytes = new byte[] {};

        String actual = RamidoreUtil.toHex(bytes);

        assertThat(actual, is(""));
    }

    /**
     * toHex No02.
     */
    @Test
    public void toHexTest02() {

        byte[] bytes = new byte[] { (byte) 0x00 };

        String actual = RamidoreUtil.toHex(bytes);

        assertThat(actual, is("00"));
    }

    /**
     * toHex No03.
     */
    @Test
    public void toHexTest03() {

        byte[] bytes = new byte[] { (byte) 0xFF };

        String actual = RamidoreUtil.toHex(bytes);

        assertThat(actual, is("FF"));
    }

    /**
     * toHex No03.
     */
    @Test
    public void toHexTest04() {

        byte[] bytes = new byte[] { (byte) 0x00, (byte) 0x01 };

        String actual = RamidoreUtil.toHex(bytes);

        assertThat(actual, is("0001"));
    }

    /**
     * intValueFromDescByteArray No01.
     */
    @Test
    public void intValueFromDescByteArrayTest01() {

        byte[] bytes = new byte[] { (byte) 0x00 };

        int actual = RamidoreUtil.intValueFromDescByteArray(bytes);

        assertThat(actual, is(0));
    }

    /**
     * intValueFromDescByteArray No02.
     */
    @Test
    public void intValueFromDescByteArrayTest02() {

        byte[] bytes = new byte[] { (byte) 0xFF };

        int actual = RamidoreUtil.intValueFromDescByteArray(bytes);

        assertThat(actual, is(255));
    }

    /**
     * intValueFromDescByteArray No03.
     */
    @Test
    public void intValueFromDescByteArrayTest03() {

        byte[] bytes = new byte[] { (byte) 0x00, (byte) 0x01 };

        int actual = RamidoreUtil.intValueFromDescByteArray(bytes);

        assertThat(actual, is(256));
    }
}
