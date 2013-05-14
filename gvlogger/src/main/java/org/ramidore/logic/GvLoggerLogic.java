package org.ramidore.logic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ramidore.core.PacketData;
import org.ramidore.logic.system.GuildBattleLogLogic;
import org.ramidore.logic.system.GuildBattleLogic;

/**
 * . メインロジック
 *
 * @author atmark
 *
 */
public final class GvLoggerLogic extends AbstractMainLogic {

    /**
     * パケット解析ロジック.
     */
    private GuildBattleLogic guildBattleLogic;

    /**
     * ログ1ファイルに対応するロジックのインスタンスマップ.
     */
    private Map<String, GuildBattleLogLogic> guiBattleLogMap;

    /**
     * コンストラクタ.
     */
    public GvLoggerLogic() {

        guildBattleLogic = new GuildBattleLogic();

        guiBattleLogMap = new HashMap<String, GuildBattleLogLogic>();
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

        guiBattleLogMap.put(f.getName(), new GuildBattleLogLogic(f.getAbsolutePath()));
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

    /**
     * getter.
     *
     * @return guiBattleLogMap
     */
    public Map<String, GuildBattleLogLogic> getGuiBattleLogMap() {
        return guiBattleLogMap;
    }

    /**
     * setter.
     *
     * @param guiBattleLogMap セットする guiBattleLogMap
     */
    public void setGuiBattleLogMap(Map<String, GuildBattleLogLogic> guiBattleLogMap) {
        this.guiBattleLogMap = guiBattleLogMap;
    }
}
