package org.ramidore.logic.item;

/**
 * アイテムグループ.
 *
 * @author atmark
 *
 */
public enum ItemGroupEnum {

    KABUTO_BOUSHI("00", "兜・帽子"),
    KANMURI("01", "冠"),
    GUROBU("02", "グローブ"),
    YARITOUTEKI("03", "槍投擲機"),
    TSUME("04", "爪"),
    TEKUBI("05", "手首"),
    KOSHI("06", "腰"),
    ASHI("07", "足"),
    KUBI("08", "首"),
    YUBIWA("09", "指輪"),
    IYARINGU("0A", "イヤリング"),
    MANTO("0B", "マント"),
    BUROCHI("0C", "ブローチ"),
    UDEIREZUMI("0D", "腕刺青"),
    KATAIREZUMI("0E", "肩刺青"),
    JUJIKA("0F", "十字架"),
    YOROI("10", "鎧"),
    SYOKUGYOYOROI("11", "職業鎧"),
    KATATEKEN("12", "片手剣"),
    TATE("13", "盾"),
    RYOTEKEN("14", "両手剣"),
    TSUE("15", "杖"),
    KIBA("16", "牙"),
    DONKI("17", "鈍器"),
    TSUBASA("18", "翼"),
    TOUTEKI("19", "投擲"),
    YUMI("1A", "弓"),
    YA("1B", "矢"),
    YARI("1C", "槍"),
    HUE("1D", "笛"),
    SURINGU("1E", "スリング"),
    TAMA("1F", "弾"),
    SUTEKKI("20", "ステッキ"),
    MUCHI("21", "鞭"),
    HOUSEKI("22", "宝石"),
    HPKAIHUKU("23", "HP回復"),
    CPKAIHUKU("24", "CP回復"),
    NOURYOKUKOUJO_1("25", "能力向上1"),
    NOURYOKUKOUJO_2("26", "能力向上2"),
    JOUTAIIJOKAIHUKU_1("27", "状態異常回復1"),
    JOUTAIIJOKAIHUKU_2("28", "状態異常回復2"),
    KAGI("29", "鍵"),
    KIKAN("2A", "帰還"),
    TOKUSYU_1("2B", "特殊"),
    IBENT("2C", "イベント"),
    STJOSYO("2D", "ステータス上昇"),
    MARYOKUHOJU("2E", "魔力補充"),
    SETTINGHOUSEKI("2F", "セッティング宝石"),
    IBENTO_HUKUKEN("30", "イベント・福券"),
    QUEST("31", "クエスト"),
    YURYOITEM("32", "有料アイテム"),
    ENCYA_ROTEN("33", "エンチャント・露店"),
    ITEMBOX("34", "アイテムボックス");

    /**
     * アイテムグループID.
     */
    private String id;

    /**
     * アイテムグループ名.
     */
    private String name;

    /**
     * コンストラクタ.
     *
     * @param id グループID
     * @param name グループ名
     */
    ItemGroupEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * グループIDから名前を返す.
     *
     * @param id グループID
     * @return グループ名.
     */
    public static String getName(String id) {

        for (ItemGroupEnum ig : ItemGroupEnum.values()) {

            if (id.equals(ig.id)) {

                return ig.name;
            }
        }

        return "分類不明";
    }

}
