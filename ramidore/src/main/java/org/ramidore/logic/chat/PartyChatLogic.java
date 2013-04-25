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
 * PTチャットのパケットを処理する.
 *
 * @author atmark
 *
 */
public class PartyChatLogic extends AbstractChatLogic {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(PartyChatLogic.class);

    /**
     * PTチャットのパターン.
     */
    private static final String PATTERN = "^..002811CDCDCDCD..000000(?:.{2})+005811CCCCCCCCCC....C0" + BASE_PATTERN + "00"
            + BASE_PATTERN + "000000(?:.{2})*$";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean execute(final PacketData data) {

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

        boolean enabled = Boolean.parseBoolean(config.getProperty("partyChat.enabled", "false"));

        this.setEnabled(enabled);
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("partyChat.enabled", String.valueOf(isEnabled()));
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
