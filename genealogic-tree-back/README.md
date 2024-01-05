# GenTree Back-End

This is the back-end part of the GenTree application.

## Table of Contents

- [Architecture](#architecture)
- [Gradle Configuration](#gradle-configuration)
- [Project Structure](#project-structure)
- [Project Structure](#project-structure)

## Architecture

The back-end is structured as a Spring Boot project using Gradle as the build tool. It includes controllers, models, repositories, services, and utility classes.

### Gradle Build Configuration

The `build.gradle` file contains the project's dependencies and configuration. Notable dependencies include Spring Boot starters for data, web, and Thymeleaf.

```groovy
// build.gradle

plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.4'
    id 'io.spring.dependency-management' version '1.1.3'
}


dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-web-services'
    runtimeOnly 'com.mysql:mysql-connector-j'
	// Dépendances Test
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.junit.platform:junit-platform-launcher:1.9.3'
    testImplementation fileTree(dir: 'libs', include: ['*.jar'])
}

```
### settings.gradle
The `settings.gradle` file specifies the root project name.

```gradle
rootProject.name = 'genealogic-tree-back'
```

### Javadoc
  under folder javadoc

# GenTree Back-End

This is the back-end part of the GenTree application.

## Project Structure

### `src/main`

- **java:**
  - **com.acfjj.app:**
    - **controller:** (Contains controllers)
    - **model:** (Contains models)
    - **repository:** (Contains repositories)
    - **service:** (Contains services)
    - **utils:** (Contains utility files)
    - **GenTreeApp.java:** (Main class to launch the back-end)

- **resources:**
  - **application.properties:**
    ```properties
    spring.application.name=GenTreeApp
    server.port=8080
    spring.jpa.hibernate.ddl-auto=update
    spring.session.jdbc.initialize-schema=always
    spring.datasource.url=jdbc:mysql://nas-clementc.synology.me:7777/GTREEDB_PROD?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=CFc9&qN#BHmE7Tt#
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    angular.app.url=http://localhost:4200
    ```
    - **test.properties:**
    ```properties
    spring.application.name=GenTreeAppTests
    server.port=8080
    spring.jpa.hibernate.ddl-auto=create
    spring.session.jdbc.initialize-schema=always
    spring.datasource.url=jdbc:mysql://localhost:3308/GTREEDB_TEST?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    angular.app.url=http://localhost:4200
    ```

### `src/test`

- **java:**
  - **model:**
  - **service:**
  

## Spring Boot Dependencies

- Spring Data JPA
- Spring Data JDBC
- Thymeleaf
- Spring Web
- Spring Web Services
- Spring Boot DevTools
- MySQL Driver

## How to Run

1. Clone the repository.
2. Navigate to the `genealogic-tree-back` directory.
3. Execute the `GenTreeApp.java` file. (`GenTreeAppTest.java` with Junit5 to run tests)
4. The back-end will start, and you can access it at [http://localhost:8080](http://localhost:8080).

Note: Make sure you have MySQL installed, and the database configurations in `application.properties` are appropriate.
