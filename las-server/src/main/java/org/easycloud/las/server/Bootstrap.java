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
 */

package org.easycloud.las.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-9
 * Time: 下午5:14
 */
public class Bootstrap {

    private static Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    public static final String DEFAULT_SPRING_CONFIG = "classpath*:spring/*.xml";
    private static final String SERVER_FACTORY_NAME = "bootServer";

    public static void main(String[] args) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Prepare for collecting data which is required by Bootstrap");
        }

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(DEFAULT_SPRING_CONFIG);
        LasServer lasServer = applicationContext.getBean(SERVER_FACTORY_NAME, LasServer.class);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("The server is ready to start, and server type is [" + lasServer.getServerType() + "]");
        }
        lasServer.start();
    }
}
