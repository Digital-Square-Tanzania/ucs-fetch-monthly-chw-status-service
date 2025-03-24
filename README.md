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
  java -jar build/libs/ucs-fetch-monthly-chw-status-service-<version>.jar
```

## Deployment via Docker

First Install docker in your PC by following [this guide](https://docs.docker.com/engine/install/). Secondly, clone this repo to your computer by using git clone and the repo's address:

`git clone https://github.com/Digital-Square-Tanzania/ucs-fetch-monthly-chw-status-service.git`

Once you have completed cloning the repo, go inside the repo in your computer: `cd ucs-fetch-monthly-chw-status-service`

Update `application.conf` found in `src/main/resources/` with the correct configs and use the following Docker commands for various uses:


## Run/Start

### Create Docker Network

Before running the docker container, create a docker network using the following command:
```bash
docker network create ucs-network
```
### Build the Docker Image

To build the Docker image, run the following command:

```bash
docker build -t ucs-fetch-monthly-chw-status-service .
```

### Run the Docker Container

To run the Docker container with environment variables and automatic restart on system reboot, use the following command:

```bash
docker run -d --network=ucs-network -p 127.0.0.1:9400:9400 \
  -e DB_HOST=10.90.0.1 \
  -e DB_PORT=5432 \
  -e DB_NAME=opensrp \
  -e DB_USER=username \
  -e DB_PASSWORD=password \
  --name ucs-fetch-monthly-chw-status-service \
  --restart unless-stopped \
  ucs-fetch-monthly-chw-status-service
```

### Explanation of Options:

- **`-d`**: Runs the container in detached mode (in the background).
- **`--network=ucs-network`**: Specifies the Docker network for the container.
- **`-p 127.0.0.1:9400:9400`**: Maps port 9400 on the host to port 9400 on the container.
- **Environment Variables**:
    - **`DB_HOST`**: The database host IP (e.g., `10.90.0.1`).
    - **`DB_PORT`**: The port on which the database is running (default is `5432`).
    - **`DB_NAME`**: The name of the database (e.g., `opensrp`).
    - **`DB_USER`**: The database user name (e.g., `username`).
    - **`DB_PASSWORD`**: The database user password (e.g., `password`).
- **`--name`**: Names the container as `ucs-fetch-monthly-chw-status-service`.
- **`--restart unless-stopped`**: Ensures the container restarts automatically unless explicitly stopped by the user.

---

### Interact With Shell

`docker exec -it ucs-fetch-monthly-chw-status-service sh`

### Stop Services

`docker stop ucs-fetch-monthly-chw-status-service`

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
