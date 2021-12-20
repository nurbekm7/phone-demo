echo $PWD
./mvnw clean package -DskipTests
cp target/phone_demo-0.0.1-SNAPSHOT.jar src/main/docker/
cd src/main/docker/
docker-compose up