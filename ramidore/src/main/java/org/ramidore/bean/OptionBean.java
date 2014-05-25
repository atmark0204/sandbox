package org.ramidore.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * オプション情報.
 *
 * @author atmark
 *
 */
@Getter
@Setter
public class OptionBean {

    /**
     * オプションID.
     *
     * size : 2byte
     */
    private String id;

    /**
     * オプション名.
     */
    private String name;

    // TODO item.datの全情報が解析出来れば追加

    /**
     * 残り.
     */
    private String buf;
}
