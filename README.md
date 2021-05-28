# spring-boot-starter-specification-builder
[![Maven Central](https://img.shields.io/maven-central/v/com.github.mikesafonov/spring-boot-starter-specification-builder.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.mikesafonov%22%20AND%20a:%22spring-boot-starter-specification-builder%22)
[![codecov](https://codecov.io/gh/MikeSafonov/spring-boot-starter-specification-builder/branch/master/graph/badge.svg)](https://codecov.io/gh/MikeSafonov/spring-boot-starter-specification-builder)
[![Travis-CI](https://travis-ci.com/MikeSafonov/spring-boot-starter-specification-builder.svg?branch=master)](https://travis-ci.com/MikeSafonov/spring-boot-starter-specification-builder)
[![Conventional Commits](https://img.shields.io/badge/Conventional%20Commits-1.0.0-yellow.svg)](https://conventionalcommits.org)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=alert_status)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=security_rating)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=bugs)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=code_smells)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)

[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=ncloc)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=MikeSafonov_spring-boot-starter-specification-builder&metric=sqale_index)](https://sonarcloud.io/dashboard?id=MikeSafonov_spring-boot-starter-specification-builder)

This is a spring Boot starter for building specifications in declarative way.

The starter is available at `maven central` repository.

Using `gradle`: 
    
    dependencies {
        implementation 'com.github.mikesafonov:spring-boot-starter-specification-builder:version'
    }

Using `maven`:

    <dependency>
      <groupId>com.github.mikesafonov</groupId>
      <artifactId>spring-boot-starter-specification-builder</artifactId>
      <version>version</version>
    </dependency>

## Usage

Only one bean ([SpecificationBuilder](https://github.com/MikeSafonov/spring-boot-starter-specification-builder/blob/master/src/main/java/com/github/mikesafonov/specification/builder/starter/SpecificationBuilder.java)) is created automatically by this starter.

In all of the examples below, it is assumed that we have the following classes:

Entity:
```java
@Entity
@Table(name = "my_entity")
public class MyEntity {
    ...
}
```

Filter:
```java
public class MyEntityFilter {
    ...
}
```

Repository:
```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Integer>, JpaSpecificationExecutor<MyEntity> {
}
```

Service which uses MyEntityRepository and `SpecificationBuilder` 

```java
public class MyEntityService {
    ...
    public List<MyEntity> findByFilter(MyEntityFilter filter){
        return myEntityRepository.findAll(specificationBuilder.buildSpecification(filter));
    }   
}
```

### Get all entities with specific field `is equals` to filters value

The following code example demonstrates how to find all entities by filter`s value:

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    private String name;
}
```

If there is no annotation on the filters field then `SpecificationBuilder` create `equals` predicate.

### Different `name` of column in filter

The following code example demonstrates how to link filters field with entity`s field:  

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    @Name(value = "name")
    private String filterByName;
}
```

### Filter by several columns by one expression

The following code example demonstrates how to filter by several columns by one expression:

Entity:
```java
@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "cost_from")
    private int costFrom;
    @Column(name = "cost_to")
    private int costTo;
    @ManyToOne
    @JoinColumn(name = "id_model")
    private CarModel model;
}
```

Filter:
```java
@Data
public class CostGreaterThenCarFilter {
    @GreaterThan
    @Names(value = {"costFrom", "costTo"}, type = Names.SearchType.AND)
    private Integer value;
}
```

### Ignore specific field in filter

The following code example demonstrates how to ignore specific field in filter:

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    @Ignore
    private String name;
    @Name(value = "name")
    private String filterByName;
}
```
In this example `SpecificationBuilder` create `equals` predicate only by `filterByName` field.

### Get all entities with specific field `is not null`

The following code example demonstrates how to find all entities with specific field is not null:

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    @NonNull
    @Name(value = "name")
    private String filterByName;
}
```

### Get all entities with specific field `is null`

The following code example demonstrates how to find all entities with specific field is null:

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    @IsNull
    @Name(value = "name")
    private String filterByName;
}
```

### Get all entities with specific field `greater than`/`greater than or equals`/`less than`/`less than or equals` filters value

The following code example demonstrates how to use `@GreaterThan`, `@GreaterThanEqual`, `@LessThan` and `@LessThanEqual` annotations:

Filter:
```java
public class CarFilter {
    @GreaterThan
    @Name(value = "size")
    private Double filterSize;
    @GreaterThanEqual
    @Name(value = "size2")
    private Double filterSize2;
    @LessThan
    @Name(value = "size3")
    private Double filterSize3;
    @LessThanEqual
    @Name(value = "size4")
    private Double filterSize4;
}
```

### `Like` predicate

`Like` predicate works only with `String` values.

The following code example demonstrates how to find all entities with specific field is `like` to filters value

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    @Like
    @Name(value = "size")
    private String likeName;
}
```

By default `@Like` ignores case. If you want to search by case sensitive values use `caseSensitive` property:

```java
public class CarFilter {
    @Like(caseSensitive = true)
    @Name(value = "size")
    private String likeName;
}
```

By default `@Like` search by `full` like (%value%). If you want to search by `left` like or `right` use `direction` property:

```java
public class CarFilter {
    @Like(direction = Like.DIRECTION.LEFT)
    @Name(value = "size")
    private String likeName;
}
```

```java
public class CarFilter {
    @Like(direction = Like.DIRECTION.RIGHT)
    @Name(value = "size")
    private String likeName;
}
```

### Using `Join`

The following code example demonstrates how to find all entities with specific field is `equal` to filters value, joined by another field 

Entities:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

```java
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_model")
    private CarModel model;
}
```

Filter:
```java
public class CarFilter {
    @Join(value = "model")
    @Name(value = "name")
    private String model;

    @Join(value = "model")
    @Name(value = "id")
    @GreaterThan
    private Integer modelId;
}
```

`Join` is `Repeatable` annotation so you can join multiple entities.

### Collections in filter

The following code example demonstrates how to find all entities with specific field `contains in` filters value:

Entity:
 ```java
 @Entity
 @Table(name = "car_models")
 public class CarModel {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;
     @Column(name = "name")
     private String name;
 }
 ```
 
Filter:
 ```java
 public class CarFilter {
     @Name(value = "name")
     private Collection<String> names;
 }
 ```

### Filter by `many-to-many` linked tables

The following code example demonstrates how to find all entities with specific many-to-many joined field `contains in` filters value:

Entity:
```java
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_classes",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_class"))
    private Set<ClassEntity> classEntities;
}
```  
```java
@Entity
@Table(name = "classes")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:

```java
public class StudentFilter {
    @ManyToManyCollection
    @Join(value = "classEntities")
    @Name(value = "name")
    private List<String> classes;
}
```

### Filter by segment intersection

The following code example demonstrate how to find entities by segment bounded by fields like `start` and `end`
and intersect with segment from filter.

Entity:
```java
@Entity
@Table(name = "students")
public class StudentEntity {
    @Column(name = "date_start_studying")
    private LocalDate studyingDateStart;

    @Column(name = "date_end_studying")
    private LocalDate studyingDateEnd;
}
```

Filter:

```java
public class StudentStudyingFilter {
    @SegmentIntersection(fromField = "studyingDateStart", toField = "studyingDateEnd")
    private SegmentFilter<LocalDate> periodFilter;
    
    public StudentStudyingFilter(LocalDate from, LocalDate to) {
        this.periodFilter = new SegmentFilter<>(from, to);
    }
}
```

`SegmentFilter` - special type from `com.github.mikesafonov.specification.builder.starter.type` 
containing temporal `from` and `to`.

You can use `@Join` annotation with `@SegmentIntersection` to refer to referenced entity.

Segment intersection predicate allow `null` values in `from` and `to` inside `SegmentFilter`,
and `null` value in entity `toField` what mean not ended segment.

### `Not` predicate

`Not` predicate negates another predicate on field.

The following code example demonstrates how to find all entities exclude specific value in field.

Entity:
```java
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
```

Filter:
```java
public class CarFilter {
    @Not
    private String name;
}
```
### Use `Distinct`

Specify whether duplicate query results will be eliminated.


Entities:
```java
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private List<ContractEntity> contracts = new ArrayList<>();
}
```

```java
@Entity
@Table(name = "contracts")
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String number;

    @Column(name = "id_client")
    private Long idClient;
}
```

Filter:
```java
@Distinct
public class ClientContractNumberFilter {

    @Like
    @Join("contracts")
    @Name("number")
    private String contract;
}
```

## Debug

You may turn on additional logging to debug created predicates:

    logging:
      level:
        com:
          github:
            mikesafonov:
              specification:
                builder:
                  starter: trace

## Build

### Build from source

You can build application using following command:

    ./gradlew clean build -x signArchives
    
#### Requirements:

JDK >= 1.8

### Unit tests

You can run unit tests using following command:

    ./gradlew test

## Contributing

Feel free to contribute. 
New feature proposals and bug fixes should be submitted as GitHub pull requests. 
Fork the repository on GitHub, prepare your change on your forked copy, and submit a pull request.

**IMPORTANT!**
>Before contributing please read about [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.2/) / [Conventional Commits RU](https://www.conventionalcommits.org/ru/v1.0.0-beta.2/)
