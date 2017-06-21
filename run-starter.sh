#!/bin/bash

. ../aws-config.sh

logpath=/tmp/log4j/starter

java -Dlog=$logpath -cp target/samples-1.0.0-jar-with-dependencies.jar com.amazonaws.swf.parallel.GreeterMain $@
