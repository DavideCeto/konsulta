@ECHO ------------------------------------------------
@ECHO Konsulta Project - Maven Build and Install
@ECHO ------------------------------------------------
@ECHO start... 
@ECHO OFF
@ECHO mvn clean package... 
call mvn clean package>konsulta-maven-install.log 

cd konsultadeskapp 
@ECHO mvn clean install... 
call mvn clean install -DskipTests assembly:assembly>>../konsulta-maven-install.log 

cd.. 
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