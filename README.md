### Enchanged Console CRUD application v 2.0

#### Used technologies:

##### JDK version 15
##### Liquibase
##### JUnit
##### Mockito

##### Database used:
MySQL

##### API for interacting with the database:
JDBC

##### To launch the application, type in the command prompt:

mvn liquibase:update

javac -sourcepath ./src -d bin src/main/java/com/taorusb/consolecrundusesjdbc/Main.java

java -classpath ./bin com.taorusb.consolecrundusesjdbc.Main

##### Used design patterns:

Chain of responsability, Facade, Singleton

##### Object data is stored in tables:

writers, posts, regions

##### The query command has a similar sql structure (commands are processed regardless of the case of letters).

##### Request structure:

[operation type] [model type] [arguments]

##### Where operation type:

select all, update [model type] where, insert into, delete from [model type] where

##### Where model type:

writers, posts, regions

##### Where arguments:

##### writers:
select - none, update - id= firstname= lastname= regionId= , insert - firstname= lastname= regionId= , delete - id=

##### posts:
select - writerid= , update - id= content= , insert - writerid= content= , delete - id=

##### regions:
select - none , update - id= name= , insert - name= , delete - id=