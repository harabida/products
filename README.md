# Products API

### Endpoints

| Resource           | POST                  | GET                            | PATCH                                    | PUT | DELETE           |
| ------------------ | --------------------- | ------------------------------ | ---------------------------------------- | --- | ---------------- |
| **/products**      | Create a new products | Retrieve all products          | X                                        | X   |     X            |
| **/products/1**    | X                     | Retrieve details for product 1 | Update details of product 1 if it exists | X   | Remove product 1 |

### Run the app
make sure your docker daemon is up and running and run the following : 

./mvnw spring-boot:run

### Doc 

link to openapi doc : http://localhost:8080/v3/api-docs
link to swagger ui : http://localhost:8080/swagger-ui/index.html

### Hooks
pre-commit : avoid commits on main
