#Spring Data REST

## Notre API REST

L'API permet de récupérer des habitations avec ses occupants et le mobilier qui la compose

Le projet utilise : 
  - Spring Boot
  - Spring Data JPA
  - Spring Data REST
 
Plusieurs endpoints existent : 
 - *GET, POST* `/houses` 
 - *PUT, PATCH, DELETE* `/houses/{id}` 
 - *GET, POST* `/furnitures`
 - *PUT, PATCH, DELETE* `/furnitures/{id}`
 - *GET, POST* `/addresses`
 - *PUT, PATCH, DELETE* `/addresses/{id}`

## Quick Start
 
 Depuis la ligne de commande
 
 ```shell
 git clone https://git.norsys.fr/norsys-nrn/blog/spring-data-rest.git
 cd spring-data-rest
 mvn clean install
 mvn spring-boot:run
 ```
 
 Pour visualiser l'ensemble des endpoints disponibles, ouvrez simplement votre naviguateur à l'adresse [http://localhost:8080/](http://localhost:8080/)
 
 TODO - complete
