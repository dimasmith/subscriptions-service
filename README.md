# Subscription Service

The API service to store subscriptions to online services.
Allows users to see how much they will spend on online service this month.  

## Build Docker image

The service uses [jib plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) 
to build docker images.

```shell script
mvn compile jib:dockerBuild
```

## See the REST spec

When service starts it exposes generated OpenAPI spec. 
You can access it on the [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml).
You can also access the swagger UI on [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Security

Calls to endpoints must be authenticated. 
Service uses basic authentication to secure access. 
When service starts for the first time it configures a single user with username `admin` and password `secret`.

Calls to swagger-ui and OpenAPI aren't secured.
