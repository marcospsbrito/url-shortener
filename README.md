# url-shortener
A simple URL shortener project.

## Install

This project is developed in Spring Boot, Java 8 and Maven. The database is an instance of built-in memory database H2. 

To run the project you have to clone this project and run follows Maven command.

`mvn spring-boot:run`


## Documentation

* Postman default requests https://www.getpostman.com/collections/3f2eee6c6e29a7dadbed
* Host url - https://short-url-marcospsbrito.herokuapp.com/

### Creation Body request

```json
    "url":"Required",
    "expiresInMinutes":"optional Integer"
```

### Creation Body response

```json
    "newUrl":"http://valid.url",
    "expiresAt":"Creation date plus expiresInMinutes or default value, 5 minutes"
```
