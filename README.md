# ls-speech
Technical Assessment to create an API for speeches, supporting CRUD operations and a search feature.
* Springboot 3.1.5
* Java 17
* MySQL 8.1

## Set-up
1. Configure the database connection strings at
    * `pom.xml` for Flyway, line 68
    * `src/main/resources/application.properties`
2.  Create the database `speech`
3.  Build and migrate
```
mvn spring-boot:run
mvn flyway:migrate
```

## API Calls
Get all speeches
```
curl --location 'http://localhost:8080/speech/all'
```
<br />

Get speech by ID
```
curl --location 'http://localhost:8080/speech/find/1'
curl --location 'http://localhost:8080/speech/find/5'

# Not found
curl --location 'http://localhost:8080/speech/find/10'
```
<br />

Create new speech
```
curl --location 'http://localhost:8080/speech/add' \
--header 'Content-Type: application/json' \
--data '{
    "author": "You",
    "speech": "tester",
    "keywords": "america philippines",
    "date": "2023-22-10T17:50:50"
}'

# Returns 'must not be null'
curl --location 'http://localhost:8080/speech/add' \
--header 'Content-Type: application/json' \
--data '{
    "author": "You",
    "speech": "tester",
}'
```
<br />


Update Speech
```
curl --location --request PUT 'http://localhost:8080/speech/update' \
--header 'Content-Type: application/json' \
--data '{
    "id": 6,
    "author": "U",
    "speech": "Automated Tester",
    "keywords": "American Filipino",
    "date": "2001-01-01"
}'
```
<br />


Delete Speech
```
curl --location --request DELETE 'http://localhost:8080/speech/delete/6'
```
<br />


Search Feature
```
# Returns all speeches
curl --location --request GET 'http://localhost:8080/speech/search' \
--header 'Content-Type: application/json' \
--data '{}'

# Returns all authors containing 'ar'
curl --location --request GET 'http://localhost:8080/speech/search' \
--header 'Content-Type: application/json' \
--data '{
    "author": "ar"
}'

# Returns all speechs with the keyword 'equality' OR 'food'
curl --location --request GET 'http://localhost:8080/speech/search' \
--header 'Content-Type: application/json' \
--data '{
    "keywords": "equality food"
}'

# Returns all speeches with the author 'reg' OR with the word 'dinner'
curl --location --request GET 'http://localhost:8080/speech/search' \
--header 'Content-Type: application/json' \
--data '{
    "author": "reg",
    "speech": "dinner"
}'

# Returns all speeches with dates between '2000-01-10' and '2025-01-01'
curl --location --request GET 'http://localhost:8080/speech/search' \
--header 'Content-Type: application/json' \
--data '{
    "startDate": "2000-01-01",
    "endDate": "2025-01-01"
}'
```
