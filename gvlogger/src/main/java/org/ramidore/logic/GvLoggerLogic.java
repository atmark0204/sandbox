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
import org.ramidore.core.PacketData;
import org.ramidore.logic.system.GuildBattleLogLogic;
import org.ramidore.logic.system.GuildBattleLogic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * . メインロジック
 *
 * @author atmark
 */
public final class GvLoggerLogic extends AbstractMainLogic {

    /**
     * パケット解析ロジック.
     */
    @Getter
    private GuildBattleLogic guildBattleLogic;

    /**
     * ログ1ファイルに対応するロジックのインスタンスマップ.
     */
    @Getter
    private Map<String, GuildBattleLogLogic> guildBattleLogMap;

    /**
     * コンストラクタ.
     */
    public GvLoggerLogic() {

        guildBattleLogic = new GuildBattleLogic();

        guildBattleLogMap = new HashMap<>();
    }

    @Override
    public void hookOnTaskStart() {
    }

    @Override
    public boolean execute(PacketData data) {

        // 短いデータは無視する
        if (data.getRawData().length < 17) {
            return false;
        }

        if (guildBattleLogic.execute(data)) {
            return true;
        }

        return false;
    }


    @Override
    public void loadConfig(Properties config) {
        // nop
    }

    @Override
    public void saveConfig(Properties config) {
        // nop
    }

    @Override
    public void loadConfig() {
        // nop
    }

    @Override
    public void saveConfig() {
        // nop
    }

    @Override
    public boolean isNoticeable() {
        return false;
    }

    @Override
    public String getOshiraseMessage() {
        return null;
    }

    /**
     * ログの読み込み.
     *
     * @param f ファイル
     */
    public void loadPastData(File f) {

        guildBattleLogMap.put(f.getName(), new GuildBattleLogLogic(f.getAbsolutePath()));
    }
}
