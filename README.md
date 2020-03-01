# Hi, Welcome to project "The migration"

----
## What is it? 

This is a project created to demonstrate programming skills in Java. Framework - Spring Boot.

---
##This project includes

* REST API
* Persistence layer based on Cassandra
* DI
* Unit test coverage (only business logic)
* DocStrings documentations
* Postman collection to make using the project easier


---
##Dependencies:
1. Cassandra DB, [link] (http://cassandra.apache.org/)

**That's all!**

---
# Structure of REST API:

The REST API includes a couple of namspaces:

**/migration** - manage migration logic

**/workload** - manage workloads entities

By the way, do you use [Postman] (https://www.postman.com/)? 
Let's download a collection from [here] (https://www.getpostman.com/collections/09a26ad29450289d344c)
and play with that easer.


## API description:
### Workload namespace:

**GET /workload** -
List of Workloads stored in DB

Response body: Array of workload entity
    
**GET /workload/{id}** -
Search Workload by Id

Response body: Workload entity
    
**POST /workload** -
Create Workload

Request body: Workload entity

Response body: Workload entity

**PUT /workload/{id}** -
Update Workload

Request body: Workload entity

Response body: Workload entity

**DELETE /workload/{id}** -
Remove Workload by Id

Response body: String message

### Migration namespace:
    
**POST /migration** -
Create a migration from bind model and save to DB
    
Request body: MigrationBind
    
Response body: Migration
    
**GET /migration/{id}** - 
Search the migration by Id

Response body: Migration
    
**GET /migration** - Returns list of migrations

Response body: Array of Migration models
    
**GET /migration/run/{id}** - 
    Run the migration process in background thread
    
Response body: String message

**PUT /migration/{id}** - 
Update the migration from MigrationBind

Request body: MigrationBind

Response body: Migration

**DELETE /migration/{id}** - 
Remove the migration by id
   
Response body: String message
    
# TODO
Errors:
Examples:

---
## Models

**Workload model**

    {
        "id": "xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx",
        "ipAddress": "192.168.0.1",
        "credentials": {
		"username": "User",
		"password": "pass",
		"domain": "/"
        },
        "volumeList": [{
		"mountPoint": "c:",
		"totalSize": 1777
        }]
    }
**Migration Bind model**

    {
        "sourceWorkloadIp": "192.168.0.1",
        "targetWorkloadIp": "192.168.0.2",
        "targetCloud": "AWS",
        "cloudCredentials": {
		"password": "pass",
        	"domain": "/",
        	"useName": "User"
        },
        "mountPoints": ["C:"],
        "osType": "WINDOWS"
    }

**Migration model**

    {
        "id": "0f8d0378-e64c-407a-884a-d52894a1dace",
        "selectedMountPoints": [
           "c:"
        ],
        "sourceId": "4888b40d-709a-446f-a589-bdcdf2b2703a",
        "source": {
            "id": "4888b40d-709a-446f-a589-bdcdf2b2703a",
            "ipAddress": "192.168.0.3",
            "credentials": {
                "password": "pass",
                "domain": "/",
                "useName": "User"
            },
            "volumeList": [{
                "mountPoint": "c:",
                "totalSize": 1777
            }]
        },
        "targetCloud": {
            "targetCloud": "AWS",
            "cloudCredentials": {
                "password": "pass",
                "domain": "/",
                "useName": "User"
            },
            "targetId": "3379710c-11a4-41f3-9bea-63d38dd0321b",
            "target": {
               "id": "3379710c-11a4-41f3-9bea-63d38dd0321b",
               "ipAddress": "192.168.0.2",
               "credentials": {
                   "password": "pass",
                   "domain": "/",
                   "useName": "User"
            },
            "volumeList": null
            }
        },
        "state": null,
        "startTime": null,
        "endTime": null,
        "errorMessage": null
    }