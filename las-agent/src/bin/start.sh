#!/bin/bash

################################
# constants
################################
LAS_AGENT_CLASS="org.easycloud.las.agent.AgentStartUp"

# Use JAVA_HOME if set, otherwise look for java in PATH
if [ -n "$JAVA_HOME" ]; then
JAVA="$JAVA_HOME/bin/java"
else
JAVA=java
fi

# Allow alternate conf dir location.
LAS_CONF_DIR="${HIVE_CONF_DIR:-../conf}"

export LAS_CONF_DIR=$LAS_CONF_DIR

CLASSPATH="${LAS_CONF_DIR}"

