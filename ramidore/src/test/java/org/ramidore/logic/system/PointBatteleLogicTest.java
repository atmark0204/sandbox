package org.ramidore.logic.system;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import javafx.scene.control.TableView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.ramidore.bean.PbStatTable;
import org.ramidore.core.PacketData;

public class PointBatteleLogicTest {

    /**
     * テスト対象.
     */
    private static PointBattleLogic target;

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(PointBatteleLogicTest.class.getName());
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");

        target = new PointBattleLogic();

        target.setStatTable(new TableView<PbStatTable>());
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
    //@Test
    public void testExecute01() {

        PacketData data = new PacketData(new Date(), new byte[]{(byte) 0x00});
        data.setStrData("4500014E54EA400074062816CB8DF166C0A80B0DD567DB19F638D0778777111D5018FFE9C8370000DE002811CDCDCDCD060000008C00BA1100000A00010000003C633A57484954453E82DC82BE91E6318347838A834182C9934782AA8E6382C182C482A282E981493C6E3E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000CC1200231100006100121B7107121B5A07F8020C0032110000060000C06D0010005C1200000B0080340000C08802000C00241100006A10B11CED070C0047110000A73A06006D0048002811CDCDCDCD040000001200231100005F00571AAE06741A0307F8020C00241100001B30310D0F090C00241100006B00DC1CD8071200231100006800ED1CE605F31CED05F802");

        boolean result = target.execute(data);

        assertThat(result, is(true));
    }

    /**
     * execute No02.
     */
    //@Test
    public void testExecute02() {

        PacketData data = new PacketData(new Date(), new byte[]{(byte) 0x00});
        data.setStrData("450000F00FCB00007406AD93CB8DF166C0A80B0DD567DB19F646B153877717C15018FF3141650000C8002811CDCDCDCD040000008C00BA1100000A00000000003C633A4C54475245454E3E90A78CC08E9E8AD43C6E3E3C633A57484954453E82AA81413C6E3E3C633A4C5459454C4C4F573E323095623C6E3E3C633A57484954453E898492B782B382EA82DC82B582BD3C6E3E0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000CC10005C1200000B0000AF0000002A760010005C1200000100A60000000000000010005C1200000100A600000000000000");

        boolean result = target.execute(data);

        assertThat(result, is(true));
    }
}
