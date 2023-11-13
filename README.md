# Getting Started

### Documentation

This is the phone demo application, which provides the REST API for booking the device and CRUD operations with Phone entity. Please do the following steps to run application:

1. Pull from github. git clone 
2. There are two ways to run the application from IDE or with docker-compose
3. If you run from docker-compose, please make sure it is installed. [Link to install guide](https://docs.docker.com/compose/install/)
   After run following command in terminal: 
   
`` ./mvnw clean package -DskipTests `` 

`` cp target/phone_demo-0.0.1-SNAPSHOT.jar src/main/docker/``

`` cd src/main/docker/``

`` docker-compose up``

4. If you run from IDE just make sure you set up the environment variables for Postgresql DB: 
    - SPRING_DATASOURCE_URL={YOUR_DATASOURCE_URL}
    - SPRING_DATASOURCE_USERNAME={YOUR_DATASOURCE_USERNAME}
    - SPRING_DATASOURCE_PASSWORD={YOUR_DATASOURCE_PASSWORD}
    - SPRING_JPA_HIBERNATE_DDL_AUTO=update


5. After you run the application, you need to create the Phone entity:
``curl --location --request POST 'http://localhost:8080/api/phone' \
   --header 'x-auth-api-key: token' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "name": "Nokia 3310",
   "userName": "testUser",
   "userEmail": "userEmail@gmail.com"
   }'``
   
    or you can find all endpoints in [swagger-ui](http://localhost:8080/swagger-ui/#/phone-controller) 
   



6. To book/de-reserve the phone you need to send [updatePhone](http://localhost:8080/swagger-ui/#/phone-controller/updatePhoneUsingPUT) request with following body: 
   ``{
   "name": "Nokia 3310",
   "userName": "testUser",
   "userEmail": "nurbekm7@gmail.com",
   "available": false
   }``, where 
   - name - is the phone Device Name
    - userName - is the name of the user who want to book
    - userEmail - is email of user who want to book. Note: if you want to skip verification with email, just use phone-demo@gmail.com 
    - available - is the flag of availability, if false, than phone is booked 