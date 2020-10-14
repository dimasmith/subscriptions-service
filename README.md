# Subscription Service

The API service to store subscriptions to online services.
Allows users to see how much they will spend on online service this month.  

## Build Docker image

The service uses [jib plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) 
to build docker images.

```shell script
mvn compile jib:dockerBuild
```
