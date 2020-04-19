# The migration

This is a sample project created to demonstrate programming skills in Java. Framework - Spring Boot.

## The Project includes

* REST API
* Persistence layer based on Cassandra
* DI
* Unit test coverage (only business logic)
* DocStrings documentations
* Postman collection to make using the project easier


## Dependencies:
1. Cassandra DB, [link](http://cassandra.apache.org/)
2. Java 11
3. Web server (optional)

**That's all**

# Structure of REST API:

The REST API includes a couple of namspaces:

**/migration** - manage migration logic

**/workload** - manage workloads entities

By the way, do you use [Postman](https://www.postman.com/)? 
Let's download a Postman collection from [here](https://drive.google.com/open?id=1_ZtkTM4GN_wXyeXKvsF4y73cJaG-0EC8)
and play with REST API easer.


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
**Error model**

All ***expectable*** errors like 400 Bad Requst or 404 Not Found have a predictable response body. This greatly simplifies the process of integration with the server.

    { 
   	"message": "Something went wrong"
    }


## Examples:

First let's create a couple of workloads with IP's ***192.168.0.1*** and ***192.168.0.2*** (source and target Workloads) and a couple of volumes in the source.

Make a REST call ***POST /workload*** with body for source

    {        
        "ipAddress": "192.168.0.1",
        "credentials": {
		"username": "User",
		"password": "pass",
		"domain": "/"
        },
        "volumeList": [{
		"mountPoint": "c:",
		"totalSize": 1777
        }, {
		"mountPoint": "d:",
		"totalSize": 2000
        }]
    }
    
and let's make a REST call for target:

    {        
       "ipAddress": "192.168.0.2",
       "credentials": {
                "username": "User",
                "password": "pass",
                "domain": "/"
        },
        "volumeList": []
    }
    
Second step is create a migration based on those workloads. Make a REST call to ***POST /migration*** with body    

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

In this case only the **C:** drive is selected, that means migration will copy to target Workload **C:** drive only from the source.

Third let's start the migration by Id, take a migration's Id and call the method: ***GET /migration/run/{migrationId}***

At this point the migration started and you are able to check the migration logs in STD output or just make a REST call to ***GET /migration/{migrationId}*** and ckeck the status.

So, that's all

**Created by Artyom Arabadzhiyan**
