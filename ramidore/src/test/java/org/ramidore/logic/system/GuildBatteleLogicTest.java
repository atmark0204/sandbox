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

package org.ramidore.logic.system;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.ramidore.core.PacketData;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GuildBatteleLogicTest {

    /**
     * テスト対象.
     */
    private static GuildBattleLogic target;

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(GuildBatteleLogicTest.class.getName());
    }

    /**
     * @throws java.lang.Exception 例外
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass");

        target = new GuildBattleLogic();
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
    @Test
    public void testExecute01() {

        PacketData data = new PacketData(new Date(), new byte[]{(byte) 0x00});
        data.setStrData("98002811CDCDCDCD070000000C0032110000060000C01400380069120000000055030000F2240000CF1B000082B682E182A089B4BCCC82E282E982ED4800836683428358814583708365838B5F4400CC0C0032110000060000C0FFFF12002311000011002D0B150B510BEB0AD1030C0029110000F1011027A00B0C002411000009103E0CCA0B1200231100001200BB12");

        boolean result = target.execute(data);

        assertThat(result, is(true));
    }
}
