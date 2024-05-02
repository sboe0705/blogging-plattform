# Blogging-Plattform

This documentation serves as a companion resource for developing an application using Java and Spring Boot. Its purpose is to provide developers with insights into best practices and techniques when using Spring Boot.

Essential parts, important source code reference and pitfalls are marked with the symbol ⚠️ to highlight them for quick flipping readers.

## Setup

⚠️ Setting up a Spring Boot application is super easy: Just create a Maven project and set `org.springframework.boot:spring-boot-starter-parent` as parent. This integrated all required dependency (and managed dependencies) as well as the `spring-boot-maven-plugin` providing a load of goal to support the build and developer experience. See the `pom.xml` of the users application: [pom.xml](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/pom.xml#L7-L29)   

Further Spring Boot features are integrated by added respective Maven Dependencies, e.g. `org.springframework.boot:spring-boot-starter-web` for web applications (described in detail later). 

For more information and alternatives see [Spring Boot Maven Plugin Documentation / Using the Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/#using).

### Setup Generator

⚠️ If you already know which Spring Boot Features you need, you can use the [spring initializr](https://start.spring.io/) to generate your project setup by simply defining some basic configuration (JDK version, build tool, Maven coordinates, etc.) and selecting the required Spring Boot dependencies. You get a ZIP file containing the initial project setup which can be extended.

---

## Persistence

Spring Data is a part of the Spring Framework that simplifies the development of data access layers in Java applications. It provides a consistent and efficient way to interact with various data sources such as relational databases, NoSQL databases, and more.

The persistence of data in a Spring Boot application is supported by Spring Data JPA ([Jakarta Persistence API](https://de.wikipedia.org/wiki/Jakarta_Persistence_API)). JPA is a Java specification for mapping Java objects to relational databases. Spring Data JPA builds on top of it and offers additional abstractions and features to further simplify database interaction.

⚠️ Spring Data JPA can easily be included by adding the Maven dependency `org.springframework.boot:spring-boot-starter-data-jpa` as well as the database dependency e.g. `com.h2database:h2`, see [pom.xml](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/pom.xml).

Spring documentation:
- [JPA and Spring Data JPA](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.jpa-and-spring-data)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa) (Guide)

### Entity Classes and Mapping

⚠️ To persist data, you simply define POJOs (Plain Old Java Objects) that represent your data model as entities. These entities are annotated to define their relationship with the database. In the example entity class `User` ([User.java](../d9f1756c0291ebfc4809d45250ae1cfd1bb8cbaa/users/src/main/java/de/sboe0705/users/persistence/User.java)) the following annotations are used: 

1. `@Entity`: This annotation marks the `User` class as a JPA entity, meaning it will be managed by the Java Persistence API and can be persisted to a database.

2. `@Table(name = "\"user\"")`: This annotation specifies the name of the table in the database that corresponds to this entity. ⚠️ Since "user" is a reserved keyword in some databases, it's enclosed in double quotes to avoid conflicts.

3. `@Id`: This annotation marks the `id` field as the primary key of the entity.

4. `@GeneratedValue`: This annotation indicates that the value of the primary key will be generated automatically, either by the database (AUTO mode) or by JPA itself (IDENTITY mode).

The other fields `firstName` and `lastName` are simple attributes of the entity, defined without any specific JPA annotations.

[Hibernate](https://de.wikipedia.org/wiki/Hibernate_(Framework)), as the JPA provider, will use the metadata defined by the annotations in the `User` entity class to automatically generate a corresponding database table during application startup.

### Advanced Mapping Techniques

***// TODO** Many-To-Many - Mapping*

### Repositories and CRUD Operations

Spring Data JPA simplifies data access by providing repositories that automatically handle CRUD (Create, Read, Update, Delete) operations for entities. These repositories are defined as interfaces and implemented automatically by Spring.

⚠️ The `UserRepository` ([UserRepository.java](../d9f1756c0291ebfc4809d45250ae1cfd1bb8cbaa/users/src/main/java/de/sboe0705/users/persistence/UserRepository.java)) interface serves as an abstraction layer for managing `User` entities in the database. It extends the `CrudRepository` interface provided by Spring Data, which offers fundamental CRUD operations for entities. This interface seamlessly integrates with Spring Boot applications and offers convenient methods for interacting with the database.

### Custom Repository Methods

***// TODO** e.g. findFooByBarOrderByMoo*

Spring documentation:
- [Defining Query Methods](https://docs.spring.io/spring-data/jpa/reference/data-commons/repositories/query-methods-details.html)
- [Repository query keywords](https://docs.spring.io/spring-data/jpa/reference/data-commons/repositories/query-keywords-reference.html)

### Testing

⚠️ In Spring Boot, the `data.sql` file ([data.sql](../56d9ae648feb53f07f0aca15215555b6c6deb0d6/users/src/test/resources/data.sql)) in the classpath automatically populates the database with initial data upon application startup or tests. This file, typically located under `src/main/resources`, contains SQL statements to insert seed or test data into the database tables. ⚠️ Additionally, setting the `spring.jpa.defer-datasource-initialization=true` property in the `application.properties` file delays the initialization of the data source until after Hibernate has set up the tables.

⚠️ The `UserRepositoryTest` class ([UserRepositoryTest.java](../d9f1756c0291ebfc4809d45250ae1cfd1bb8cbaa/users/src/test/java/de/sboe0705/users/persistence/UserRepositoryTest.java)) is a JUnit test class used to test the functionality of the `UserRepository` interface.

The `@DataJpaTest` annotation marks the test class as a JPA test class and is part of the Spring Test framework. This annotation configures the test to load only the components relevant to the JPA layer, making the test faster and more isolated. By default, it uses an embedded H2 database to execute the database operations during the test. See [Embedded Database Support](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.datasource.embedded) for further information about embedded databases.

⚠️ You need the Maven dependencies `org.springframework.boot:spring-boot-starter-test` (_test_ scoped) for testing, see [pom.xml](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/pom.xml).

### H2-Console

The H2 Console is a web-based database management tool provided by the [H2 Database Engine](https://www.h2database.com). It allows users to interact with the H2 in-memory (used by Spring Boot as default) via a browser-based interface.

⚠️ To enable the H2 Console in a Spring Boot application, set the application property `spring.h2.console.enabled=true`. After that the H2 Console is available under [http://localhost:8080/h2-console](http://localhost:8080/h2-console). For en even more comfortable usage, define a stable database name with the application property `spring.datasource.url=jdbc:h2:mem:users` (as default a random name is used).

### Database Migration

***// TODO** Flyway*

---

## REST Services

### RestController

***// TODO***

### Testing

REST services can be tested either in unit tests or in integration tests with the whole application running on a web server. For further descriptions see the next two chapters.

Spring Guide: https://spring.io/guides/gs/testing-web

#### Unit Testing Web Layer

***// TODO***

#### Integration Testing with server

⚠️ The `UserRestRepositoryIT` class (see [UserRestRepositoryIT.java](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/src/test/java/de/sboe0705/users/rest/UserRestRepositoryIT.java)) tests the auto-generated REST service provided by the `UserRepository` in an integration test scenario. In this test, the server is started, and the web layer is tested with real HTTP requests. The test is configured with the following annotations:

1. `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`: Loads the entire Spring Boot application context including the embedded servlet container with a randomly chosen port for the test.

2. `@LocalServerPort`: Injects the local port of the running server into the test class, necessary to dynamically construct the URLs in the test methods.

3. `@Autowired TestRestTemplate`: Provided by Spring Boot for making HTTP requests.

### OpenAPI / Swagger

***// TODO***

### Hypermedia-Driven REST service

***// TODO** HATEAOS*

***// TODO** RESTful*

Spring Guide: https://spring.io/guides/gs/rest-hateoas

### HAL Explorer

***// TODO***

### Spring Data REST

⚠️ Spring Data REST is a convenient way to expose CRUD operations of Spring Data Repositories as RESTful services with minimal configuration. After including the Maven dependency `org.springframework.boot:spring-boot-starter-data-rest` (see [pom.xml](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/pom.xml#L36-L39)) the configured repositories expose there CRUD operations as RESTful services using the HAL Media-Type format as default, e.g. the `UserRepository` (see [UserRepository.java](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/src/main/java/de/sboe0705/users/persistence/UserRepository.java)) is accessible under http://localhost:8080/api/users.

⚠️ The `UserRestRepositoryTest` class (see [UserRestRepositoryTest.java](../579f6ec83f1d97a1b07486366220d59dd1405d00/users/src/test/java/de/sboe0705/users/rest/UserRestRepositoryTest.java)) tests the auto-generated REST service provided by the `UserRepository`. Since in this case the repository, rather than controller class, serves the REST service, the `@WebMvcTest` annotation cannot be used. Instead, the complete Spring Boot application context is loaded using `@SpringBootTest` and a `MockMvc` instance is injected with `@AutoConfigureMockMvc`.

Spring Guide: https://spring.io/guides/gs/accessing-data-rest 

### Security

***// TODO** OIDC with authentication server*

---

# IntelliJ IDEA

## Run Application

***// TODO***
