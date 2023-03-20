# The server part of the online store "Тапки-Лапки"

This is a service part of an online store written in Java using the Spring Boot framework.

- Backend: Java + Spring Boot + Spring Security + Spring Data JPA + Flyway + GraphQL
- Database: PostgreSQL
---

A Super Admin is created at system startup if it is not in the Database. If you don't have such a user in your Database, enter his data in the _.env_ file.

Docker ports:
- backend: 8080
- database: 5433 (if PostgreSQL is not running on the host, set port 5432 to _docker-compose.yml_)
---
### GraphQL

This service uses GraphQL as a data transfer protocol. All query and mutation are registered in the file _schema.graphqls_ (path: _src/main/resources/graphql/schema.graphqls_).

You can also use the web-interface to test queries (http://localhost:8080/graphiql).