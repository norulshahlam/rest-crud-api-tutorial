# REST CRUD API Tutorials - Creating a CRUD application using REST API
## Version 1 - Create DB

### Diagram flow

    Database
        |
    Repository -> Model (Entity)
        |
    Service - > Exception Handler
        |
    Controller (Handle request from external)

### Step 1 - Maven dependencies

Jpa - Maps Java objects to relational database tables
Rest repositories
MySql connector - To use MySql for database
Lombok - boilerplate reduction
Devtool - Auto restart on any changes
Validation - To validate user input 
Hibernate Validator - Reference implementation of the validation API.
H2 - For testing custom query
Web - Develop web applications using Tomcat as embedded server. Without this, the app will shutdown once everything is done.
Apache Commons Lang -  host of helper utilities for the java

### Step 2 - Run DB server

We will create DB without having to manually create from RDBMS by utilising Spring JPA. Our table will look something like this:


[![Image](./src/main/resources/sql-table.png "Deploying Spring Boot Apps to AWS using Elastic Beanstalk")](https://ipwithease.com/three-tier-architecture-in-application/)


We will be using MySql for database creation and will be using Docker to run MySql. Lets create an instance of MySql Docker image:

    docker run --detach --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=mydb --env MYSQL_PASSWORD=root --env MYSQL_USER=admin --name localhost --publish 3306:3306 mysql:8.0

    docker run --name postgres-tutorial -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres

### Step 3 - Create entity

Entities in JPA are nothing but POJOs representing data that can be persisted to the database. An entity represents a table stored in a database. Every instance of an entity represents a row in the table. This will be in Employee.java


### Step 4 - Configure datasource

This is to configure how you connect to database. This configuration can be defined in application.properties. Make the the properties is consistent with the variables defined during creation of database server.

  server.port=9090

  spring.datasource.url=jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=UTC
  spring.datasource.password=root
  spring.datasource.username=admin

  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
  spring.jpa.properties.hibernate.format.sql=true
  spring.jpa.hibernate.ddl-auto=create
  spring.jpa.show-sql=true
  spring.sql.init.mode=always
  server.servlet.context-path=/my-api

  spring.jpa.defer-datasource-initialization: true

### Step 5 - Prepopulate data

We can add values in our table in data.sql in resources folder. This values will be added when Spring starts. In certain scenario you might not able able to populate thru this approach so you have to manually add values thru test cases. 

This test case will be created under repository test folder, for the sake of Project Structure Best Practices. But first we need to create repository, then generate test case through it, run Spring, then run this test.

### Step 6 - Start Spring!

Let check our database (thru docker container) to verify if table is created and data added. Make sure the parameters entered is consistent with the variables used during docker creation.

`Run mysql in cli using docker`  

    docker exec -it localhost bash

`Connect to mysql`  

    mysql -u admin -proot;

`Test`  

    use mydb;  
    show tables;
    desc employee;  
    select * from employee;  

`Stop & remove all running proceses`  

    docker rm $(docker ps -a -q) -f
