mvn clean compile package install
mvn install:install-file -Dfile=target/console-utils-assembly-latest.jar -DgroupId=yankov -DartifactId=console-utils-assembly -Dversion=latest -Dpackaging=jar
