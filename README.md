# REST CRUD API Tutorials - Creating a CRUD application using REST API

## Version 6 - [Swagger](https://dzone.com/articles/spring-boot-restful-api-documentation-with-swagger) & [Scheduler](https://www.baeldung.com/spring-scheduled-tasks) 

### Scheduler

Scheduling is a process of executing the tasks for the specific time period. Spring Boot provides a good support to write a scheduler on the Spring applications.

- @Configuration & @EnableScheduling

Use this 2 annotations on the class where the scheduled method in running on.

- @Scheduled

Use this on the method where the scheduler is running. on top of that, you can set the interval using cron or time units eg

@Scheduled(fixedDelay = 1000)
@Scheduled(fixedRate = 1000)
@Scheduled(fixedDelay = 1000, initialDelay = 1000)
@Scheduled(cron = "0 15 10 15 * ?")
@Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")

- Cron Expression

It is always an advantage to know what your cron expression value is by using CronParser

	<!-- https://mvnrepository.com/artifact/net.redhogs.cronparser/cron-parser-spring -->
    <dependency>
        <groupId>net.redhogs.cronparser</groupId>
        <artifactId>cron-parser-spring</artifactId>
        <version>3.5</version>
    </dependency>

Method name: 

    static net.redhogs.cronparser.CronExpressionDescriptor
        .getDescription(0 15 10 15 * ?);


### Swagger2 

An open source project used to generate the REST API documents for RESTful web services. It provides a user interface to access our RESTful web services via the web browser.

To enable the Swagger2 in Spring Boot application, you need to add the following dependencies in our build configurations file:


    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.9.2</version>
    </dependency>

    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>3.0.0</version>
    </dependency>

`Swagger config`

