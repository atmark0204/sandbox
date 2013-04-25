package org.ramidore.util;

import java.util.regex.Matcher;

import org.apache.commons.lang3.ArrayUtils;
import org.ramidore.core.PacketData;
import org.ramidore.logic.AbstractLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * デバッグ用ユーティリティクラス.
 *
 * @author atmark
 *
 */
public final class DebugUtil {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(DebugUtil.class);

    /**
     * コンストラクタ.
     */
    private DebugUtil() {
    }

    /**
     * マッチ後の文字列をデバッグ出力する.
     *
     * @param m
     *            Matcher
     */
    public static void debugMatcher(Matcher m) {

        for (int i = 0; i < m.groupCount(); i++) {
            log.debug(i + ":" + m.group(i));
        }
    }

    /**
     * パケットデータを16進ダンプする.
     *
     * @param data
     *            パケットデータ
     * @return 文字列
     */
    public static String hexDump(PacketData data) {

        StringBuilder resultBuilder = new StringBuilder();

        String header1 = "           00 01 02 03 04 05 06 07-08 09 0A 0B 0C 0D 0E 0F   0123456789ABCDEF\n";
        resultBuilder.append(header1);

        String header2 = "         +-------------------------------------------------+-----------------+\n";
        resultBuilder.append(header2);

        byte[] b = data.getRawData();

        int lineCount = (int) (b.length / 16) + 1;

        int index = 0;

        for (int i = 0; i < lineCount; i++) {

            StringBuilder lineBuilder = new StringBuilder();

            int addressNum = i * 10;

            lineBuilder.append(String.format("%08d", addressNum) + " | ");

            int indexS = index;

            for (int j = 0; j < 16; j++) {

                lineBuilder.append((index < b.length) ? String.format("%02X", b[index]) : "  ");
                lineBuilder.append(" ");

                index++;
            }

            int indexE = (index < b.length) ? index : b.length;

            lineBuilder.append("| ");

            byte[] line = ArrayUtils.subarray(b, indexS, indexE);

            lineBuilder.append(RamidoreUtil.encode(RamidoreUtil.toHex(line), AbstractLogic.ENCODING));

            resultBuilder.append(lineBuilder.toString() + "\n");
        }

        resultBuilder.append("\n\n");

        return resultBuilder.toString();
    }
}
