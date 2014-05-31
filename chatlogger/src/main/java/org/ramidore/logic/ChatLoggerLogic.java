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

package org.ramidore.logic;

import lombok.Getter;
import lombok.Setter;
import org.ramidore.core.PacketData;
import org.ramidore.logic.chat.*;
import org.ramidore.logic.item.ItemLogic;
import org.ramidore.logic.system.DameonMessageLogic;
import org.ramidore.logic.system.RedstoneLogic;

import java.util.LinkedList;
import java.util.Properties;

/**
 * . メインロジック
 *
 * @author atmark
 */
public class ChatLoggerLogic extends AbstractMainLogic {

    /**
     * お知らせ表示の初期設定.
     */
    @Getter
    @Setter
    private boolean noticeable;

    /**
     * お知らせの表示行数.
     */
    @Getter
    @Setter
    private int oshiraseLineCount;

    /**
     * お知らせメッセージ.
     */
    private LinkedList<String> oshiraseMessages;

    /**
     * 叫び.
     */
    @Getter
    private SakebiChatLogic sakebiChatLogic;

    /**
     * 通常チャット.
     */
    @Getter
    private NormalChatLogic normalChatLogic;

    /**
     * PTチャット.
     */
    @Getter
    private PartyChatLogic partyChatLogic;

    /**
     * ギルドチャット.
     */
    @Getter
    private GuildChatLogic guildChatLogic;

    /**
     * 耳打ち.
     */
    @Getter
    private MimiChatLogic mimiChatLogic;

    /**
     * RS返却.
     */
    @Getter
    private RedstoneLogic redstoneLogic;

    /**
     * 運営メッセージ.
     */
    private DameonMessageLogic dameonMessageLogic;

    /**
     * アイテム取得.
     */
    @Getter
    private ItemLogic itemLogic;

    /**
     * コンストラクタ.
     */
    public ChatLoggerLogic() {

        // チャット関連
        sakebiChatLogic = new SakebiChatLogic();
        normalChatLogic = new NormalChatLogic();
        partyChatLogic = new PartyChatLogic();
        guildChatLogic = new GuildChatLogic();
        mimiChatLogic = new MimiChatLogic();

        // システムメッセージ関連
        redstoneLogic = new RedstoneLogic();
        dameonMessageLogic = new DameonMessageLogic();

        // アイテム関連
        itemLogic = new ItemLogic();
    }

    @Override
    public boolean execute(PacketData data) {

        // 短いデータは無視する
        if (data.getRawData().length < 17) {
            return false;
        }

        if (dameonMessageLogic.execute(data)) {
            // ギルチャより先に処理しないと引っかかる
            return true;
        }

        if (sakebiChatLogic.execute(data)) {

            if (sakebiChatLogic.isNoticeable()) {
                oshiraseMessages.offer(sakebiChatLogic.getOshiraseMessage());
            }

            return true;
        }

        if (normalChatLogic.execute(data)) {

            if (normalChatLogic.isNoticeable()) {
                oshiraseMessages.offer(normalChatLogic.getOshiraseMessage());
            }

            return true;
        }

        if (partyChatLogic.execute(data)) {

            if (partyChatLogic.isNoticeable()) {
                oshiraseMessages.offer(partyChatLogic.getOshiraseMessage());
            }

            return true;
        }

        if (guildChatLogic.execute(data)) {

            if (guildChatLogic.isNoticeable()) {
                oshiraseMessages.offer(guildChatLogic.getOshiraseMessage());
            }

            return true;
        }

        if (mimiChatLogic.execute(data)) {

            if (mimiChatLogic.isNoticeable()) {
                oshiraseMessages.offer(mimiChatLogic.getOshiraseMessage());
            }

            return true;
        }

        if (redstoneLogic.execute(data)) {
            return true;
        }

        if (itemLogic.execute(data)) {
            return true;
        }

        return false;
    }


    @Override
    public void loadConfig(Properties config) {

        noticeable = Boolean.parseBoolean(config.getProperty("oshirase.enabled", "false"));
        oshiraseLineCount = Integer.parseInt(config.getProperty("oshirase.linecount", "1"));
        oshiraseMessages = new LinkedList<String>();

        sakebiChatLogic.loadConfig(config);
        normalChatLogic.loadConfig(config);
        partyChatLogic.loadConfig(config);
        guildChatLogic.loadConfig(config);
        mimiChatLogic.loadConfig(config);
        itemLogic.loadConfig(config);
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("oshirase.enabled", String.valueOf(noticeable));
        config.setProperty("oshirase.linecount", String.valueOf(oshiraseLineCount));

        sakebiChatLogic.saveConfig(config);
        normalChatLogic.saveConfig(config);
        partyChatLogic.saveConfig(config);
        guildChatLogic.saveConfig(config);
        mimiChatLogic.saveConfig(config);
        itemLogic.saveConfig(config);
    }

    @Override
    public final void loadConfig() {
        // nop
    }

    @Override
    public final void saveConfig() {
        // nop
    }

    @Override
    public String getOshiraseMessage() {

        while (oshiraseMessages.size() > oshiraseLineCount) {

            oshiraseMessages.remove();
        }

        StringBuilder builder = new StringBuilder("<html>");

        for (int i = 0; i < oshiraseMessages.size(); i++) {

            String line = oshiraseMessages.get(i);

            if (i < oshiraseMessages.size() - 1) {
                builder.append(line + "<br />");
            } else {
                builder.append(line + "</html>");
            }
        }

        return builder.toString();
    }

    @Override
    public boolean isNoticeable() {
        return noticeable;
    }
}
