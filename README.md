# Pluralsight: Spring Framework: Spring Data MongoDB

### Module 2
* MongoDB dependency
  * groupId: org.springframework.boot
  * artifactId: spring-boot-starter-data-mongodb
    * mongodb-driver
      * mongodb-driver:bson
      * mongodb-driver:driver-core
    * spring-data-mongodb
      * spring-data-commons
* Mongo Data Annotatioins
  * @Document(collection=".."): on a class. similar to @Entity
  * @Id: similar to JPA @Id. Usually stored as an ObjectID type or GUID
  * @Field(name=".."): similar to @Document but on property level.
  * @Transient: exclude a property from persist
  * @Indexed(direction=.., unique=true/false: on the field that you want to Index. Apply on the field that you query frequently
  * @TextIndexed: If the field is in a full Text search
  * @CompoundIndex(def="{'field 1', 'field2'...}): on collection level
  * @DbRef: link multiple @Document together. 
* Mongo Connection Properties: IP, port, database name and credentials(optional)
```
spring.data.mongodb.uri=mongodb://localhost:27017/airportmanagement

java -jar ./my-app.jar --spring.data.mongodb.uri=mongodb://localhost:27017/airportmanagement

```
### Module 3
* Fetching Data with MongoTemplate
  * The query definition with filters, sort etc
    * Query structrure: 
     * Query.query(Criteria.) 
       * new Criteria().orOperation(Criteria...., Criteria...))
       * traverse the object graph in the query: Criteria.where("engine.needsMaintenance").is(true)
     * Query.query(Criteria...).with(Sort.by(Sort.Direction.ASC, "...")).with(PageRequest.of(1.20))
    ```
      Query byAircraft = Query.query(Criteria.where("aircraft.model").is(aircraft));
      
      Query byCity = Query.query(
                new Criteria()
                        .orOperator(
                                Criteria.where("departure").is(city),
                                Criteria.where("destination").is(city))
                        .andOperator(
                                Criteria.where("isDelayed").is(false)
                        )
                        
    ``` 
  * Outcome of the query
  * Class type that contains Mongo annotations
  * MongoTemplate has many find methods
    * findAll(Person.class)
    * find(q, Person.class)
    * findOne(q, Person.class)
    * count(q, Person.class)
  
* Text Indexes
  * Work on properties of type string or arrays of strings
  * You can text index properties across the whole object graph
  * Pay attention to weights as they may change the order or relevance of a found document
  * Each document is scanned, A score is computed internally based on the text index weights, results are sorted by this score
* Full Text Search
  * Query is constructed differently
    * TextCriteria
    * Query byFreeText = TextQueryt.queryText(textCriteria)


  


