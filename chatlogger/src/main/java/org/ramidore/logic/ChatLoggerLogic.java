package org.ramidore.logic;

import java.util.LinkedList;
import java.util.Properties;

import org.ramidore.core.PacketData;
import org.ramidore.logic.chat.GuildChatLogic;
import org.ramidore.logic.chat.MimiChatLogic;
import org.ramidore.logic.chat.NormalChatLogic;
import org.ramidore.logic.chat.PartyChatLogic;
import org.ramidore.logic.chat.SakebiChatLogic;
import org.ramidore.logic.item.ItemLogic;
import org.ramidore.logic.system.DameonMessageLogic;
import org.ramidore.logic.system.RedstoneLogic;

/**
 * . メインロジック
 *
 * @author atmark
 *
 */
public class ChatLoggerLogic extends AbstractMainLogic {

    /**
     * お知らせ表示の初期設定.
     */
    private boolean noticeable;

    /**
     * お知らせの表示行数.
     */
    private int oshiraseLineCount;

    /**
     * お知らせメッセージ.
     */
    private LinkedList<String> oshiraseMessages;

    /**
     * 叫び.
     */
    private SakebiChatLogic sakebiChatLogic;

    /**
     * 通常チャット.
     */
    private NormalChatLogic normalChatLogic;

    /**
     * PTチャット.
     */
    private PartyChatLogic partyChatLogic;

    /**
     * ギルドチャット.
     */
    private GuildChatLogic guildChatLogic;

    /**
     * 耳打ち.
     */
    private MimiChatLogic mimiChatLogic;

    /**
     * RS返却.
     */
    private RedstoneLogic redstoneLogic;

    /**
     * 運営メッセージ.
     */
    private DameonMessageLogic dameonMessageLogic;

    /**
     * アイテム取得.
     */
    private ItemLogic itemLogic;

    /**
     * コンストラクタ.
     */
    public ChatLoggerLogic() {

        // チャット関連
        sakebiChatLogic = new SakebiChatLogic();
        normalChatLogic = new NormalChatLogic();
        partyChatLogic = new PartyChatLogic();
        guildChatLogic = new GuildChatLogic();
        mimiChatLogic = new MimiChatLogic();

        // システムメッセージ関連
        redstoneLogic = new RedstoneLogic();
        dameonMessageLogic = new DameonMessageLogic();

        // アイテム関連
        itemLogic = new ItemLogic();
    }

    @Override
    public boolean execute(PacketData data) {

        // 短いデータは無視する
        if (data.getRawData().length < 17) {
            return false;
        }

        if (dameonMessageLogic.execute(data)) {
            // ギルチャより先に処理しないと引っかかる
            return true;
        }

        if (sakebiChatLogic.execute(data)) {

            oshiraseMessages.offer(sakebiChatLogic.getOshiraseMessage());

            return true;
        }

        if (normalChatLogic.execute(data)) {

            oshiraseMessages.offer(normalChatLogic.getOshiraseMessage());

            return true;
        }

        if (partyChatLogic.execute(data)) {

            oshiraseMessages.offer(partyChatLogic.getOshiraseMessage());

            return true;
        }

        if (guildChatLogic.execute(data)) {

            oshiraseMessages.offer(guildChatLogic.getOshiraseMessage());

            return true;
        }

        if (mimiChatLogic.execute(data)) {

            oshiraseMessages.offer(mimiChatLogic.getOshiraseMessage());

            return true;
        }

        if (redstoneLogic.execute(data)) {
            return true;
        }

        if (itemLogic.execute(data)) {
            return true;
        }

        return false;
    }


    @Override
    public void loadConfig(Properties config) {

        noticeable = Boolean.parseBoolean(config.getProperty("oshirase.enabled", "false"));
        oshiraseLineCount = Integer.parseInt(config.getProperty("oshirase.linecount", "1"));
        oshiraseMessages = new LinkedList<String>();

        sakebiChatLogic.loadConfig(config);
        normalChatLogic.loadConfig(config);
        partyChatLogic.loadConfig(config);
        guildChatLogic.loadConfig(config);
        mimiChatLogic.loadConfig(config);
    }

    @Override
    public void saveConfig(Properties config) {

        config.setProperty("oshirase.enabled", String.valueOf(noticeable));
        config.setProperty("oshirase.linecount", String.valueOf(oshiraseLineCount));

        sakebiChatLogic.saveConfig(config);
        normalChatLogic.saveConfig(config);
        partyChatLogic.saveConfig(config);
        guildChatLogic.saveConfig(config);
        mimiChatLogic.saveConfig(config);
    }

