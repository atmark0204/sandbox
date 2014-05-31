/*
 * Copyright 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ramidore.logic.system;

import org.ramidore.Const;
import org.ramidore.core.PacketData;
import org.ramidore.util.RamidoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ダメオンの宣伝.
 *
 * @author atmark
 */
public class DameonMessageLogic extends AbstractSystemMessageLogic {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DameonMessageLogic.class);

    /**
     * パケット全体にマッチする正規表現パターン.
     */
    private static final String PATTERN = "^..005811CCCCCCCCCCCC4CC1A2C4B0FCB8AEC0DAA2C5" + "00" + Const.BASE_PATTERN + "000000(.{2})*$";

    /**
     * 正規表現オブジェクト.
     */
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * コンストラクタ.
     */
    public DameonMessageLogic() {
    }

    @Override
    public boolean execute(final PacketData data) {

        Matcher matcher = pattern.matcher(data.getStrData());

        if (matcher.matches()) {

            String content = RamidoreUtil.encode(matcher.group(1), Const.ENCODING);

            LOG.info(content);

            return true;
        }

        return false;
    }
}
