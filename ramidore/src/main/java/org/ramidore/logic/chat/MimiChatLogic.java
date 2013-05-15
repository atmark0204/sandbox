package org.ramidore.logic.chat;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ramidore.Const;
import org.ramidore.bean.ChatTable;
import org.ramidore.core.PacketData;
import org.ramidore.util.RamidoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 耳打ちのパケットを処理する.
 *
 * @author atmark
 *
 */
public class MimiChatLogic extends AbstractChatLogic {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(MimiChatLogic.class);

    /**
     * TOのプレフィックス.
     */
    private static final String PREFIX_TO = "(?:.{2}+)00761100000000";

    /**
     * FROMのプレフィックス.
     */
    private static final String PREFIX_FROM = "(?:.{2}+)005811CCCCCCCC....4CC0";

    /**
     * . 耳をするパターン
     */
    private static final String PATTERN_TO = "^..002811CDCDCDCD..000000" + PREFIX_TO + Const.BASE_PATTERN + "(?:00+)"
            + Const.BASE_PATTERN + "000000(?:.{2})*$";

    /**
     * . 耳が来るパターン
     */
    private static final String PATTERN_FROM = "^..002811CDCDCDCD..000000" + PREFIX_FROM + Const.BASE_PATTERN + "00"
            + Const.BASE_PATTERN + "000000(?:.{2})*$";

    /**
     * From, Toが1パケットにまとまって来る場合のパターン.
     * 自分自身に耳をする場合のみ？
     */
    private static final String PATTERN_FROM_TO = "^..002811CDCDCDCD..000000" + PREFIX_TO + Const.BASE_PATTERN
            + "0000000000000000" + Const.BASE_PATTERN + "0{2}+" + PREFIX_FROM + Const.BASE_PATTERN + "00"
            + Const.BASE_PATTERN + "000000(?:.{2})*$";

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternTo = Pattern.compile(PATTERN_TO);

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternFromTo = Pattern.compile(PATTERN_FROM_TO);

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternFrom = Pattern.compile(PATTERN_FROM);

    /**
     * プレフィックス.
     */
    private static final String TO = "to ";

    /**
     * プレフィックス.
     */
    private static final String FROM = "from ";

    /**
     * コンストラクタ.
     */
    public MimiChatLogic() {

        LOG.debug("FROM  :" + PATTERN_FROM);
        LOG.debug("TO    :" + PATTERN_TO);
        LOG.debug("FROMTO:" + PATTERN_FROM_TO);
    }

    @Override
    public boolean execute(final PacketData data) {

        // 先にFromToから処理しないと誤マッチする

        Matcher mFromTo = patternFromTo.matcher(data.getStrData());

        if (mFromTo.matches()) {

            String toName = RamidoreUtil.encode(mFromTo.group(1), Const.ENCODING);
            String toContent = RamidoreUtil.encode(mFromTo.group(2), Const.ENCODING);

            addData(new ChatTable(data.getDate(), TO, toName, toContent));

            LOG.info(TO + "【" + toName + "】 " + toContent);

            String fromName = RamidoreUtil.encode(mFromTo.group(3), Const.ENCODING);
            String fromContent = RamidoreUtil.encode(mFromTo.group(4), Const.ENCODING);

            addData(new ChatTable(data.getDate(), FROM, fromName, fromContent));

            LOG.info(FROM + "【" + fromName + "】 " + fromContent);

            return true;
        }

        Matcher mTo = patternTo.matcher(data.getStrData());

        if (mTo.matches()) {

            String name = RamidoreUtil.encode(mTo.group(1), Const.ENCODING);

            String content = RamidoreUtil.encode(mTo.group(2), Const.ENCODING);

            addData(new ChatTable(data.getDate(), TO, name, content));

            LOG.info(TO + "【" + name + "】 " + content);

            return true;
        }

        Matcher mFrom = patternFrom.matcher(data.getStrData());

        if (mFrom.matches()) {

            String name = RamidoreUtil.encode(mFrom.group(1), Const.ENCODING);

            String content = RamidoreUtil.encode(mFrom.group(2), Const.ENCODING);

            addData(new ChatTable(data.getDate(), FROM, name, content));

            LOG.info(FROM + "【" + name + "】 " + content);

            return true;
        }

        return false;
    }

    @Override
    public void loadConfig(Properties config) {

        boolean enabled = Boolean.parseBoolean(config.getProperty("mimiChat.enabled", "false"));

        this.setEnabled(enabled);
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("mimiChat.enabled", String.valueOf(isNoticeable()));
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
