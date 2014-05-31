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

package org.ramidore.controller;

import javafx.fxml.Initializable;
import org.ramidore.core.PacketAnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractController.
 * <p>
 * サブのコントローラ
 *
 * @author atmark
 */
public abstract class AbstractController implements Initializable {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

    /**
     * パケット解析サービス.
     * <p>
     * JavaFXアプリケーションスレッドとは異なるスレッドで実行される
     */
    private PacketAnalyzeService service;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        concreteInitialize();
    }

    /**
     * 実初期化メソッド.
     */
    abstract public void concreteInitialize();


    /**
     * getter.
     *
     * @return service
     */
    public PacketAnalyzeService getService() {
        return service;
    }

    /**
     * setter.
     *
     * @param service セットする service
     */
    public void setService(PacketAnalyzeService service) {
        this.service = service;
    }

    /**
     * getter.
     *
     * @return log
     */
    public static Logger getLOG() {
        return LOG;
    }
}
