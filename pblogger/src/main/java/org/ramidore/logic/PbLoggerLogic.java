package org.ramidore.logic;

import lombok.Getter;
import lombok.Setter;
import org.ramidore.core.PacketData;
import org.ramidore.logic.system.PointBattleLogic;

import java.util.Properties;

/**
 * . メインロジック
 *
 * @author atmark
 */
public final class PbLoggerLogic extends AbstractMainLogic {

    /**
     * ポイント戦.
     */
    @Getter
    @Setter
    private PointBattleLogic pointBattleLogic;

    /**
     * コンストラクタ.
     */
    public PbLoggerLogic() {

        pointBattleLogic = new PointBattleLogic();
    }

    @Override
    public boolean execute(PacketData data) {

        // 短いデータは無視する
        if (data.getRawData().length < 17) {
            return false;
        }

        if (pointBattleLogic.execute(data)) {
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
}
