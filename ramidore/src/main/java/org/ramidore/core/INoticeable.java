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

package org.ramidore.core;

/**
 * お知らせを提供するインターフェース.
 *
 * @author atmark
 */
public interface INoticeable {

    /**
     * お知らせ可能か.
     *
     * @return 可/否
     */
    boolean isNoticeable();

    /**
     * お知らせメッセージを返す.
     *
     * @return お知らせメッセージ
     */
    String getOshiraseMessage();
}
