#!/bin/bash
export PATH=$JAVA_HOME/bin:$PATH
ant -buildfile build.xml
cp README output/

echo "build success!"
exit 0
