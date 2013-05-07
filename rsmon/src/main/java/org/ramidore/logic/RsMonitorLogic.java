package org.ramidore.logic;

import java.util.Properties;

import org.ramidore.core.PacketData;
import org.ramidore.logic.system.RedstoneLogic;

/**
 * . メインロジック
 *
 * @author atmark
 *
 */
public class RsMonitorLogic extends AbstractMainLogic {

    /**
     * 0x00以外の16進文字列にマッチする正規表現パターン.
     */
    public static final String BASE_PATTERN = "(([1-9A-F]{2}|[1-9A-F]0|0[1-9A-F])+)";

    /**
     * RS返却.
     */
    private RedstoneLogic redstoneLogic;

    /**
     * コンストラクタ.
     */
    public RsMonitorLogic() {

        // システムメッセージ関連
        redstoneLogic = new RedstoneLogic();
    }

    @Override
    public boolean execute(PacketData data) {

        // 短いデータは無視する
        if (data.getRawData().length < 17) {
            return false;
        }

        if (redstoneLogic.execute(data)) {
            return true;
        }

        //debugLogic.execute(data);

        return false;
    }


    @Override
    public boolean isNoticeable() {
        return false;
    }

    @Override
    public String getOshiraseMessage() {
        return null;
    }

    public void loadConfig(Properties config) {
        // nop
    }

    public void saveConfig(Properties config) {
        // nop
    }

    public final void loadConfig() {
        // nop
    }
    public final void saveConfig() {
        // nop
    }

    /**
     * getter.
     *
     * @return redstoneLogic
     */
    public RedstoneLogic getRedstoneLogic() {
        return redstoneLogic;
    }

    /**
     * setter.
     *
     * @param redstoneLogic セットする redstoneLogic
     */
    public void setRedstoneLogic(RedstoneLogic redstoneLogic) {
        this.redstoneLogic = redstoneLogic;
    }
}
