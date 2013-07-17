package org.easycloud.las.server;

import org.apache.thrift.TProcessor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easycloud.las.core.util.Assert.assertStateNotEmpty;
import static org.easycloud.las.core.util.Assert.assertStateNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-10
 * Time: 上午10:12
 */
public class LasServerFactory implements FactoryBean<LasServer>, InitializingBean {

    private List<LasServer> lasServers;
    private Map<String, TProcessor> processors;
    private Map<Class<? extends LasServer>, LasServer> lasServerMap;
    private Class bootServerClass;

    public void setProcessors(Map<String, TProcessor> processors) {
        this.processors = processors;
    }

    public void setLasServers(List<LasServer> lasServers) {
        this.lasServers = lasServers;
    }

    public void setBootServerClass(Class bootServerClass) {
        this.bootServerClass = bootServerClass;
    }

    @Override
    public LasServer getObject() throws Exception {
        LasServer bootServer = lasServerMap.get(bootServerClass);
        assertStateNotNull(bootServer,
                "LasServerFactory getObject bootServer not found. Please check your bootServerClass config.");
        bootServer.setProcessors(processors);
        return bootServer;
    }

    @Override
    public Class<?> getObjectType() {
        return bootServerClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        assertStateNotNull(bootServerClass, "LasServerFactory bootServerClass must be set!");
        assertStateNotEmpty(processors, "LasServerFactory processors must be set!");
        assertStateNotEmpty(lasServers, "LasServerFactory lasServers must be set!");
        lasServerMap = new HashMap<Class<? extends LasServer>, LasServer>();
        for (LasServer server : lasServers) {
            lasServerMap.put(server.getClass(), server);
        }
    }
}