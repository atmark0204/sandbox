package org.ramidore.logic.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.collections.ObservableList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ramidore.bean.GvLogTable;
import org.ramidore.bean.GvStatTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GVの得点状況を解析する.
 *
 * @author atmark
 *
 */
public class GuildBattleLogLogic {

    private static final String ENCODING = "Windows-31J";

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GuildBattleLogLogic.class);

    private ConcurrentLinkedQueue<GvLogTable> logDataQ;

    public GuildBattleLogLogic() {

        logDataQ = new ConcurrentLinkedQueue<GvLogTable>();
    }

    public GuildBattleLogLogic(String absolutePath) {

        this();

        loadPastData(absolutePath);
    }

    /**
     * 過去ログを読み込む.
     *
     * @param absolutePath
     */
    public void loadPastData(String absolutePath) {

        LOG.trace("load " + absolutePath);

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath), ENCODING));

            List<String> list = IOUtils.readLines(br);

            // 0 vs 0のデータ追加
            GvLogTable log0 = new GvLogTable();
            log0.setDate(list.get(0));

            logDataQ.add(log0);

            for (int i = 1; i < list.size(); i++) {
                GvLogTable logRow = loadLine(list.get(i));

                if (logRow == null) {
                    continue;
                }

                logDataQ.add(logRow);
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        } finally {
            IOUtils.closeQuietly(br);
        }

    }

    /**
     * ログ1行を読み込む
     *
     * @param line 文字列
     * @return GvLogTable
     */
    private GvLogTable loadLine(String line) {

        String[] elem = StringUtils.split(line, '\t');

        if (elem.length != 7) {

            return null;
        }

        GvLogTable row = new GvLogTable();

        row.setDate(elem[0]);
        row.setSrcCharaName(elem[1]);
        row.setDstCharaName(elem[2]);
        row.setGuildName(Integer.valueOf(elem[3]));
        row.setPoint(Integer.valueOf(elem[4]));
        row.setPoint0(Integer.valueOf(elem[5]));
        row.setPoint1(Integer.valueOf(elem[6]));

        return row;
    }

    /**
     * 統計情報をテキストに保存する.
     *
     * @param items
     */
    public static void saveStatData(ObservableList<GvStatTable> items, File f) {

        List<String> lines = new ArrayList<>();

        lines.add("name\tkill\tdeath\tpoint\tnote");

        for (GvStatTable item : items) {
            List<String> itemList = new ArrayList<String>();

            itemList.add(item.getCharaName());
            itemList.add(String.valueOf(item.getKillCount()));
            itemList.add(String.valueOf(item.getDeathCount()));
            itemList.add(String.valueOf(item.getPoint()));
            itemList.add(item.getNote());

            String line = StringUtils.join(itemList, '\t');

            lines.add(line);
        }

        try {
            FileUtils.writeLines(f, ENCODING, lines);
        } catch (IOException e) {

            LOG.error("I/O error " + f.getAbsolutePath());
        }
    }

    /**
     * getter.
     *
     * @return logDataQ
     */
    public ConcurrentLinkedQueue<GvLogTable> getLogDataQ() {
        return logDataQ;
    }
}
