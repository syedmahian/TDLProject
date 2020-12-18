# TDLProject

The TDL Project aims to create a Spring Boot To-Do List application. It has the CRUD functionalities for two entities: User and ToDos. The back end is programmed using Java, while the front-end was programmed using HTML, CSS and Javascript. SQL was used as the database running from localhost. Please use the .jar (TDLProject.jar) to run the program from command line. The localhost is 9092.

# Repository details
This git repository holds both the front-end and back-end of my project. The front-end folders are located in src/main/resources/static.

The documentation folder holds all the documentation used in the planning and execution of the project.

# Jira Project Management Board:
[Project Management Board](https://sdet2020oct.atlassian.net/secure/RapidBoard.jspa?projectKey=TDL2020&useStoredSettings=true&rapidView=6&atlOrigin=eyJpIjoiZjk2ODg4Yjc3OGE2NGM3OWI3MzVkODczNTdlYzE2M2QiLCJwIjoiaiJ9)



# Prerequisites
To get the project running you must install java into your computer as well as maven.

Install Java (jdk14): https://docs.oracle.com/en/java/javase/12/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA
Install for Maven: https://mkyong.com/maven/how-to-install-maven-in-windows/ 
The project should run in your web browser, using the port "localhost:8080".

# Running Tests

In order to run tests you need an IDE that supports Java, if you use Spring tools you can right click on your project folder, click on "Coverage as" then "Junit". This will run all the test files, if there are any issues with testing please try updating your maven project and refershing Spring Tools. This was an issue that was faced throughout the project but it can be fixed by updating the maven project.

# Tests
Rest:
ToDoControllerUnitTest.java
UserControllerUnitTest.java
ToDoControllerIntegrationTest.java
UserControllerIntegrationTest.java

# Built With
* Maven
* SpringTool Suit
* Java
* HTML
* CSS
* Javascript
* SQL

