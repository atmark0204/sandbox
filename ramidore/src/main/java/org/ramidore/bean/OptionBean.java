package org.ramidore.bean;

/**
 * オプション情報.
 *
 * @author atmark
 *
 */
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

    /**
     * getter.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * setter.
     *
     * @param id セットする id
     */
    public void setId(String id) {
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
     * @return buf
     */
    public String getBuf() {
        return buf;
    }

    /**
     * setter.
     *
     * @param buf セットする buf
     */
    public void setBuf(String buf) {
        this.buf = buf;
    }
}
