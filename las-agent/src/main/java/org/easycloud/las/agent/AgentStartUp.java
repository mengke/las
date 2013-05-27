package org.easycloud.las.agent;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-13
 */
public class AgentStartUp {

	public static void main(String[] args) {
		LoggingAgent agent = LoggingAgent.getInstance();
		agent.startUp();
	}
}
