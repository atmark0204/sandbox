package org.ramidore.logic.chat;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import javafx.scene.control.TableView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.ramidore.bean.ChatTable;
import org.ramidore.core.PacketData;

/**
 * Test.
 *
 * @author atmark
 *
 */
public class GuildChatLogicTest {

    /**
     * テスト対象.
     */
    private static GuildChatLogic target;

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(GuildChatLogicTest.class.getName());
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");

        target = new GuildChatLogic();
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

        target.setTable(new TableView<ChatTable>());

        byte[] b = new byte[] { (byte) 0xFF, (byte) 0x00, (byte) 0x58, (byte) 0x11, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC,
                (byte) 0xFF, (byte) 0xFF, (byte) 0xC0, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

        PacketData data = new PacketData(new Date(), b);

        boolean result = target.execute(data);

        assertThat(result, is(true));
        assertThat(target.getTable().getItems().size(), is(1));
    }

    /**
     * execute No02.
     */
    @Test
    public void testExecute02() {

        target.setTable(new TableView<ChatTable>());

        byte[] b = new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0x58, (byte) 0x11, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC,
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

        PacketData data = new PacketData(new Date(), b);

        boolean result = target.execute(data);

        assertThat(result, is(false));
        assertThat(target.getTable().getItems().size(), is(0));
    }
}
