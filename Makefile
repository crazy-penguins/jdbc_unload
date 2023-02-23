SHELL := /bin/bash
clean:
	find target -name 'jdbc_unload*.jar' -delete
build:
	mvn package
	for x in `ls target/*-jar-with-dependencies.jar` ; do mv $$x $${x%-jar-with-dependencies.jar}_full.jar ; done
legacy:
	mv pom.xml pom.modern.xml && mv pom.legacy.xml pom.xml
	mvn package
	mv pom.xml pom.legacy.xml && mv pom.modern.xml pom.xml
	# for x in `ls target/*-jar-with-dependencies.jar` ; do mv $$x $${x%-jar-with-dependencies.jar}_full.jar ; done

