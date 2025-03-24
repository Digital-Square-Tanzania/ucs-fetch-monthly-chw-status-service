# CHW Monthly Status Service

## Overview
This service is built using **Akka HTTP** to handle eligibility checks for Community Health Workers (CHWs) based on their activity in a given month. It exposes an endpoint that processes requests containing CHW details and responds with their eligibility status.

## Features
- Built using **Akka HTTP** for high-performance, non-blocking request handling.
- Uses **PostgreSQL** as the database.
- Implements **Jackson** for JSON serialization/deserialization.
- Provides a REST API endpoint for checking CHW monthly eligibility.

## Prerequisites
Ensure you have the following installed:
- **Java 11+**
- **PostgreSQL** (with the required database and tables set up)
- **Maven** or **Gradle** for dependency management

## Installation
Clone the repository and navigate into the project directory:
```sh
$ git clone https://github.com/your-repo/chw-status-service.git
$ cd chw-status-service
```

## Configuration
Set up environment variables or modify `application.conf` to match your database settings:

```
DB_HOST=127.0.0.1
DB_PORT=5432
DB_NAME=your_database_name
DB_USER=your_username
DB_PASSWORD=your_password
```

## API Endpoints

### 1. Check CHW Monthly Status
#### Endpoint:
```http
POST /chw/monthly-status
```
#### Request Body:
```json
{
  "period": {
    "month": "03",
    "year": "2025"
  },
  "chws": [
    {
      "NationalIdentificationNumber": "19890823-53103-00002-23",
      "OpenmrsProviderId": "agakhanchw"
    },
    {
      "NationalIdentificationNumber": "19890823-00898-98783-30",
      "OpenmrsProviderId": "NafubaCHW"
    }
  ]
}
```
#### Response:
```json
[
  {
    "NationalIdentificationNumber": "19890823-53103-00002-23",
    "Eligible": true
  },
  {
    "NationalIdentificationNumber": "19890823-00898-98783-30",
    "Eligible": true
  }
]
```

## Building and Running the Service
To build and run the service after performing the above configurations, run the following:
```sh
  ./gradlew clean shadowJar
  java -jar build/libs/ucs-lab-module-integration-service-<version>.jar
```

## Deployment via Docker

First Install docker in your PC by following [this guide](https://docs.docker.com/engine/install/). Secondly, clone this repo to your computer by using git clone and the repo's address:

`git clone https://github.com/Digital-Square-Tanzania/ucs-lab-module-integration-service.git`

Once you have completed cloning the repo, go inside the repo in your computer: `cd ucs-lab-module-integration-service`

Update `application.conf` found in `src/main/resources/` with the correct configs and use the following Docker commands for various uses:

### Run/start
`docker build -t ucs-lab-module-integration-service .`

`docker run -d -p 127.0.0.1:9400:9400 ucs-lab-module-integration-service`

### Interact With Shell

`docker exec -it ucs-lab-module-integration-service sh`

### Stop Services

`docker stop ucs-lab-module-integration-service`

## Database Setup
Ensure your PostgreSQL database has the necessary tables:
```sql
CREATE TABLE hps_client_services (
    provider_id VARCHAR(255),
    event_date DATE
);

CREATE TABLE hps_household_services (
    provider_id VARCHAR(255),
    event_date DATE
);

CREATE TABLE hps_death_registrations (
    provider_id VARCHAR(255),
    event_date DATE
);

CREATE TABLE hps_mobilization_services (
    provider_id VARCHAR(255),
    event_date DATE
);
```

## Logging
Logs are stored in `logs/app.log`. You can also check the console output for debugging information.

## License
ISC

## Author
Ilakoze Jumanne

## Version
1.0.0

## Contact
For support, please contact **ilakozejumanne@gmail.com**.

