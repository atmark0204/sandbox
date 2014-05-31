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

package org.ramidore.bean;

import org.junit.*;
import org.junit.runner.JUnitCore;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChatTableTest {

    /**
     * Main.
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        JUnitCore.main(ChatTableTest.class.getName());
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
     * toString No.01.
     */
    @Test
    public void toStringTest01() {

        ChatTable target = new ChatTable("name", "somechat");

        String actual = target.toString();

        assertThat(actual, is("【name】 somechat"));
    }

    /**
     * toString No.02.
     */
    @Test
    public void toStringTest02() {

        ChatTable target = new ChatTable(new Date(), "name", "somechat");

        String actual = target.toString();

        assertThat(actual, is("【name】 somechat"));
    }

    /**
     * toString No.03.
     */
    @Test
    public void toStringTest03() {

        ChatTable target = new ChatTable(new Date(), "from ", "name", "somechat");

        String actual = target.toString();

        assertThat(actual, is("from 【name】 somechat"));
    }

    /**
     * getName No.01.
     */
    @Test
    public void getNameTest01() {

        ChatTable target = new ChatTable(new Date(), "name", "somechat");

        String actual = target.getName();

        assertThat(actual, is("name"));
    }

    /**
     * getName No.02.
     */
    @Test
    public void getNameTest02() {

        ChatTable target = new ChatTable(new Date(), "to ", "name", "somechat");

        String actual = target.getName();

        assertThat(actual, is("name"));
    }

}
