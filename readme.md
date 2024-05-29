# Comic Reader Mobile App - Backend

This is a mobile application for reading comics, backend built using Spring Boot.

## Features

- Browse and search for comics
- Read comics with a user-friendly interface
- Bookmark your favorite comics for easy access
- Rate and review comics
- User authentication and profile management

## Technologies Used

- Spring Boot: The server-side framework used with Java 21
- PostgreSQL 16
- Spring Security: For secure user authentication and authorization
- JWT: For secure transmission of information between client and server
- Hibernate: For object-relational mapping
- Maven: Dependency Management

## Getting Started

To get a local copy up and running, follow these steps:

1. Clone the repository
```
git clone https://github.com/LongVu2190/comic_reader.git
```

2. Install the dependencies
```
mvn install
```

3. Set up your database and update the `application.properties` file with your database credentials

4. Set up mail account & password for forgot password function and ChatGPT key in `application.properties`

5. Run application using `IntelliJ` or `VSCode` or command

```
mvn spring-boot:run
```