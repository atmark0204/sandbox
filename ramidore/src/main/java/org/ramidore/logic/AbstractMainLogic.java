package org.ramidore.logic;

import org.ramidore.core.IConfigurable;
import org.ramidore.core.INoticeable;

/**
 * 基底のファサードロジック.
 * 小さなロジッククラスのインスタンスを保持し、まとめて実行する.
 *
 * @author atmark
 *
 */
public abstract class AbstractMainLogic implements IPacketExecutable, INoticeable, IConfigurable {

    /**
     * タスク開始にフックする.
     */
    public void hookOnTaskStart() {
    }
}
