#!/usr/bin/env bash
PROB=$1
cd ..
rm -r A
rm -r B
rm -r C
rm *.tgz
mkdir -p ${PROB}
cp -r KickStartCodeJam/src ${PROB}/src
cp KickStartCodeJam/pom.xml ${PROB}/pom.xml
rm -rf ${PROB}/src/main/resources
rm -rf ${PROB}/src/test
tar -zcvf ${PROB}.tgz ${PROB}
