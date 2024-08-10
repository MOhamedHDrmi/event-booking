
# Event Booking

This service enables customers to effortlessly create, discover, and secure tickets for various events. Users can seamlessly view and manage their reservations, and the system also incorporates periodic notifications to alert users before the commencement of the event.

*This application follows the Hexagonal Architecture as its main design paradigm*



## Technologies and Tools

* Java 11
* Maven
* Spring boot.2.7.14 (Web - Security - Jpa - Test)
* Junit and Mockito
* Flyway
* H2 in-memory database
* OpenApi plugin


## Build and Run

 1. Clone the project

 2. Go to the project directory

#### Run commands
```bash
docker-compose build

docker-compose up
```
## Test
Now service is up and running on port 8080, So we can start the test based on the [API specifications](./event-booking-adapter-inbound-rest/README.md) using postman or any similar tool 
