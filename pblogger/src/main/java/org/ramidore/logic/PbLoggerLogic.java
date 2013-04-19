package org.ramidore.logic;

import java.util.Properties;

import org.ramidore.core.PacketData;
import org.ramidore.logic.system.PointBattleLogic;

/**
 * . メインロジック
 *
 * @author atmark
 *
 */
public final class PbLoggerLogic extends AbstractRedomiraLogic {

    /**
     * ポイント戦.
     */
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


    /**
     * getter.
     *
     * @return pointBattleLogic
     */
    public PointBattleLogic getPointBattleLogic() {
        return pointBattleLogic;
    }

    /**
     * setter.
     *
     * @param pointBattleLogic セットする pointBattleLogic
     */
    public void setPointBattleLogic(PointBattleLogic pointBattleLogic) {
        this.pointBattleLogic = pointBattleLogic;
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
}
