package org.ramidore.logic;

import java.util.Properties;

import org.ramidore.core.PacketData;
import org.ramidore.logic.AbstractRedomiraLogic;
import org.ramidore.logic.DebugLogic;
import org.ramidore.logic.system.PointBattleLogic;

/**
 * . メインロジック
 *
 * @author atmark
 *
 */
public final class PbLoggerLogic extends AbstractRedomiraLogic {

    /**
     * . 日本語エンコードはWindows-31J
     *
     * ※ SJISだと機種依存文字が化ける
     */
    public static final String ENCODING = "Windows-31J";

    /**
     * 0x00以外の16進文字列にマッチする正規表現パターン.
     */
    public static final String BASE_PATTERN = "((?:[1-9A-F]{2}|[1-9A-F]0|0[1-9A-F])+)";

    /**
     * お知らせメッセージ.
     */
    private String oshiraseMessage;

    /**
     * ポイント戦.
     */
    private PointBattleLogic pointBattleLogic;

    /**
     * デバッグ.
     */
    private DebugLogic debugLogic;

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

        //debugLogic.execute(data);

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
    public final void loadConfig() {
        // nop
    }
    @Override
    public final void saveConfig() {
        // nop
    }

    /**
     * . accessor
     *
     * @return debugLogic
     */
    public DebugLogic getDebugLogic() {
        return debugLogic;
    }

    /**
     * . accessor
     *
     * @param debugLogic
     *            セットする debugLogic
     */
    public void setDebugLogic(DebugLogic debugLogic) {
        this.debugLogic = debugLogic;
    }

    /**
     * . accessor
     *
     * @return oshiraseMessage
     */
    public String getOshiraseMessage() {
        return oshiraseMessage;
    }

    /**
     * . accessor
     *
     * @param oshiraseMessage セットする oshiraseMessage
     */
    public void setOshiraseMessage(String oshiraseMessage) {
        this.oshiraseMessage = oshiraseMessage;
    }

    @Override
    public boolean isEnabled() {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }
}
