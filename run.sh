mvn package -DskipTests
mvn exec:java -Dexec.args="$1 $2 $3 $4 $5"
