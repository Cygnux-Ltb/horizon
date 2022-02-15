mvn install:install-file -Dfile=thostapi-6.3.13.jar -DgroupId=ctp -DartifactId=thostapi -Dversion=6.3.13 -Dpackaging=jar

sleep 1s

mvn install:install-file -Dfile=thostapi-6.3.15.jar -DgroupId=ctp -DartifactId=thostapi -Dversion=6.3.15 -Dpackaging=jar
