#Issue Tracker

Issue tracker Application developed using Spring Boot, Spring Jpa Data using In memory Database H2.

#IDE Setup
IntelliJ Idea,
Maven run directly from the wrapper,
JDK11

#API Endpoints
        URL		    METHOD	            REQUEST BODY 	                        RESPONSE 
1. /developer      RetrieveAll          -                      [{"id": 1,"name": "Arbind"},{"id": 2,"name": "New Developer"}]

2. /developer       Create          {"name" : "New Developer"}           {"id": 1,"name": "New Developer"}

3. /developer/{id) RetrieveById      -                                    {"id": 1,"name": "New Developer"}  

#Added Postman collection for all Endpoint API.