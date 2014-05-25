package org.ramidore.bean;

import javafx.scene.chart.XYChart.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * ポイント戦のログ.
 *
 * @author atmark
 *
 */
@Getter
@Setter
public class PbLogBean {

    /**
     * 識別子.
     */
    private String id;

    /**
     * シーケンス番号.
     */
    private int sequentialNo;

    /**
     * 面番号.
     */
    private int stageNo;

    /**
     * ステージごとのシーケンス番号.
     */
    private int stageSequentialNo;

    /**
     * 点数.
     */
    private int point;

    /**
     * 前ステージのポイント.
     */
    private int pointOffset = 0;

    /**
     * コンストラクタ.
     *
     * @param id
     *            識別子
     * @param sequentialNo
     *            シーケンス番号
     * @param stageNo
     *            ステージ番号
     * @param stageSequentialNo
     *            ステージシーケンス番号
     * @param point
     *            点数
     * @param pointOffset
     *            前面の最終点数
     */
    public PbLogBean(String id, int sequentialNo, int stageNo, int stageSequentialNo, int point, int pointOffset) {

        this.id = id;
        this.sequentialNo = sequentialNo;
        this.stageNo = stageNo;
        this.stageSequentialNo = stageSequentialNo;
        this.point = point;
        this.pointOffset = pointOffset;
    }

    /**
     * コンストラクタ(pointOffset無し).
     *
     * @param id
     *            識別子
     * @param sequentialNo
     *            シーケンス番号
     * @param stageNo
     *            ステージ番号
     * @param stageSequentialNo
     *            ステージシーケンス番号
     * @param point
     *            点数
     */
    public PbLogBean(String id, int sequentialNo, int stageNo, int stageSequentialNo, int point) {

        this.id = id;
        this.sequentialNo = sequentialNo;
        this.stageNo = stageNo;
        this.stageSequentialNo = stageSequentialNo;
        this.point = point;
    }

    /**
     * ステージ中のチャート表示用データを返す.
     *
     * @return チャート表示用データ
     */
    public Data<Number, Number> toStageData() {

        return new Data<Number, Number>(stageSequentialNo, point - pointOffset);
    }

    /**
     * 全体のチャート表示用データを返す.
     *
     * @return チャート表示用データ
     */
    public Data<Number, Number> toData() {

        return new Data<Number, Number>(sequentialNo, point);
    }
}
