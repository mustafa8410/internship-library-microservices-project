# Internship Library Microservices Project

This project is a **Library Management System** that i developed during my internship using the **Microservices Architecture**. The system allows for efficient management of books, users, and loans while maintaining a modular and scalable codebase. Each module is implemented as an independent microservice, sharing a common database for communication and data persistence.

---

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Microservices](#microservices)
- [Setup and Installation](#setup-and-installation)
- [Contribution](#contribution)

---

## Project Overview
The Library Management System is built to demonstrate the use of microservices in solving common problems in large-scale applications. Each microservice is responsible for a specific domain of the application and communicates through a shared relational database. This approach simplifies development and ensures modularity.

---

## Technologies Used
- **Programming Language**: Java
- **Framework**: Spring Boot
- **Build Tool**: Gradle
- **Database**: Shared Relational Database (e.g., MySQL or PostgreSQL)
- **Persistence**: JPA/Hibernate

---

## Some Features
1. **Book Management**: Add, update, delete, and fetch books.
2. **User Management**: Register, update, delete, and fetch user details.
3. **Loan Management**: Rent books and return them.
4. **Centralized Database**: All microservices share a common database for seamless data management.
5. **Validation and Error Handling**: Robust input validation and custom error responses.
6. **Scalability**: Modular architecture allows easy extension of functionality.
7. **Enhanced Security**: No user can get, update, create or delete an info about another. 

---

## Microservices
### 1. **Webapi**
   This is the microservice that handles the HTTP Requests and responds them.

### 2. **Batch**
   Has three scheduled operations that run in different scheduled times respectively. One is responsible for sending mails to the users for expired rentals, one is responsible for getting mass inputs from .csv files, and the other creates a report .csv file each week.

---

## Setup and Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/mustafa8410/internship-library-microservices-project.git
   cd internship-library-microservices-project
   ```

2. **Build the Project**:
   Run the following Gradle command to build the project:
   ```bash
   ./gradlew build
   ```

3. **Run Each Microservice**:
   Start each service by navigating to its directory and running:
   ```bash
   ./gradlew bootRun
   ```

4. **Database Setup**:
   - Create a PostgreSQL database.
   - Update the database configuration in each microservice's `application.properties` or `application.yml` file.

---



## Contribution
Feel free to fork the repository and submit pull requests to contribute to the project. Suggestions and improvements are always welcome.


