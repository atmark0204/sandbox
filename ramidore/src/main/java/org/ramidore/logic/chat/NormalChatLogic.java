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
 * 通常チャットのパケットを処理する.
 *
 * @author atmark
 *
 */
public class NormalChatLogic extends AbstractChatLogic {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(NormalChatLogic.class);

    /**
     * パケット全体にマッチする正規表現パターン.
     */
    private static final String PATTERN = "^..002811CDCDCDCD..000000(?:.{2})+005811CCCCCCCC..C80CC0" + BASE_PATTERN + "00"
            + BASE_PATTERN + "000000(?:.{2})*$";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * コンストラクタ.
     */
    public NormalChatLogic() {
    }

    @Override
    public final boolean execute(final PacketData data) {

        Matcher matcher = pattern.matcher(data.getStrData());

        if (matcher.matches()) {

            String name = RamidoreUtil.encode(matcher.group(1), ENCODING);

            String content = RamidoreUtil.encode(matcher.group(2), ENCODING);

            addData(new ChatTable(data.getDate(), name, content));

            LOG.info("【" + name + "】 " + content);

            return true;
        }

        return false;
    }

    @Override
    public void loadConfig(Properties config) {

        boolean enabled = Boolean.parseBoolean(config.getProperty("normalChat.enabled", "false"));

        this.setEnabled(enabled);
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("normalChat.enabled", String.valueOf(isNoticeable()));
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
