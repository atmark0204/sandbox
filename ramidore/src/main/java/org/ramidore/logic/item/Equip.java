package org.ramidore.logic.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 装備可能職業.
 *
 * @author atmark
 *
 */
public class Equip {

    private static final String PATTERN = "(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)(..)";

    private static final Pattern pattern = Pattern.compile(PATTERN);

    /**
     * 装備可能職業.
     */
    private String euipId;

    /**
     * コンストラクタ.
     *
     * @param equipId equipId
     */
    public Equip(String equipId) {

        this.euipId = equipId;
    }

    public String toString() {

        Matcher m = pattern.matcher(euipId);

        if (m.matches()) {

            if ("01".equals(m.group(17)) && "00".equals(m.group(18))) {

                return JobEnum.MALE.getShortName();
            }

            if ("00".equals(m.group(17)) && "01".equals(m.group(18))) {

                return JobEnum.FEMALE.getShortName();
            }

            StringBuilder builder = new StringBuilder();

            for (int i = 1; i < 17; i++) {

                if ("01".equals(m.group(i))) {
                    builder.append(JobEnum.getShortName(i - 1) + " ");
                }
            }

            return builder.toString();
        }

        return StringUtils.EMPTY;
    }
}
