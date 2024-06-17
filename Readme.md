Ceci est un repository de test qui a servi a réaliser l'article suivant : 


- https://medium.com/norsys-octogone/adieu-les-controller-avec-spring-data-rest-821420089cda


# Adieu les Controller avec Spring data REST
**Baptiste Thery**  
*norsys-octogone*

**Publié dans norsys-octogone**  
*6 min read*  
*Jun 16, 2021*

---

Spring data rest est une partie du framework Spring data qui permet d’exposer des Web Services basés sur les repository.

## Introduction
Cela fait maintenant plusieurs années que je code avec Spring, et suite à l’avènement des micro services, certaines API exposent du CRUD sans règle métier. Qui n’a pas croisé un micro-service “en couche” qui fait passe-plat vers le repository:

> Pourquoi écrire un “service” et un “controller” s’ils ne font que passe-plat ?

N’ayant pas de bonne réponse dans mon cas d’usage, le controller et le service deviennent, pour moi, du code inutile. J’ai donc cherché à les supprimer. Pour la partie “service”: aucun souci, il suffit de supprimer la classe. Pour la partie “controller”: c’est plus compliqué, puisqu’il sert à déclarer nos routes, c’est là que Spring Data Rest intervient!

## Mise en place d’un projet de test
On commence par importer les bonnes dépendances avec Maven: Java 11 et Spring Boot 2.4.5. Nous ajoutons une base H2 (in memory) et Lombok pour faciliter le développement de notre test.

Ensuite un peu de configuration pour générer la BDD directement depuis les annotations JPA.

## Let’s code !
Ici nous décrivons des maisons avec leurs habitants, leurs adresses et leurs meubles.

Ensuite nous implémentons les repositories correspondants aux entités.

> Note importante: aucun repository n’est mis en place pour les habitants.

## S’équiper correctement
Une bonne façon d’explorer les possibilités offertes par Spring Data Rest est d’utiliser un navigateur HAL. Il suffit d’ajouter la dépendance suivante et d’appeler l’endpoint `http://localhost:8080/browser/index.html`.

## Il ne reste plus qu’à tester
Démarrons notre serveur avec le jeu de données suivant.

### Rechercher de la donnée
Nous retrouvons bien notre liste de maisons avec leurs adresses, les fournitures et les habitants associés. À noter: pour les habitants, comme le repository n’a pas été créé, aucun service n’expose la donnée. Celle-ci se retrouve donc directement dans le JSON de la maison. Pour les autres, l’API fournit un lien vers la donnée dans le plus strict respect des normes HATEOAS.

### Insérer de la donnée
Pour les opérations d’insertion, afin de lier la ressource courante à une autre ressource, si un repository existe, on fournira un “path” vers la ressource cible. Sinon, il faut fournir un JSON correspondant à la structure de l’objet. Nous insérons d’abord une “furniture”.

Ensuite nous pouvons insérer une “house” avec la “furniture” créée précédemment.

Alors `GET houses/5/furnitures` est bien un lien vers les meubles de la maison, on y retrouvera le “sofa” créé précédemment.

## Autres opérations possibles
- **PATCH exemple**: `PATCH /houses/4` permet de modifier de la donnée existante. La donnée peut être définie partiellement.
- **DELETE exemple**: `DELETE /houses/4` permet de supprimer la ressource existante.
- **PUT exemple**: `PUT /houses/4` permet de créer ou de modifier de la data existante qui à la différence du PATCH doit être définie dans sa totalité.

### Quelques méthodes “maison”
Nous allons ajouter à l’un de nos repository une méthode personnalisée construite avec JPA.

Cette méthode est automatiquement exposée sous le “search” de la ressource fournie par le repository. Là où toutes les méthodes sont listées.

Ainsi, on pourra interroger le serveur de cette façon et récupérer la ressource House.

### Tri/pagination
Pour appliquer le tri et la pagination sur une entité, il suffit de modifier la classe étendue par le repository avec `PagingAndSortingRepository<T, ID>`.

Si par exemple, on fait la modification pour la classe `FurnituresRepository`:

Le service est alors défini de la façon suivante, et l’on voit apparaître 3 nouveaux paramètres :

- pagination
- tri

Et si l’on souhaite appliquer le tri sur plus d’une propriété, on utilise plusieurs fois le paramètre `sort`, alors le tri sera appliqué dans l'ordre d'apparition des paramètres dans l'URL.

### Validation
Nous souhaitons maintenant créer un validateur empêchant la sauvegarde d’une maison ayant le nom “test”. D’après la documentation, il faut implémenter un “validator” et générer un bean dans notre contexte d’application avec un nom ayant le formalisme suivant: `[eventName][entityName]Validator`. Ainsi le code suivant devrait fonctionner:

Lors de l’appel à la méthode `supports`, si le retour est `true`, c’est à dire que la classe à valider est `House`, alors la méthode `validate` est exécutée sinon le validator n’est pas pris en compte.

