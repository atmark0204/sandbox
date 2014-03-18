package org.ramidore.logic.system;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.ramidore.core.PacketData;

public class GuildBatteleLogicTest {

    /**
     * テスト対象.
     */
    private static GuildBattleLogic target;

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(GuildBatteleLogicTest.class.getName());
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");

        target = new GuildBattleLogic();
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
     * execute No01.
     */
    @Test
    public void testExecute01() {

        PacketData data = new PacketData(new Date(), new byte[]{(byte) 0x00});
        data.setStrData("98002811CDCDCDCD070000000C0032110000060000C01400380069120000000055030000F2240000CF1B000082B682E182A089B4BCCC82E282E982ED4800836683428358814583708365838B5F4400CC0C0032110000060000C0FFFF12002311000011002D0B150B510BEB0AD1030C0029110000F1011027A00B0C002411000009103E0CCA0B1200231100001200BB12");

        boolean result = target.execute(data);

        assertThat(result, is(true));
    }
}
