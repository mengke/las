#!/bin/bash

################################
# constants
################################
LAS_AGENT_CLASS="org.easycloud.las.agent.AgentStartUp"

# Check if JAVA_HOME is set
# Use JAVA_HOME if set, otherwise look for java in PATH
if [ -n "$JAVA_HOME" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
  echo "Error: JAVA_HOME is not set."
  exit 1
fi

JAVA_HEAP_MAX=-Xmx1000m

THIS_DIR=`dirname "$THIS"`
LAS_AGENT_HOME=`cd "$THIS_DIR/.." ; pwd`

# Allow alternate conf dir location.
LAS_AGENT_CONF_DIR="${LAS_AGENT_CONF_DIR:-../conf}"

export LAS_AGENT_CONF_DIR=$LAS_AGENT_CONF_DIR

CLASSPATH="${LAS_AGENT_CONF_DIR}"
CLASSPATH=.:${CLASSPATH}:$JAVA_HOME/lib/tools.jar

LAS_AGENT_LIB=../lib

for f in ${LAS_AGENT_LIB}/*.jar; do
CLASSPATH=${CLASSPATH}:$f;
done

EXEC_CALL="$JAVA $JAVA_HEAP_MAX -classpath $CLASSPATH"

exec $EXEC_CALL $LAS_AGENT_CLASS "$@"