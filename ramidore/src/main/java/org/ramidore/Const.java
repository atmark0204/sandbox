package org.ramidore;

/**
 * 定数.
 *
 * @author atmark
 *
 */
public final class Const {

    /**
     * 0x00以外の16進文字列にマッチする正規表現パターン.
     */
    public static final String BASE_PATTERN = "((?:[1-9A-F]{2}|[1-9A-F]0|0[1-9A-F])+)";

    /**
     * . 日本語エンコードはWindows-31J
     *
     * ※ SJISだと機種依存文字が化ける
     */
    public static final String ENCODING = "Windows-31J";

    /**
     * プライベートコンストラクタ.
     */
    private Const() {
    }
}
