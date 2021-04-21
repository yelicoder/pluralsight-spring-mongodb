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

  


