# Blogging-Plattform

This documentation serves as a companion resource for developing an application using Java and Spring Boot. Its purpose is to provide developers with insights into best practices and techniques when using Spring Boot.

Essential parts, important source code reference and pitfalls are marked with the symbol ⚠️ to highlight them for quick flipping readers.

## Persistence

Spring Data is a part of the Spring Framework that simplifies the development of data access layers in Java applications. It provides a consistent and efficient way to interact with various data sources such as relational databases, NoSQL databases, and more.

The persistence of data in a Spring Boot application is supported by Spring Data JPA ([Jakarta Persistence API](https://de.wikipedia.org/wiki/Jakarta_Persistence_API)). JPA is a Java specification for mapping Java objects to relational databases. Spring Data JPA builds on top of it and offers additional abstractions and features to further simplify database interaction.

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

### Repositories and CRUD Operations

Spring Data JPA simplifies data access by providing repositories that automatically handle CRUD (Create, Read, Update, Delete) operations for entities. These repositories are defined as interfaces and implemented automatically by Spring.

⚠️ The `UserRepository` ([UserRepository.java](../d9f1756c0291ebfc4809d45250ae1cfd1bb8cbaa/users/src/main/java/de/sboe0705/users/persistence/UserRepository.java)) interface serves as an abstraction layer for managing `User` entities in the database. It extends the `CrudRepository` interface provided by Spring Data, which offers fundamental CRUD operations for entities. This interface seamlessly integrates with Spring Boot applications and offers convenient methods for interacting with the database.

### Custom Repository Methods

Spring documentation:
- [Defining Query Methods](https://docs.spring.io/spring-data/jpa/reference/data-commons/repositories/query-methods-details.html)
- [Repository query keywords](https://docs.spring.io/spring-data/jpa/reference/data-commons/repositories/query-keywords-reference.html)

### Testing

⚠️ In Spring Boot, the `data.sql` file ([data.sql](../d9f1756c0291ebfc4809d45250ae1cfd1bb8cbaa/users/src/test/resources/data.sql)) in the classpath automatically populates the database with initial data upon application startup or tests. This file, typically located under `src/main/resources`, contains SQL statements to insert seed or test data into the database tables. ⚠️ Additionally, setting the `spring.jpa.defer-datasource-initialization=true` property in the `application.properties` file delays the initialization of the data source until after Hibernate has set up the tables.

⚠️ The `UserRepositoryTest` class ([UserRepositoryTest.java](../d9f1756c0291ebfc4809d45250ae1cfd1bb8cbaa/users/src/test/java/de/sboe0705/users/persistence/UserRepositoryTest.java)) is a JUnit test class used to test the functionality of the `UserRepository` interface.

The `@DataJpaTest` annotation marks the test class as a JPA test class and is part of the Spring Test framework. This annotation configures the test to load only the components relevant to the JPA layer, making the test faster and more isolated. By default, it uses an embedded H2 database to execute the database operations during the test. See [Embedded Database Support](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.datasource.embedded) for further information about embedded databases.

### H2-Console

## REST Services

### RestController

### Testing

### OpenAPI / Swagger

### Hypermedia-Driven REST service

### HAL Explorer

### Spring Data REST

# IntelliJ IDEA

## Run Application
