package org.ramidore.logic.chat;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import javafx.collections.ObservableList;
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
public class SakebiChatLogicTest {

    /**
     * テスト対象.
     */
    private static SakebiChatLogic target;

    /**
     * Main.
     *
     * @param args
     *            引数
     */
    public static void main(String[] args) {
        JUnitCore.main(SakebiChatLogicTest.class.getName());
    }

    /**
     * @throws java.lang.Exception
     *             例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");

        target = new SakebiChatLogic();
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
     *
     * 末尾が以下のパターンで終わらない場合
     *
     * 000000$ 000000(.{2})+$
     */
    @Test
    public void testExecute01() {

        target.setTable(new TableView<ChatTable>());

        byte[] b = {};

        PacketData data = new PacketData(new Date(), b);

        data.setStrData("43002811CDCDCDCD0200000008004F110000BC002F005811CCCCCCCCE3C80CC142455744009483816A57895E94E483418393836081408EA882E682EB82C582B7");

        boolean result = target.execute(data);

        assertThat(result, is(true));

        ObservableList<ChatTable> list = target.getTable().getItems();

        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is("BEWD"));
    }

    /**
     * execute No02.
     *
     * 前方にゴミパケット
     */
    @Test
    public void testExecute02() {

        target.setTable(new TableView<ChatTable>());

        byte[] b = {};

        PacketData data = new PacketData(new Date(), b);

        data.setStrData("24012811CDCDCDCD0400000041002E110000CCCC02B0DE17958D96C1FFC1530005028C000000640011074D03000000000000000000000000000000000000000082E482AB82DD91E5959F00CCCC41002E110000CCCC02A0DE17C18F96C1FF93530005028C00000064005E074803000000000000000000000000000000000000000082A282BF82B291E5959F00CCCC39002E110000CCCC02701E19811096C1FEAD0A0035008C00000064007707A9020000000000000000000000000000000000000000736200CCCC5D005811CCCCCCCCBFC80CC182DC815B82AD82F1825200834D838B83688D6782A2978382C582CD8341838A815B836957495A82B382F182F095E58F57928682C582B781408FDA8DD782CD8EA882A88AE882A282B582DC82B7814200000014002811CDCDCDCD01000000");

        boolean result = target.execute(data);

        assertThat(result, is(true));

        ObservableList<ChatTable> list = target.getTable().getItems();

        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is("まーくん３"));
    }
}
