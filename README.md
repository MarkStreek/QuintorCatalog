[![Contributors][contributors-shield]][contributors-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Forks][forks-shield]][forks-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

# QuintorCatalogBackEnd

The Quintor Catalog is an automated hardware catalog for the software company Quintor. System administrators can add, update, delete, search, filter, sort, and borrow hardware components. 
CTO can approve/deny a borrow request to a user. The hardware is stored in a database. The front end is built using React. [Go to Front end repo](https://github.com/MarkStreek/QuintorCatalogFrontEnd)

![Screenshot1](docs/screenshots/homepage.png)
![Screenshot2](docs/screenshots/devices_page.png)
![Screenshot3](docs/screenshots/AddDevice_page.png)

This application was built in Spring Boot. Additionally, the application uses a MySQL database to store the hardware components. The information is served to the front end using a REST API. A REST API is a way to communicate between different software systems regardless of the operating system or programming language. 

 

[//]: #

[//]: # (![Screenshot4]&#40;docs/screenshots/homepage_phone.png&#41;)

[//]: # (<div align="center">)

[//]: # (  <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd">)

[//]: # (    <img src="https://quintor.nl/wp-content/uploads/2022/08/logo-square.png" alt="Logo" width="100" height="100">)

[//]: # (  </a>)

[//]: # (  <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd">)

[//]: # (    <img src="https://www.heeredwinger.nl/wp-content/uploads/2019/12/Logo-Hanze.jpg" alt="Logo" width="250" height="100">)

[//]: # (  </a>)

[//]: # (<h1 align="center">QuintorCatalogBackEnd</h3>)

[//]: # ()
[//]: # (  <p align="center">)

[//]: # (    Automated hardware catalog for the software company Quintor)

[//]: # (    <br />)

[//]: # (    <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd"><strong>Explore the docs »</strong></a>)

[//]: # (    <br />)

[//]: # (    <br />)

[//]: # (    <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd">View Demo</a>)

[//]: # (    ·)

[//]: # (    <a href="https://github.com/MarkStreek/QuintorCatalogBackEnd/issues">Report Bug</a>)

[//]: # (  </p>)

[//]: # (</div>)

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Getting Started](#getting-started)
- [About Quintor](#about-quintor)
- [About the project](#about-the-project)
- [Unit Tests](#unit-tests)
- [Built with](#built-with)
- [React Project](#react-project)

## Getting Started

**! It is very adviced to read the full insight of the project before starting the application !**

To start the application, clone the repo and navigate to the cloned folder. Type the following command in the terminal:

```bash
java -jar QuintorCatalogBackEnd-0.0.1-SNAPSHOT.jar
```

> Warning: before starting the application, you have to meet the following (minimum) requirements to run the application:

**Install**:
- Java 21 or higher
- Mysql 8.0

**Database Operations**:

 - Create a database account
 - Create a new database in mysql
 - Make sure the database is running
 - Make sure the database account has the correct permissions

**Application Operations**:

 - Fill in the database credentials in the application.properties file
 - Build the application using Gradle, navigate to the cloned folder and type the following command in the terminal:
 ```bash
 ./gradlew build
```

> After building the application, you can start the application using the command mentioned above.

## About Quintor

Quintor is a software company based in the Netherlands. Quintor helps customers to professionalize software development.

Quintor has the following disciplines:

1. Agile analytics
2. Software Development
3. Platform Engineering
4. Architecture
5. Cloud-native development
6. Security

Currently, Quintor Groningen is documenting all their hardware components in a simple Excel sheet. This is not efficient and not user-friendly.
Therefore, we created an automated hardware catalog for Quintor. This catalog will store all hardware components in the company.

Additionally, the catalog will be able to:

- Add new hardware components
- Update existing hardware components
- Delete hardware components
- Search for hardware components
- Filter hardware components
- Sort hardware components
- Borrow hardware components to a user
- CTO can approve/deny a borrow request to a user

## About the project

This project was created by students bioinformatics at the Hanze University of Applied Sciences. The main task was to create a (automated) catalog to store all hardware components in the company. 
The hardware tools are stored in a database. This information is served to the front end using a REST API.

## Full Insight

> This section will deep dive into the project functionalities and approaches. This is a must-read before starting the application. Additionally, this section is handy for future developers who want to expand to the project.

In the project, the following packages are present:

- **Auth** : This package contains the main security configuration. The main security filter is being defined here. Every new request that is made to the rest controllers is first being checked by the security filter.
- **Config** : This package contains the configuration of the application. One of these classes is responsible for putting some test data in the database, everytime the application starts up.
- **Controllers** : This package contains all the rest controllers. The REST controllers are responsible for handling the incoming requests.
- **DTO** : This package contains objects that are used to transfer data from the back end to the front end.
- **Entities** : This package contains all the entities. The entities are the objects that are stored in the database.
- **Model** : This package contains the models. The models are used to define the objects that are used in the application.
- **Repository** : This package contains the repositories. The repositories are used to communicate with the database.
- **Services** : This package contains the services. The services are used to handle the main logic and checking of the application.

### Auth

To start with the Auth package. This package contains the main security configuration. The main security filter is being defined here (`JwtAuthenticationFilter.java`). Every new request that is made to the rest controllers is first being checked by the `doFilterInternal.java` method of the class. The security filter checks if the request is allowed to be made. I.e., is the right token present in the request header? Or is the token still valid? If the token is not valid, the request will be denied/caught by the exception handler. More on the exception handler later. Additionally, the Auth package contains the security configuration in which the end points are being configured. /auth/login, for example is accessible for everyone, this is defined in the security configuration (SecurityConfig.java).

### Configuration

The configuration package contains two classes. One of these classes is responsible for putting some test data in the database, everytime the application starts up. This class probably should be empty/or deleted, when starting the application in production. The `WebConfig.java` class contains a single method that makes sure the request from localhost:3000 are not failing because of the CORS policy. Because the front end is running on localhost:3000, the request from the front end to the back end should be allowed. Normally this is not allowed, because you're making a request to your own server. Read more about CORS [here](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing).

### Controllers

The controllers package contains all the rest controllers. The REST controllers are responsible for handling the incoming requests. The REST controllers are the entry point of the application. The end-points are defined above methods (with `@Mapping`) of the REST controller. When a request is incoming, the method that is defined below the `@Mapping` is being called. Good to know, some end-point methods take arguments. For example take the `AuthController.java` class and look at the login method:

```java
@PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest request) {
    return authenticationService.signin(request);
}
```

This method takes a LoginRequest as input. LoginRequest is simple object that is defined in the `model` package:

```java
// LoginRequest.java

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
```
When creating a request to the /auth/login end-point, the request should contain a body with an email and password. The body should be in JSON format. Spring will **automatically** cast/map this incoming JSON request to the LoginRequest object. The incoming request body:

```json
{
    "email": "some_user@info.com",
    "password": "welcome123"
}
```

Will be cast to a LoginRequest object. This is very handy, because you don't have to do this manually. This way of incoming request-to-object mapping is used more often in the project. Later, in the `DTO`, we will see that this method is extremely useful for `@Validation`.

### DTO

When listing devices in the frond end, you need devices, locations, device specification, etc. It is very redundant to first request the devices, then the locations, etc. Here comes the DTO package in play. DTO stands for Data Transfer Object. The DTO package contains objects that are used to transfer data from the back end to the front end. The `DeviceDTO.java` class is the most important class in the DTO package. This class contains all the information that is needed to display a device in the front end. Additionally, The `DeviceDTO.java` is also used as incoming request. Just like we were talking above: `@RequestBody @Valid DeviceDTO deviceDTO`.

Right before sending the data to the front end, the right database lines are called from the database and converted to DTO. This is done in the `Converter` classes. The `Converter.java` class contains a method that: creates a new DTO object, sets the right values with incoming database lines (devices, locations, specifications, etc.) and returns the DTO object.

Because the DTO is sometimes also used as incoming request, the `@Valid` annotation is used. The `@Valid` annotation is used to validate the incoming request. These annotation is placed in the controller and checks for more validation annotations in the incoming request-object. For example, the `@NotNull` annotation is used in the `DeviceDTO.java` class. This annotation checks if the incoming request contains a value for the field. An example validation in the `DeviceDTO.java` class:

```java
@NotNull
@NotEmpty
@Length(min = 1, max = 50, message = "Merk moet tussen de 1 en 50 karakters lang zijn")
private String brandName;
```

Message is passed to the exception handler when the validation fails.

### Database schema

Below you can find the database schema. The fields on the scheme may differ slightly but the main structure is the same.

![Database Schema](docs/DB_Scheme.png)

The HardwareSpecifications contains a device id and a spec id. The spec id is placed in a different table. This is because it's now future and expanding proof. A user can add a new spec in the spec table and give it a value in the HardwareSpecs table. They are completely separate from each-other.

### Entities

The entities package contains all the entities. The entities are the objects that are stored in the database. The entities are the objects that are being converted to DTO objects. The entities are actually just database schemes. The entities in classes are annotated with `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, etc. These annotations are used to define the database scheme. Only the `Role.java` class is not an entity. The `Role.java` class is used to define the roles in the application. The roles are used to define the permissions in the application.

After every push to the main branch, the unit tests will be run automatically. The tests are run using GitHub Actions and executed on an ubuntu-latest runner. Results are shown in the badge below:

>[![Run Tests](https://github.com/MarkStreek/QuintorCatalogBackEnd/actions/workflows/runTests.yml/badge.svg)](https://github.com/MarkStreek/QuintorCatalogBackEnd/actions/workflows/runTests.yml)

## Built with

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

## React Project

The front end of this project can be found [here](https://github.com/MarkStreek/QuintorCatalogFrontEnd)






<!-- Markdown Links -->
[contributors-shield]: https://img.shields.io/github/contributors/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[contributors-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/graphs/contributors
[stars-shield]: https://img.shields.io/github/stars/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[stars-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/stargazers
[issues-shield]: https://img.shields.io/github/issues/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[issues-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/company/quintor/
[forks-shield]: https://img.shields.io/github/forks/MarkStreek/QuintorCatalogBackEnd.svg?style=for-the-badge
[forks-url]: https://github.com/MarkStreek/QuintorCatalogBackEnd/network/members