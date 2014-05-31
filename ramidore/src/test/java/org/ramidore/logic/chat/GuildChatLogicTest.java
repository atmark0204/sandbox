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

package org.ramidore.logic.chat;

import javafx.scene.control.TableView;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.ramidore.bean.ChatTable;
import org.ramidore.core.PacketData;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author atmark
 */
public class GuildChatLogicTest {

    /**
     * テスト対象.
     */
    private static GuildChatLogic target;

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(GuildChatLogicTest.class.getName());
    }

    /**
     * @throws java.lang.Exception 例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");

        target = new GuildChatLogic();
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
     * execute No01.
     */
    //@Test
    public void testExecute01() {

        target.setTable(new TableView<ChatTable>());

        byte[] b = new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x58, (byte) 0x11, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC,
                (byte) 0xFF, (byte) 0xFF, (byte) 0xC0, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        PacketData data = new PacketData(new Date(), b);

        boolean result = target.execute(data);

        assertThat(result, is(true));
        assertThat(target.getTable().getItems().size(), is(1));
    }

    /**
     * execute No02.
     */
    //@Test
    public void testExecute02() {

        target.setTable(new TableView<ChatTable>());

        byte[] b = new byte[]{(byte) 0xFF, (byte) 0x01, (byte) 0x58, (byte) 0x11, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC,
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        PacketData data = new PacketData(new Date(), b);

        boolean result = target.execute(data);

        assertThat(result, is(false));
        assertThat(target.getTable().getItems().size(), is(0));
    }
}
