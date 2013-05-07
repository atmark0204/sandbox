package org.ramidore.logic.chat;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ramidore.bean.ChatTable;
import org.ramidore.core.PacketData;
import org.ramidore.util.RamidoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * . 叫びのパケットを処理する
 *
 * @author atmark
 *
 */
public class SakebiChatLogic extends AbstractChatLogic {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(SakebiChatLogic.class);

    /**
     * . パケット全体にマッチする正規表現パターン
     *
     */
    private static final String PATTERN = "^....2811CDCDCDCD..000000(?:.{2})+005811CCCCCCCC....0CC1" + BASE_PATTERN + "00"
            + BASE_PATTERN + "(000000(?:.{2})*)?$";

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * . コンストラクタ
     */
    public SakebiChatLogic() {
    }

    @Override
    public boolean execute(final PacketData data) {

        Matcher m = pattern.matcher(data.getStrData());

        if (m.matches()) {

            String name = RamidoreUtil.encode(m.group(1), ENCODING);

            String content = RamidoreUtil.encode(m.group(2), ENCODING);

            addData(new ChatTable(data.getDate(), name, content));

            LOG.info("【" + name + "】 " + content);

            return true;
        }

        return false;
    }

    @Override
    public void loadConfig(Properties config) {

        boolean enabled = Boolean.parseBoolean(config.getProperty("sakebiChat.enabled", "false"));

        this.setEnabled(enabled);
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("sakebiChat.enabled", String.valueOf(isNoticeable()));
    }

    @Override
    public final void loadConfig() {
        // nop
    }

    @Override
    public final void saveConfig() {
        // nop
    }
}
