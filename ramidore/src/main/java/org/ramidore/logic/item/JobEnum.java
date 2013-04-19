package org.ramidore.logic.item;

/**
 * 職業.
 *
 * @author atmark
 *
 */
public enum JobEnum {

    KENSHI(0, "剣士", "剣"),
    SENSHI(1, "戦士", "戦"),
    WIZ(2, "ウィザード", "魔"),
    WOLFMAN(3, "ウルフマン", "犬"),
    BIS(4, "ビショップ", "癒"),
    TENSHI(5, "追放天使", "天"),
    SIHU(6, "シーフ", "盗"),
    BUDOU(7, "武道家", "武"),
    RANSA(8, "ランサー", "槍"),
    ACYA(9, "アーチャー", "弓"),
    TEIMA(10, "ビーストテイマー", "獣"),
    SAMANA(11, "サマナー", "召"),
    PURI(12, "プリンセス", "姫"),
    RITORU(13, "リトルウィッチ", "魔女"),
    NEKURO(14, "ネクロマンサー", "屍"),
    AKUMA(15, "悪魔", "悪"),
    REIJUTSU(16, "霊術師", "霊"),
    TOUSHI(17, "闘士", "闘"),
    KOUSOUSHI(18, "光奏師", "光"),

    MALE(20, "男性キャラクター", "男"),
    FEMALE(21, "女性キャラクター", "女"),
    ALL(22, "全職業", "全");

    /**
     * 職業ID.
     */
    private int id;

    /**
     * 職業名.
     */
    private String name;

    /**
     * 職業略称.
     */
    private String shortName;

    /**
     * コンストラクタ.
     *
     * @param id id
     * @param name name
     * @param shortName shortName
     */
    private JobEnum(int id, String name, String shortName) {

        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public static String getShortName(int id) {

        for (JobEnum job : JobEnum.values()) {

            if (job.getId() == id) {

                return job.getShortName();
            }
        }

        return null;
    }

    /**
     * getter.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter.
     *
     * @param id セットする id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter.
     *
     * @param name セットする name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter.
     *
     * @return shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * setter.
     *
     * @param shortName セットする shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
