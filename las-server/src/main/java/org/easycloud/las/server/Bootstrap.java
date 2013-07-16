package org.easycloud.las.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-9
 * Time: 下午5:14
 */
public class Bootstrap {

    public static final String DEFAULT_SPRING_CONFIG = "classpath*:spring/*.xml";
    private static final String SERVER_FACTORY_NAME = "bootServer";

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(DEFAULT_SPRING_CONFIG);
        LasServer lasServer = applicationContext.getBean(SERVER_FACTORY_NAME, LasServer.class);
        lasServer.start();
    }
}