    @Override
    public final void loadConfig() {
        // nop
    }
    @Override
    public final void saveConfig() {
        // nop
    }

    @Override
    public String getOshiraseMessage() {

        while (oshiraseMessages.size() > oshiraseLineCount) {

            oshiraseMessages.remove();
        }

        StringBuilder builder = new StringBuilder("<html>");

        for(int i = 0;i < oshiraseMessages.size(); i++) {

            String line = oshiraseMessages.get(i);

            if (i < oshiraseMessages.size() - 1) {
                builder.append(line + "<br />");
            } else {
                builder.append(line + "</html>");
            }
        }

        return builder.toString();
    }

    @Override
    public boolean isNoticeable() {
        return noticeable;
    }

    /**
     * . accessor
     *
     * @return sakebiChatLogic
     */
    public SakebiChatLogic getSakebiChatLogic() {
        return sakebiChatLogic;
    }

    /**
     * . accessor
     *
     * @param sakebiChatLogic
     *            セットする sakebiChatLogic
     */
    public void setSakebiChatLogic(SakebiChatLogic sakebiChatLogic) {
        this.sakebiChatLogic = sakebiChatLogic;
    }

    /**
     * . accessor
     *
     * @return normalChatLogic
     */
    public NormalChatLogic getNormalChatLogic() {
        return normalChatLogic;
    }

    /**
     * . accessor
     *
     * @param normalChatLogic
     *            セットする normalChatLogic
     */
    public void setNormalChatLogic(NormalChatLogic normalChatLogic) {
        this.normalChatLogic = normalChatLogic;
    }

    /**
     * . accessor
     *
     * @return partyChatLogic
     */
    public PartyChatLogic getPartyChatLogic() {
        return partyChatLogic;
    }

    /**
     * . accessor
     *
     * @param partyChatLogic
     *            セットする partyChatLogic
     */
    public void setPartyChatLogic(PartyChatLogic partyChatLogic) {
        this.partyChatLogic = partyChatLogic;
    }

    /**
     * . accessor
     *
     * @return guildChatLogic
     */
    public GuildChatLogic getGuildChatLogic() {
        return guildChatLogic;
    }

    /**
     * . accessor
     *
     * @param guildChatLogic
     *            セットする guildChatLogic
     */
    public void setGuildChatLogic(GuildChatLogic guildChatLogic) {
        this.guildChatLogic = guildChatLogic;
    }

    /**
     * . accessor
     *
     * @return mimiChatLogic
     */
    public MimiChatLogic getMimiChatLogic() {
        return mimiChatLogic;
    }

    /**
     * . accessor
     *
     * @param mimiChatLogic
     *            セットする mimiChatLogic
     */
    public void setMimiChatLogic(MimiChatLogic mimiChatLogic) {
        this.mimiChatLogic = mimiChatLogic;
    }

    /**
     * . accessor
     *
     * @return redstoneLogic
     */
    public RedstoneLogic getRedstoneLogic() {
        return redstoneLogic;
    }

    /**
     * . accessor
     *
     * @param redstoneLogic
     *            セットする redstoneLogic
     */
    public void setRedstoneLogic(RedstoneLogic redstoneLogic) {
        this.redstoneLogic = redstoneLogic;
    }

    /**
     * . accessor
     *
     * @return itemLogic
     */
    public ItemLogic getItemLogic() {
        return itemLogic;
    }

    /**
     * . accessor
     *
     * @param itemLogic
     *            セットする itemLogic
     */
    public void setItemLogic(ItemLogic itemLogic) {
        this.itemLogic = itemLogic;
    }

    /**
     * setter.
     *
     * @param noticeable セットする noticeable
     */
    public void setNoticeable(boolean noticeable) {
        this.noticeable = noticeable;
    }

    /**
     * getter.
     *
     * @return oshiraseLineCount
     */
    public int getOshiraseLineCount() {
        return oshiraseLineCount;
    }

    /**
     * setter.
     *
     * @param oshiraseLineCount セットする oshiraseLineCount
     */
    public void setOshiraseLineCount(int oshiraseLineCount) {
        this.oshiraseLineCount = oshiraseLineCount;
    }
}
