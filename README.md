# movie-management

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/movie-management-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### Endpoints:
#### GET /movies/listAll
```shell script
Description: Retrieves a list of all movies available in the database.
Example call:
curl -X GET http://localhost:8080/movies/listAll
```
#### GET /movies/list/page={page}/size={size}
```shell script
Description: Retrieves a paginated list of movies. Specify the page number and the number of movies per page.
Example call:
curl -X GET "http://localhost:8080/movies/list/page=1/size=10"
```
#### GET /movies/search?title={title}
```shell script
Description: Searches for movies by title. Use the `title` query parameter to filter results.
Example call:
curl -X GET "http://localhost:8080/movies/search?title=Spiderman"
```
#### GET /movies/get/{imdbID}
```shell script
Description: Retrieves a specific movie based on its IMDb ID. Replace `{imdbID}` with the actual IMDb ID of the movie.
Example call:
curl -X GET http://localhost:8080/movies/get/tt0050083 
```
#### POST /movies/create
```shell script
Description: Creates a new movie entry in the database. Provide the movie details in the request body.
Request Body:
{
    "imdbID": String,
    "title": String,
    "yearCreated": Integer,
    "genre": String(Enum),
    "description": String,
    "pictures": String array
    "actorIds": Long array
}
Example call:
curl -X POST "http://localhost:8080/movies/create" -H "Content-Type: application/json" -d '{
    "imdbID": "tt0050083",
    "title": "Spiderman",
    "yearCreated": 2000,
    "genre": "ACTION",
    "description": "With Spider-Mans identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear.",
    "pictures": [
        "https://via.placeholder.com/150/0000FF/808080?Text=PAKAINFO.com",
        "https://via.placeholder.com/150/FF0000/FFFFFF?Text=yttags.com"
    ],
    "actorIds": [1, 2, 3]
}'
```
#### PUT /movies/update
```shell script
Description: Updates an existing movie entry in the database. Provide the updated movie details in the request body.
Request Body:
{
    "imdbID": String,
    "title": String,
    "yearCreated": Integer,
    "genre": String(Enum),
    "description": String,
    "pictures": String array
    "actorIds": Long array
}
Example call:
curl -X POST "http://localhost:8080/movies/update" -H "Content-Type: application/json" -d '{
    "imdbID": "tt0050083",
    "title": "Spiderman",
    "yearCreated": 2000,
    "genre": "ACTION",
    "description": "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear.",
    "pictures": [
        "https://via.placeholder.com/150/0000FF/808080?Text=PAKAINFO.com",
        "https://via.placeholder.com/150/FF0000/FFFFFF?Text=yttags.com"
    ],
    "actorIds": [1, 2, 3]
}'
```
#### DELETE /movies/delete/{imdbID}
```shell script
Description: Deletes a movie entry from the database using its IMDb ID.
Example call:
curl -X DELETE "http://localhost:8080/movies/delete/tt0050083"
```


#### GET /actors/listAll
```shell script
Description: Retrieves a list of all actors available in the database.
Example call:
curl -X GET http://localhost:8080/actors/listAll
```
#### GET /actors/list/page={page}/size={size}
```shell script
Description: Retrieves a paginated list of actors. Specify the page number and the number of actors per page.
Example call:
curl -X GET "http://localhost:8080/actors/list/page=1/size=10"
```
#### GET /actors/get/{id}
```shell script
Description: Retrieves a specific actor based on its ID. Replace `{id}` with the actual ID of the actor.
Example call:
curl -X GET http://localhost:8080/actors/get/1 
```
#### POST /actors/create
```shell script
Description: Creates a new actor entry in the database. Provide the actor details in the request body.
Request Body:
{
    "firstName": String,
    "lastName": String,
    "gender": String(Enum),
    "bornDate": Date,
    "movieIds": String array
}
Example call:
curl -X POST "http://localhost:8080/movies/create" -H "Content-Type: application/json" -d '{
    "firstName": "Denzel",
    "lastName": "Washington",
    "gender": "MALE",
    "bornDate": "1954-12-28T00:00:00",
    "movieIds": ["tt0050083", "tt0343243"]
}'
```
#### POST /actors/update
```shell script
Description: Updates an existing actor entry in the database. Provide the updated actor details in the request body.
Request Body:
{
    "id": Long,
    "firstName": String,
    "lastName": String,
    "gender": String(Enum),
    "bornDate": Date,
    "movieIds": String array
}
Example call:
curl -X POST "http://localhost:8080/movies/create" -H "Content-Type: application/json" -d '{
    "id": 1,
    "firstName": "Denzel",
    "lastName": "Washington",
    "gender": "MALE",
    "bornDate": "1954-12-28T00:00:00",
    "movieIds": ["tt0050083", "tt0343243"]
}'
```
#### DELETE /actors/delete/{id}
```shell script
Description: Deletes an actor entry from the database using its ID.
Example call:
curl -X DELETE "http://localhost:8080/actors/delete/tt0050083"
```