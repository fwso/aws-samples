#!/bin/bash

. ../aws-config.sh

logpath=/tmp/log4j/workflow

jvmargs="-Dlog=$logpath -javaagent:/Users/tanlinhu/.m2/repository/org/aspectj/aspectjweaver/1.7.4/aspectjweaver-1.7.4.jar"
jvmargs="$jvmargs -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
java $jvmargs -cp target/samples-1.0.0-jar-with-dependencies.jar com.amazonaws.swf.parallel.GreeterWorkflowWorker --with-error-handler
