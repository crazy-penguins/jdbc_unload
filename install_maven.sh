#!/bin/bash
if [[ ! -d /opt/maven ]] ; then
    mkdir -p /opt/maven ; 
fi
cd /opt/maven
url="https://archive.apache.org/dist/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz"
filename=$(basename $url)
wget $url
tar xzf $filename --strip-components=1
update-alternatives --install /usr/bin/mvn mvn /opt/maven/bin/mvn 384

