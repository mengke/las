package org.easycloud.las.agent.cfg;

import org.easycloud.las.core.cfg.Configuration;
import org.easycloud.las.core.util.las.agent.Constants;

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
