@ECHO ------------------------------------------------
@ECHO Konsulta Project - Maven Build and Install
@ECHO ------------------------------------------------
@ECHO start... 
@ECHO OFF
@ECHO mvn clean package...

cd ..
cd ..

call mvn clean package>logs/konsulta-maven-install.log

cd konsultadeskapp

@ECHO mvn clean install... 

call mvn clean install -DskipTests assembly:assembly

cd ..
@ECHO end
@ECHO ------------------------------------------------
@ECHO from 'konsulta-maven-install.log' :
@ECHO ------------------------------------------------
FINDSTR /L /I /N "SUCCESS" konsulta-maven-install.log
FINDSTR /L /I /N "FAILURE" konsulta-maven-install.log
@ECHO ------------------------------------------------
@ECHO see 'konsulta-maven-install.log' for details
@ECHO ------------------------------------------------
@ECHO OFF
pause