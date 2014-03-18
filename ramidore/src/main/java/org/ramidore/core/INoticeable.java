package org.ramidore.core;

/**
 * お知らせを提供するインターフェース.
 *
 * @author atmark
 *
 */
public interface INoticeable {

    /**
     * お知らせ可能か.
     *
     * @return 可/否
     */
    boolean isNoticeable();

    /**
     * お知らせメッセージを返す.
     *
     * @return お知らせメッセージ
     */
    String getOshiraseMessage();
}
