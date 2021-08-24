# Getting Started

### Based on

Youtube channel: [Devdojo](https://www.youtube.com/c/DevDojoBrasil)  
Playlist: [Spring WebFlux Essentials - APIs Reativas e escaláveis com Spring WebFlux](https://www.youtube.com/playlist?list=PL62G310vn6nH5Tgcp5q2a1xCb6CsZJAi7)

### To run  
- Run needed infra (Postgres SQL) with docker-compose 
    - `docker-compose -f ./docker-compose.yml up -d`
- Create schema and tables (SQL in next session)
    - **schema**: `anime`
    - **tables**: `anime`
- Run application class as java application

#### SQL for anime schema/table creation:

```
CREATE schema if not exists anime AUTHORIZATION root;

CREATE TABLE if not exists anime.anime (
	id serial NOT NULL,
	name varchar NOT NULL,
	CONSTRAINT anime_pk PRIMARY KEY (id)
);
```

### OpenAPI Access

Running application, access http://localhost:8080/swagger-ui.html

### Pending topics
- Database migration

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#using-boot-devtools)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/2.5.3/reference/html/spring-boot-features.html#boot-features-r2dbc)

### Guides
The following guides illustrate how to use some features concretely:

* [Acessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)

### Additional Links
These additional references should also help you:

* [R2DBC Homepage](https://r2dbc.io)

## Missing R2DBC Driver

Make sure to include a [R2DBC Driver](https://r2dbc.io/drivers/) to connect to your database.
