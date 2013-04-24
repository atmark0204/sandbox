package org.ramidore.logic;

import org.ramidore.core.IConfigurable;
import org.ramidore.core.INoticeable;

/**
 * 基底のファサードロジック.
 *
 * @author atmark
 *
 */
public abstract class AbstractRedomiraLogic implements IPacketExecutable, INoticeable, IConfigurable {

    /**
     * タスク開始にフックする.
     */
    public void hookOnTaskStart() {
    }
}
