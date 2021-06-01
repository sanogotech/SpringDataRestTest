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
 
## Tri / Pagination

Pour appliquer le tri et la pagination sur une entité, il suffit de modifier la classe étendue par le repository avec `PagingAndSortingRepository<T, ID>`.

Si par exemple, on fait la modification pour la classe `FurnituresRepository` : 

```java
package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.Furniture;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FurnituresRepository extends PagingAndSortingRepository<Furniture, String> {
}
```

Le service est alors défini de la façon suivante, et l'on voit apparaître 3 nouveaux paramètres : 

```json
"furnitures": {
    "href": "http://localhost:8080/furnitures{?page,size,sort}",
	"templated": true
}	
```

Que l'on va utiliser de la manière suivante : 

### Pagination 


```shell
curl --location --request GET 'http://localhost:8080/furnitures/?size=5' \
--header 'Content-Type: application/json'
```

### Tri
```shell
curl --location --request GET 'http://localhost:8080/furnitures/?sort=name,desc' \
--header 'Content-Type: application/json'
```

Et si l'on souhaite appliquer le tri sur plus d'une propriété, on utilise plusieurs fois le paramètre `sort`, le tri sera appliquer dans l'ordre d'apparation des paramètres dans l'URL.

```shell
curl --location --request GET 'http://localhost:8080/furnitures/?sort=name,desc&sort=id' \
--header 'Content-Type: application/json'
```

## Validation

## Mapping

## Les limitations et contournements
Dans certains cas, il  peut arriver que les opérations fournies ne correspondent pas aux process fonctionnels attendus (imaginons par exemple une gestion des commandes), il peut alors être nécessaire de créer manuellement des controllers. 

Pour ce faire, il existe plusieurs annotations :

 - @BasePathAwareController et @RepositoryRestController sont utilisés pour créer manuellement des endpoints, en profitant des configurations Spring Data REST du projet.

 - @RestController (annotations standard REST) créée, par contre un ensemble parallèle de endpoints avec des options de configuration différentes (mappeurs différents, gestionnaires d'erreurs différents, etc.).
 
Un petit exemple avec l'annotation `@RepositoryRestController`


```java
@RepositoryRestController
public class InhabitantController {
	
    private final HouseRepository houseRepository;

    @Autowired
    public InhabitantController(HouseRepository repo) { 
        houseRepository = repo;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/houses/inhabitants") 
    public @ResponseBody ResponseEntity<?> searchInhabitants(@RequestParam String name) {
        List<House> inhabitants = houseRepository.findHomeByInhabitant(name); 

        CollectionModel<House> resources = CollectionModel.of(inhabitants); 

        resources.add(linkTo(methodOn(InhabitantController.class).searchInhabitants(name)).withSelfRel()); 

        return ResponseEntity.ok(resources); 
    }

}
```
