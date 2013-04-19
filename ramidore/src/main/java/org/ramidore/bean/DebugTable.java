package org.ramidore.bean;

/**
 * . デバッグ用データモデル
 *
 * @author atmark
 *
 */
public class DebugTable {

    /**
     * . パケットのフレーム番号
     */
    private String frameNo;

    /**
     * . ダンプ
     */
    private String dump;

    /**
     * . コンストラクタ
     *
     * @param frameNo
     *            フレーム番号
     * @param dump
     *            ダンプ
     */
    public DebugTable(final String frameNo, final String dump) {

        this.frameNo = frameNo;
        this.dump = dump;
    }

    /**
     * . accessor
     *
     * @return frameNo
     */
    public String getFrameNo() {
        return frameNo;
    }

    /**
     * . accessor
     *
     * @param frameNo
     *            セットする frameNo
     */
    public void setFrameNo(final String frameNo) {
        this.frameNo = frameNo;
    }

    /**
     * . accessor
     *
     * @return dump
     */
    public String getDump() {
        return dump;
    }

    /**
     * . accessor
     *
     * @param dump
     *            セットする dump
     */
    public void setDump(final String dump) {
        this.dump = dump;
    }

}
