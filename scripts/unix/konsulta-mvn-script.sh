#!/bin/bash

echo ------------------------------------------------
echo Konsulta Project - Maven Build and Install
echo ------------------------------------------------
echo start... 
echo mvn clean package...

localdir=$(dirname -- "$0")
cd $localdir

cd ..
cd ..

mvn clean package>logs/konsulta-maven-install.log

cd konsultadeskapp 
echo mvn clean install... 

mvn clean install -DskipTests assembly:assembly>/dev/null

cd .. 
echo end
echo ------------------------------------------------
echo from 'konsulta-maven-install.log' :
echo ------------------------------------------------
grep -C 1 "SUCCESS" logs/konsulta-maven-install.log
grep -C 1 "FAILURE" logs/konsulta-maven-install.log
echo ------------------------------------------------
echo see 'konsulta-maven-install.log' for details
echo ------------------------------------------------

