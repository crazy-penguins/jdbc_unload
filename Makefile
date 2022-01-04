SHELL := /bin/bash
clean:
	find target -name 'jdbc_unload*.jar' -delete
build:
	# mvn package
	for x in `ls target/*-jar-with-dependencies.jar` ; do mv $$x $${x%-jar-with-dependencies.jar}_full.jar ; done
