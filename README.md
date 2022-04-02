# Airline System Always Online

This project focuses on the booking function for airline system. 

For the contruction of this project was use microservice architecture and each microservice was written in a different language.

## Microservices

|                Service                |   Language   |                         Description                        |
|:-------------------------------------:|:------------:|:----------------------------------------------------------:|
| airline-system-always-online-web-app  | Typescript   | Basic frontend that allows to book flight                  |
| airline-system-always-online-booking  | Java Spring  | Booking flights and stores in postgres db                  |
| airline-system-always-online-checkin  | Java Spring  | Chekin the flights and stores in postgres db               |
| airline-system-always-online-email    | Python Flask | Sends users a booking confirmation                         |
| airline-system-always-online-list     | Python Flask | List Amadeus flights and cache them in Redis               |
| airline-system-always-online-log      | Python Flask | Sends important logs from booking (not completed)          |
| airline-system-always-online-payments | Java Spring  | Payments booking with Stripe and store data in postgres db |
| airline-system-always-online-user     | Java Spring  | Manages users in the system and store them in postgres db  |

## Deploy

This microservices can deploy in OpenShift 4 with s2i feature.

```bash
# Create a new project
oc new-project airline-system-always-online

# Deploy postgres db

# booking
oc new-app --name booking-postgresdb \
    -e POSTGRESQL_USER=postgres \
    -e POSTGRESQL_PASSWORD=admin \
    -e POSTGRESQL_DATABASE=asao-ms-booking \
    postgresql:9.5

# checkin
oc new-app --name checkin-postgresdb \
    -e POSTGRESQL_USER=postgres \
    -e POSTGRESQL_PASSWORD=admin \
    -e POSTGRESQL_DATABASE=asao-ms-checkin \
    postgresql:9.5

# payment
oc new-app --name payments-postgresdb \
    -e POSTGRESQL_USER=postgres \
    -e POSTGRESQL_PASSWORD=admin \
    -e POSTGRESQL_DATABASE=asao-ms-payment \
    postgresql:9.5

# users
oc new-app --name user-postgresdb \
    -e POSTGRESQL_USER=postgres \
    -e POSTGRESQL_PASSWORD=admin \
    -e POSTGRESQL_DATABASE=asao-ms-users \
    postgresql:9.5

# list
# Deploy redis
https://docs.redis.com/latest/kubernetes/deployment/openshift/openshift-cli/

# Deploy microservices+

# web app
oc new-app \
--name web-app \
--context-dir airline-system-always-online-web-app https://github.com/sergioseks/airline-system-always-online.git

# booking
oc new-app \
--name booking \
--context-dir airline-system-always-online-booking https://github.com/sergioseks/airline-system-always-online.git \ 
-e POSTGRESQL_USER=postgres \
-e POSTGRESQL_PASSWORD=admin \
-e POSTGRESQL_SERVICE_HOST=booking-postgresdb \
-e POSTGRESQL_SERVICE_PORT=5432 \
-e POSTGRESQL_DATABASE=asao-ms-booking

# checkin
oc new-app \
 --name checkin \
 --context-dir airline-system-always-online-checkin https://github.com/sergioseks/airline-system-always-online.git
 -e POSTGRESQL_USER=postgres \
 -e POSTGRESQL_PASSWORD=admin \
 -e POSTGRESQL_SERVICE_HOST=checkin-postgresdb \
 -e POSTGRESQL_SERVICE_PORT=5432 \
 -e POSTGRESQL_DATABASE=asao-ms-checkin

# email
oc new-app \
--name email \
--context-dir airline-system-always-online-email https://github.com/sergioseks/airline-system-always-online.git
-e USER_EMAIL=my_email
-e USER_PASSWORD=my_password

# list
oc new-app \
--name list \
--context-dir airline-system-always-online-list https://github.com/sergioseks/airline-system-always-online.git
-e REDIS_SERVICE_HOST=redis_service_name
-e REDIS_SERVICE_PORT=redis_service:port

# payments
oc new-app \
 --name payments \ 
 --context-dir airline-system-always-online-payments https://github.com/sergioseks/airline-system-always-online.git
 -e POSTGRESQL_USER=postgres \
 -e POSTGRESQL_PASSWORD=admin \
 -e POSTGRESQL_SERVICE_HOST=payments-postgresdb \
 -e POSTGRESQL_SERVICE_PORT=5432 \
 -e POSTGRESQL_DATABASE=asao-ms-payments

# user
oc new-app \
--name user \ 
--context-dir airline-system-always-online-user https://github.com/sergioseks/airline-system-always-online.git
 -e POSTGRESQL_USER=postgres \
 -e POSTGRESQL_PASSWORD=admin \
 -e POSTGRESQL_SERVICE_HOST=user-postgresdb \
 -e POSTGRESQL_SERVICE_PORT=5432 \
 -e POSTGRESQL_DATABASE=asao-ms-user
```

