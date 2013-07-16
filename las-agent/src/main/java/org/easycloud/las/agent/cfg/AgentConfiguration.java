/*
 * Copyright 2013 Ke Meng (mengke@icloud.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.easycloud.las.agent.cfg;

import org.easycloud.las.agent.Constants;
import org.easycloud.las.core.cfg.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class AgentConfiguration extends Configuration {

    private static AgentConfiguration instance = new AgentConfiguration();

    private AgentConfiguration() {
        super(Constants.LOG_AGENT_PROPS);
    }

    public static AgentConfiguration getInstance() {
        return instance;
    }

}
