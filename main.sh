#!/bin/bash
export CLASSPATH=$CLASSPATH:./jdbc_unload-1.0_full.jar
export _JAVA_OPTIONS=--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED
java ewoks.jdbc_unload.OracleTableUnload $@
