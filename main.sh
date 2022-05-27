#!/bin/bash
export CLASSPATH=$CLASSPATH:/u/jdbc_unload/target/jdbc_unload-1.5_full.jar
export _JAVA_OPTIONS="--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED -Doracle.net.disableOob=true"
java ewoks.jdbc_unload.NetsuiteTableUnload "$@"
