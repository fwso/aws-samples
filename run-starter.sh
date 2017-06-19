#!/bin/bash

. env.sh

java -cp target/samples-1.0.0-jar-with-dependencies.jar com.amazonaws.swf.parallel.GreeterMain $@
