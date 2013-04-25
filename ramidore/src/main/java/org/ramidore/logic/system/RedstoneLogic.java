package org.ramidore.logic.system;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ramidore.bean.RedStoneChartBean;
import org.ramidore.core.PacketData;
import org.ramidore.util.RamidoreUtil;

/**
 * Redstone返却先.
 *
 * @author atmark
 *
 */
public class RedstoneLogic extends AbstractSystemMessageLogic {

    /**
     * フラグ　天上.
     */
    private static final String FLAG_TENJO = "00";

    /**
     * フラグ　地下.
     */
    private static final String FLAG_CHIKA = "01";

    /**
     * フラグ　悪魔.
     */
    private static final String FLAG_AKUMA = "02";

    /**
     * . REDSTONE返却時のシステムメッセージのパターン
     */
    public static final String PATTERN = "^2C002811CDCDCDCD0100000020005D120000BB00(00|01|02)000000(......)00(......)00(......)000100000000000000$";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * JavaFXスレッドと同期するためのLinkedQueue.
     */
    private ConcurrentLinkedQueue<RedStoneChartBean> chartDataQ = new ConcurrentLinkedQueue<RedStoneChartBean>();

    /**
     * チャート用データ.
     */
    private RedStoneChartBean dataBean;

    /**
     * コンストラクタ.
     */
    public RedstoneLogic() {

        dataBean = new RedStoneChartBean();
    }

    @Override
    public boolean execute(final PacketData data) {

        Matcher m = pattern.matcher(data.getStrData());

        if (m.matches()) {

            String flag = m.group(1);

            String tenjoCnt = m.group(2);
            String chikaCnt = m.group(3);
            String akumaCnt = m.group(4);

            dataBean.setCurrentTenjoCount(RamidoreUtil.intValueFromDescHexString(tenjoCnt));
            dataBean.setCurrentChikaCount(RamidoreUtil.intValueFromDescHexString(chikaCnt));
            dataBean.setCurrentAkumaCount(RamidoreUtil.intValueFromDescHexString(akumaCnt));

            dataBean.setCurrentTotalCount();

            if (FLAG_TENJO.equals(flag)) {
                dataBean.addTenjo();
            }
            if (FLAG_CHIKA.equals(flag)) {
                dataBean.addChika();
            }
            if (FLAG_AKUMA.equals(flag)) {
                dataBean.addAkuma();
            }
            dataBean.setLocalTotalCount();

            chartDataQ.add(dataBean);

            return true;
        }

        return false;
    }

    /**
     * getter.
     *
     * @return chartDataQ
     */
    public ConcurrentLinkedQueue<RedStoneChartBean> getChartDataQ() {
        return chartDataQ;
    }

    /**
     * setter.
     *
     * @param chartDataQ
     *            セットする chartDataQ
     */
    public void setChartDataQ(ConcurrentLinkedQueue<RedStoneChartBean> chartDataQ) {
        this.chartDataQ = chartDataQ;
    }

}