You just need to have one class for this config and you will do all the necessary configurations there. Click [here](https://stackoverflow.com/questions/70043841/swagger-2-issue-spring-boot) for any issues faced.
    

`application.properties`

    spring.mvc.pathmatch.matching-strategy=ant-path-matcher

`What to implement?`

Docket - Your configurations and useful information
- Api information like name, contact, versrion
- Request header like application/json, request id, request datme/time

@ApiResonses
- Describes a possible response of an operation. 
- Defined at controller level or application level

@ApiOperation
- Describes an operation or typically a HTTP method against a specific path
- Defined on every controller method

@ApiParam
- Describes a parameters of an API resource request
- You give a sample value on the request parameter
- Defined on every controller method

@ApiModelProperty
- Define your model description (value), name, data type, example values on your model


`Accessing Swagger`

You can access it in JSON-based or UI-based. These two have their default url. You can, of course customize this.

JSON-based

    http://localhost:9090/v2/api-docs

UI-based

    http://localhost:9090/swagger-ui.html

## Version 5 - Unit Testing

`Introduction`


Unit test refers to the test of the most basic parts of an app -> A Unit. For REST application, we create test cases starting from Repository layer, then Service layer, then Controller where the test focus on integrating different layers of the application.

`Code Coverage`

Code coverage describes the percentage of code covered by automated tests. in Eclipse we use [(EclEmma)](https://www.eclemma.org/) which is a free Java code coverage tool for Eclipse. Coverage is measured by percentage. Especially when working in enterprise, we must achieve atleast 50% total coverage

![Image](./src/main/resources/code-coverage.JPG "Deploying Spring Boot Apps to AWS using Elastic Beanstalk")

To achieve a high % coverage, we need to test elements that has highest number of instruction. Also, to cover your service class is highest priority. 

`Code Quality`

[(SonarLint)](https://www.sonarlint.org/) is a Free and Open Source IDE extension that identifies and helps you fix quality and security issues as you code. Like a spell checker, SonarLint squiggles flaws and provides real-time feedback and clear remediation guidance to deliver clean code from the get-go.


`Dependencies for testing`


    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.hamcrest</groupId>
                <artifactId>hamcrest-core</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest-library</artifactId>
        <scope>test</scope>
    </dependency>   

also add dependency for h2:
    
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>


### Unit Test - Repository

`Diagram`

[![Image](./src/main/resources/unit-test-repository.JPG "Deploying Spring Boot Apps to AWS using Elastic Beanstalk")](https://ipwithease.com/three-tier-architecture-in-application/)

- In Repository, we dont need to test build-in methods of JPA. Only test your custom methods [(Explanation)](https://youtu.be/Geq60OVyBPg?t=2422)

- Since we dont have one, lets create one [(using @Query)](https://stackoverflow.com/questions/58453768/variables-in-spring-data-jpa-native-query)

- This query will count number of country in employee table

- The result will have custom fields [(using projection)](https://stackoverflow.com/questions/46083329/no-converter-found-capable-of-converting-from-type-to-type) 

Run http to test:  

Number of each country

    http://localhost:9090/crud-api/employees/count/country

Number of a country

    http://localhost:9090/crud-api/employees/count/singapore


`H2 database`

To test repository, we can run the query against H2 database simply we dont want to store the data during testing. This can be easily done by copy-paste our main application.properties into the test folder and change the db url from mysql to h2. Schema and data will be loaded from the main resources

Now lets create a unit test for this!


`Create test case`

Simply right-click on the repo file -> new -> Junit. This will automatically generate test method. We will implement our test cases. 


### Unit Test - Service layer

Hardest unit to test.

`Using Mock`

Since our repo is tested and works fine, we dont need to test the service class against repo but instead we will mock it. Basically we don't want to test the real repository when we are testing the service because we know that repository is tested and it works. So we can just mock its implementation inside of the service test.
The benefit that we get is that our unit test is now testing is fast as we don't have to bring up the database, create table, insert a new student, drop the database, and all of that stuff that you've seen when we tested the repository which we've done earlier. Therefore anywhere that we use the repository we just `mock` it. 

[![Image](./src/main/resources/unit-test-service.JPG "Deploying Spring Boot Apps to AWS using Elastic Beanstalk")](https://www.tutorialspoint.com/mockito/mockito_junit_integration.htm)

Besides mocking the repository, we can mock basically anything and define what it reutrn, making our work easier and faster [(more info)](https://visitmehere.wordpress.com/2019/06/07/mock-an-arraylist/). We also implement @InjectMocks simply because Service layer need Repository layer [(more info)](https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks).

`IMPORTANT NOTE`

You dont need to create any real objects at all. Just create mock of any instance, method, class, anything. The goal of testing the service is to detach any real object as much as possible!


### Unit Test - Controller layer

Unlike the Service layeer where we can mock everything, here we need to use real object for the response. From there we will use JSONPath to match certain fields in your result set. If you are not familiar with it, you can use  [(JSONPath Online Evaluator)](https://jsonpath.com/) to play around with the expressions.



















**********************************************************************

## Version 4 - Global exception - @ControllerAdvice


`Intro`

    During the software development process, it is inevitable to handle all kinds of exceptions. For me, at least half of the time is spent dealing with all kinds of exceptions, so there will be a lot of try {...} catch {...} finally {...} code blocks in the code, which not only has a lot of redundant code, but also affects the readability of the code.


`So what is it?`

    Spring consider exception handling a cross-cutting concern, thus it allows you to handle exceptions separately from the rest of your code. This approach truly does work great with Spring!

    Used for global error handling in the Spring MVC application.It also has full control over the body of the response and the status code.


`Types?`

There are 2 types:

1. Custom exception - where u throw yourself if it meets your condition and use GlobalExceptionHandler to handle [HERE](https://stackoverflow.com/questions/67090406/throw-custom-exception-with-spring-data-rest)

        
2. Global exception - where it throwas itself and u handle it using GlobalExceptionHandler


`Benefits?`

    No cluttering of your code surrounding with try-catch blocks
    u can have more meaningful error message

Previously we use try catch to handle exception. Now lets refactor our code by removing all try-catch blocks and use global exception handler!


`Maven packaging issues`

If you have issue packaging to jar, click [HERE](https://stackoverflow.com/questions/35394885/lombok-not-compiling-in-maven)

`Objectives`

1. Create global exception handler to handle expected errorr
2. Create custom exception handler to handle our own error
3. Create 1 generic error to handle other unexpected errors

### ******************************************* ###






/////////////////////////////////////////////////////////////////////////
************************************************************************




## Version 3 - CRUD operations

This were the main function is. We will be doing CRUD operations - create, read, update and delete. We will also apply try-catch for expected errors. Before this let us understand the architecture:


`3 tier architecture`

[![Image](./src/main/resources/3-tier-architecture.JPG "Deploying Spring Boot Apps to AWS using Elastic Beanstalk")](https://ipwithease.com/three-tier-architecture-in-application/)

`Layered Architecture`  

[![Image](./src/main/resources/3-layered-architecture.JPG "Deploying Spring Boot Apps to AWS using Elastic Beanstalk")](https://medium.com/java-vault/layered-architecture-b2f4ebe8d587)


### Rest Controller

- Implement controller to receive requests and use ResponseEntity() to return different status codes
- Implement validation such as min character, unique value, 
- Add validation dependency - javax-validation [HERE](https://www.baeldung.com/javax-validation)
- Validate using @Valid in controller
- Retrieve validation message [HERE](https://stackoverflow.com/questions/2751603/how-to-get-error-text-in-controller-from-bindingresult)
- Diff btwn javax.persistence & javax.validation and how to handle error from each validation [HERE](https://reflectoring.io/bean-validation-with-spring-boot/)
- Create mock data from [HERE](https://www.mockaroo.com/) 

### Patch method

- How to map random fields [HERE](https://newbedev.com/spring-rest-partial-update-with-patch-method) 

- How to validate patch method using ValidatorFactory
[HERE](https://stackoverflow.com/questions/56139024/how-to-automatically-add-bean-validation-when-partially-updating-patch-spring-bo) 

- Know that entity having camelCase will mapped into db into under_score eg:

    In Entity: birthDate -> birth_date in Database

- Use exception to throw validation error by means of try-catch

- Implement more fields in Employee to learn pagination

- Structuring Your Code [HERE](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code)  

       
This branch we have made a few bad practics:
- How messy the response object is,
- Messy try-catch blocks
- We didnt handle all related exception, and it is hard to handle all exception as the code will get cluttered

We also assume that this is web-service where user can enter any field and value so a lot of validation is needed. Know that most of the time it will be web application so most validation will be done in front-end. Something to take note of.

Next lesson we will use @ControllerAdvice to handle ALL exception. This will result in cleaner and manageable code. See you next lesson!






/////////////////////////////////////////////////////////////////////////
************************************************************************






## Version 2 - Customize json property

The Jackson JSON toolkit contains a set of Java annotations which you can use to influence how JSON is read into objects, or what JSON is generated from the objects. Click  [HERE](http://tutorials.jenkov.com/java-json/jackson-annotations.html) for more information.

 
### Arrange your order of json properties

Currently the id is at the bottom. we can bring this up by adding this at class level:  

    @JsonPropertyOrder({"firstName","lastName"})
    
From this example, firstName will be at the most top followed by lastName

### Hide json property

You can hide certain property of json. let us hide lastName by this annotation in entity:

    @JsonIgnore
    private String lastName;

### Creation timestamp

Marks a property as the creation timestamp of the containing entity. The property value will be set to the current VM date exactly once when saving the owning entity for the first time.

    @CreationTimestamp
    private Date createdAt;

### Rename json property

You can rename your json property name instead of using the vdefault value based on variable name

    @JsonProperty("MyAwesomeFirstName")
    private String firstName;









///////////////////////////////////////////////////////////////////////////
**************************************************************************


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
  server.servlet.context-path=/crud-api

  spring.jpa.defer-datasource-initialization: true

### Step 5 - Prepopulate data

We can add values in our table in data.sql in resources folder. This values will be added when Spring starts. In certain scenario you might not able able to populate thru this approach so you have to manually add values thru test cases. 

This test case will be created under repository test folder, for the sake of Project Structure Best Practices. But first we need to create repository, then generate test case through it, run Spring, then run this test.

### Step 6 - Start Spring!

Start Spring. Ensure there is no error,

### Step 7 - Verify database

Once Spring starts, let's check our database (thru docker container) to verify if table is created and data added. Make sure the parameters entered is consistent with the variables used during docker creation.

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

### Step 8 - Verify in Postman / Web browser

  Let's make a controller and sent request to view our pre-populated data.