> Note importante: le `@Component("beforeCreateHouseValidator")` doit permettre d’assigner le validator au bon “event”. Cependant, un bug est en cours de traitement sur le projet et empêche ce fonctionnement. Nous allons donc supprimer l’annotation. Plusieurs “workarounds” sont disponibles sur le ticket, nous nous intéressons ici à l’approche “manuelle”.

Il faut ajouter une configuration pour indiquer à Spring lors de quel “event” le validator est exécuté.

Ici notre validator est exécuté avant chaque `create` (HTTP POST).

## Modifier le format de la ressource
Pour ce faire il suffit d’utiliser les “projections”. Une projection est une “vue” particulière de la ressource qui peut être utilisée par le client.

Par exemple, si nous souhaitons avoir une visualisation de `House` avec uniquement le `name` et la liste des `furnitures`. Il faut créer la projection.

> Attention, pour être scanné automatiquement par Spring, les projections doivent être dans le même package que l’entity cible. Sinon il est possible d’enregistrer une projection manuellement.

Ensuite cette “vue” peut être exploitée de cette manière:

> Note importante: dans le cas d’une projection, tous les champs sont directement fournis “inline”. C’est pourquoi les informations de `furnitures` sont fournies directement dans le tableau et non pas au travers d’une référence, même si le repository est exposé.

### Afficher la data d’un fils dont le repository existe
Il est possible de déterminer une projection “par défaut” utilisée uniquement depuis une ressource parente. Par exemple, on peut ajouter l’annotation:
`@RepositoryRestResource(excerptProjection=FurnitureExcerpt.class)` au `FurnituresRepository`. Ainsi, lorsque l’on charge une maison, la donnée de `furniture` est fournie directement. `House` prend alors la forme suivante:

> Note importante: la ressource `GET /furnitures` reste inchangée, il s’agit là d’avoir une “preview” de l’objet depuis les parents.

### Gérer la concurrence
Nous souhaitons maintenant gérer les modifications concurrentes d’une ressource. Cela permet, entre autre, d’empêcher le scénario suivant:
- un utilisateur A accède à une ressource qu’il souhaite modifier,
- un utilisateur B accède à la même ressource,
- l’utilisateur A pousse une modification,
- l’utilisateur B pousse une autre modification.

Alors B écrase les modifications apportées par A sans s’en rendre compte. Pour éviter cet effet, Spring Data Rest propose d’utiliser la “version” d’une ressource au travers d’un header appelé `ETAG`.

Il faut ajouter une version à notre entity:

Suite à cet ajout, un `ETAG` sera fourni dans le header pour chaque `GET`.
Par exemple, `GET /houses/1` fournira `ETAG:1`.
Lors du `PUT` permettant de modifier la maison, on ajoutera le header `If-Match:1`.
Par exemple, `PUT /houses/1` avec header `If-Match:1`.
Suite au `PUT`, le `ETAG` devient 2. Si l’on réitère l’opération en gardant le
`If-Match:1` alors on reçoit une réponse `412 precondition failed`.

De la même façon, on peut utiliser une date lors des `GET` afin de recevoir la donnée uniquement si celle-ci a été modifiée. On ajoute `@LastModifiedDate Date date` dans l’entity, un nouvel header apparaît dans les réponses suite à un `GET`, le `Last-Modified`, que l’on peut utiliser lors d’un `GET` postérieur avec le header `If-Modified-Since:MA-DATE`. Ainsi, si la date fournie est antérieure à `Last-Modified` alors une réponse `200` est retournée avec la ressource, sinon une réponse `304 not modified` est retournée. Cela permet d’optimiser les performances en limitant l’usage du réseau.

## Limitations, où quand et comment remettre des controllers
Dans certains cas, il arrive que les opérations fournies ne correspondent pas aux process fonctionnels, il peut être nécessaire de créer manuellement des “controllers”.

Pour ce faire, il existe plusieurs annotations :

- `@BasePathAwareController` et `@RepositoryRestController` sont utilisées pour créer manuellement des endpoints, en profitant des configurations Spring Data REST du projet.
- `@RestController` (annotations standard REST) crée, par contre, un ensemble parallèle de endpoints avec des options de configuration différentes (mappeurs différents, gestionnaires d’erreurs différents, etc.).

Un petit exemple avec l’annotation `@RepositoryRestController`:

## Conclusion
Spring Data Rest permet d’exposer et manipuler des web services en implémentant uniquement les “repository”. L’outil a de nombreux avantages mais aussi quelques inconvénients, il faut donc l’utiliser dans le bon cas.

### Les plus:
- Allège le code, le rend facile à maintenir
- Fournit un Webservice “standard” qui suit les normes HATEOAS
- Permet de manipuler/modifier simplement le Webservice
- Mise en œuvre simple de la gestion de la concurrence
- Permet facilement l’ajout de règles personnalisées
- Prise en main rapide

### Les moins:
- HATEOAS peut amener de la latence s’il est mal exploité
- HATEOAS peut être complexe à utiliser pour les non initiés
- Le projet est moins connu que Spring Web
- Peu d’intérêt si votre API a de nombreuses règles métiers

Les sources du test sont disponibles ici.
