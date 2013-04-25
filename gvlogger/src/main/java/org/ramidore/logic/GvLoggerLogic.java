package org.ramidore.logic;

import java.util.Properties;

import org.ramidore.core.PacketData;
import org.ramidore.logic.system.GuildBattleLogic;

/**
 * . メインロジック
 *
 * @author atmark
 *
 */
public final class GvLoggerLogic extends AbstractMainLogic {

    /**
     * ギルド戦.
     */
    private GuildBattleLogic guildBattleLogic;

    /**
     * コンストラクタ.
     */
    public GvLoggerLogic() {

        guildBattleLogic = new GuildBattleLogic();
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
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getOshiraseMessage() {
        return null;
    }

    /**
     * getter.
     *
     * @return guildBattleLogic
     */
    public GuildBattleLogic getGuildBattleLogic() {
        return guildBattleLogic;
    }

    /**
     * setter.
     *
     * @param guildBattleLogic セットする guildBattleLogic
     */
    public void setGuildBattleLogic(GuildBattleLogic guildBattleLogic) {
        this.guildBattleLogic = guildBattleLogic;
    }
}
