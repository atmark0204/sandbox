package org.ramidore.logic.item;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TableView;

import org.apache.commons.lang3.StringUtils;
import org.ramidore.bean.ItemBean;
import org.ramidore.bean.ItemTable;
import org.ramidore.core.PacketData;
import org.ramidore.logic.IPacketExecutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * アイテム取得パケットを処理する.
 *
 * @author atmark
 *
 */
public class ItemLogic implements IPacketExecutable {

    /**
     * . Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ItemLogic.class);

    /**
     * 自動取得.
     *
     * ex.) RS欠片等
     */
    private static final String PATTERM_AUTO_PICKUP = "^..002811CDCDCDCD010000002200A2110000........(....)01000000(....)....(....)....(....)....3C0000000000$";

    /**
     * 手動取得.
     */
    private static final String PATTERN_MANUAL_PICKUP = "^(?:.{2})*2200381100000000........(....)........(....)....(....)....(....)....3C000000..(?:.{2})*$";

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternAutoPickup = Pattern.compile(PATTERM_AUTO_PICKUP);

    /**
     * . 正規表現オブジェクト
     */
    private static Pattern patternManualPickup = Pattern.compile(PATTERN_MANUAL_PICKUP);

    /**
     * item.datのデータ.
     *
     * TODO Singletonにしてもいい
     */
    private ItemDatHolder itemDat;

    /**
     * 表示用テーブル.
     */
    private TableView<ItemTable> itemTable;

    /**
     * . コンストラクタ
     */
    public ItemLogic() {

        itemDat = new ItemDatHolder();
    }

    @Override
    public boolean execute(PacketData data) {

        Map<String, ItemBean> itemMap = itemDat.getItemMap();

        Matcher mAutoPickup = patternAutoPickup.matcher(data.getStrData());

        if (mAutoPickup.matches()) {

            String id = mAutoPickup.group(1);

            String name = "未知のアイテム" + id;

            if (itemMap.containsKey(id)) {
                name = itemMap.get(id).getName();
            }

            String opName1 = getOptionName(mAutoPickup.group(2));
            String opName2 = getOptionName(mAutoPickup.group(3));
            String opName3 = getOptionName(mAutoPickup.group(4));

            String itemName = opName1 + opName2 + opName3 + name;

            itemTable.getItems().add(new ItemTable(data.getDate(), itemName));

            LOG.info("自動取得 : " + itemName);

            return true;
        }

        Matcher mManualPickup = patternManualPickup.matcher(data.getStrData());

        if (mManualPickup.matches()) {

            String id = mManualPickup.group(1);

            String name = "未知のアイテム : " + id;

            if (itemMap.containsKey(id)) {
                name = itemMap.get(id).getName();
            }

            String opName1 = getOptionName(mManualPickup.group(2));
            String opName2 = getOptionName(mManualPickup.group(3));
            String opName3 = getOptionName(mManualPickup.group(4));

            String itemName = opName1 + opName2 + opName3 + name;

            itemTable.getItems().add(new ItemTable(data.getDate(), itemName));

            LOG.info("拾得 : " + itemName);

            return true;
        }

        return false;
    }

    /**
     * オプション名を返す.
     *
     * @param id オプションID
     * @return オプション名
     */
    private String getOptionName(String id) {

        if (itemDat.getOptionMap().containsKey(id)) {

            return itemDat.getOptionMap().get(id).getName();
        }

        return StringUtils.EMPTY;
    }

    /**
     * getter.
     *
     * @return itemTable
     */
    public TableView<ItemTable> getItemTable() {
        return itemTable;
    }

    /**
     * setter.
     *
     * @param itemTable セットする itemTable
     */
    public void setItemTable(TableView<ItemTable> itemTable) {
        this.itemTable = itemTable;
    }

}
