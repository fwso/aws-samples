#!/bin/bash

jvmargs=-javaagent:/Users/tanlinhu/.m2/repository/org/aspectj/aspectjweaver/1.7.4/aspectjweaver-1.7.4.jar
java $jvmargs -cp target/samples-1.0.0-jar-with-dependencies.jar com.amazonaws.swf.parallel.GreeterWorkflowWorker
