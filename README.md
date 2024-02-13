# URL Shortener Api

You can access the React app that is connected to this Spring boot application on this address : [Url shortener UI](https://urlshortener-ui-1c6938ccabc0.herokuapp.com/)

It's on Eco mode on Heroku so on the first connection the server needs to launch so it could take some time for the first shorten Url.

## Overview

This project is a Java Spring Boot application built to leverage modern tools and technologies for efficient development, deployment, and management of a database-driven web application. By integrating Liquibase for database version control, PostgreSQL for reliable and scalable data storage, Docker for containerization, Gradle for build automation, and Lombok for reducing boilerplate code, this project aims to provide a streamlined development experience.

## Key Features

1. **Spring Boot Framework**: Simplifies configuration and eliminates boilerplate code associated with traditional Spring applications.

2. **Liquibase Integration**: Manages database schema versioning, ensuring consistency and reliability across different environments.

3. **PostgreSQL Database**: Offers robust features, reliability, and scalability for demanding data storage requirements.

4. **Docker Containerization**: Provides consistency, portability, and scalability by packaging the application into lightweight containers.

5. **Gradle Build Automation**: Simplifies managing dependencies, compiling source code, and packaging the application for deployment.

6. **Lombok Annotation Library**: Reduces boilerplate code and improves code readability by automatically generating common Java code.

## Getting Started

### Prerequisites

- Docker

### Installation

1. Clone the repository:

```bash
git clone https://github.com/BastienCarbonnier/url-shortener-api
```

2. Start application by docker

```bash
docker compose -f docker/docker-compose.yaml -p "url-shortener" up
```

You can access http://localhost:3001 and you will find the React application connected directly to this Spring boot application.

This docker-compose file is pulling latest image of my personal docker hub repository.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Liquibase](https://www.liquibase.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)
- [Gradle](https://gradle.org/)
- [Project Lombok](https://projectlombok.org/)