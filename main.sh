#!/bin/bash
export CLASSPATH=$CLASSPATH:./jdbc_unload-1.0_full.jar
java ewoks.jdbc_unload.OracleTableUnload $@

