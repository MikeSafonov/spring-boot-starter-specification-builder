package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.base.cars.*;
import com.github.mikesafonov.specification.builder.starter.base.clients.ClientContractNumberFilter;
import com.github.mikesafonov.specification.builder.starter.base.clients.ClientEntity;
import com.github.mikesafonov.specification.builder.starter.base.clients.ClientRepository;
import com.github.mikesafonov.specification.builder.starter.base.persons.PersonEntity;
import com.github.mikesafonov.specification.builder.starter.base.persons.PersonNameOrSurnameFilter;
import com.github.mikesafonov.specification.builder.starter.base.persons.PersonRepository;
import com.github.mikesafonov.specification.builder.starter.base.studens.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MikeSafonov
 */

class SpecificationBuilderTest {

    @DataJpaTest
    @Sql("/init.sql")
    @ExtendWith(OutputCaptureExtension.class)
    abstract class BaseTest {

        @Autowired
        CarRepository carRepository;
        @Autowired
        CarModelRepository carModelRepository;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        PersonRepository personRepository;
        @Autowired
        ClientRepository clientRepository;

        SpecificationBuilder specificationBuilder;

        @BeforeEach
        void setUp() {
            specificationBuilder = new SpecificationBuilder();
        }
    }


    @Nested
    class Equals extends BaseTest {

        @Test
        void shouldFindById() {
            EqualsCarFilter carFilter = new EqualsCarFilter();
            carFilter.setId(1);
            List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(carFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(1);
                assertThat(carEntity.getNumber()).isEqualTo("123");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(1);
                    assertThat(carModel.getName()).isEqualTo("audi");
                });
            });
        }
    }

    @Nested
    class Join extends BaseTest {

        @Test
        void shouldFindByJoin() {
            ModelCarFilter carFilter = new ModelCarFilter();
            carFilter.setModel("audi");
            List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(carFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(1);
                assertThat(carEntity.getNumber()).isEqualTo("123");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(1);
                    assertThat(carModel.getName()).isEqualTo("audi");
                });
            });
        }

        @Test
        void shouldJoinWithTableOnlyOnceWithSeveralJoins(CapturedOutput capturedOutput) {
            ModelTypeCarFilter carFilter = new ModelTypeCarFilter();
            carFilter.setModel("audi");
            carFilter.setType("new");
            List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(carFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(1);
                assertThat(carEntity.getNumber()).isEqualTo("123");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(1);
                    assertThat(carModel.getName()).isEqualTo("audi");
                    assertThat(carModel.getType()).satisfies(carType -> {
                        assertThat(carType.getType()).isEqualTo("new");
                        assertThat(carType.getId()).isEqualTo(1);
                    });
                });
            });

            assertThat(capturedOutput).containsOnlyOnce("inner join car_models");
        }

        @Test
        void shouldJoinWithTableOnlyOnce(CapturedOutput capturedOutput) {
            ModelCarFilter carFilter = new ModelCarFilter();
            carFilter.setModel("audi");
            carFilter.setName("audi");
            List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(carFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(1);
                assertThat(carEntity.getNumber()).isEqualTo("123");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(1);
                    assertThat(carModel.getName()).isEqualTo("audi");
                });
            });

            assertThat(capturedOutput).containsOnlyOnce("inner join car_models");
        }

        @Test
        void shouldJoinWithTableOnlyOnceWithPageable(CapturedOutput capturedOutput) {
            ModelCarFilter carFilter = new ModelCarFilter();
            carFilter.setModel("audi");
            carFilter.setName("audi");
            Pageable pageable = PageRequest.of(0, 1);
            Page<CarEntity> page = carRepository.findAll(specificationBuilder.buildSpecification(carFilter), pageable);
            List<CarEntity> data = page.getContent();
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(1);
                assertThat(carEntity.getNumber()).isEqualTo("123");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(1);
                    assertThat(carModel.getName()).isEqualTo("audi");
                });
            });

            assertThat(capturedOutput)
                .containsOnlyOnce("select carentity0_.id as id1_2_, carentity0_.cost_from as cost_fro2_2_, carentity0_.cost_to as cost_to3_2_, carentity0_.id_model as id_model5_2_, carentity0_.number as number4_2_ from cars carentity0_ inner join car_models carmodel1_ on carentity0_.id_model=carmodel1_.id where carmodel1_.name=? and carmodel1_.name=? limit ?")
                .containsOnlyOnce("select count(carentity0_.id) as col_0_0_ from cars carentity0_ inner join car_models carmodel1_ on carentity0_.id_model=carmodel1_.id where carmodel1_.name=? and carmodel1_.name=?");
        }

        @Test
        void shouldJoinWithTableOnlyOnceWithAndSpecification(CapturedOutput capturedOutput) {
            ModelCarFilter carFilter = new ModelCarFilter();
            carFilter.setModel("audi");
            carFilter.setName("audi");
            Specification specification = specificationBuilder.buildSpecification(carFilter)
                .and((root, q, cb) -> cb.isNotNull(root.get("number")));

            List<CarEntity> data = carRepository.findAll(specification);
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(1);
                assertThat(carEntity.getNumber()).isEqualTo("123");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(1);
                    assertThat(carModel.getName()).isEqualTo("audi");
                });
            });

            assertThat(capturedOutput).containsOnlyOnce("inner join car_models");
        }
    }

    @Nested
    class IsNull extends BaseTest {

        @Test
        void shouldFindByIsNull() {
            IsNullCarModelFilter modelFilter = new IsNullCarModelFilter();
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(3);
                assertThat(carModel.getName()).isNull();
            });
        }
    }

    @Nested
    class NonNull extends BaseTest {

        @Test
        void shouldFindByNonNull() {
            NonNullCarModelFilter modelFilter = new NonNullCarModelFilter();
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(2, data.size());
            assertThat(data.get(0)).satisfies(carModel -> assertThat(carModel.getName()).isNotNull());
            assertThat(data.get(1)).satisfies(carModel -> assertThat(carModel.getName()).isNotNull());
        }
    }

    @Nested
    class GreaterThan extends BaseTest {

        @Test
        void shouldFindByGreaterThan() {
            GreaterCarModelFilter modelFilter = new GreaterCarModelFilter();
            modelFilter.setId(2);
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(3);
                assertThat(carModel.getName()).isNull();
            });
        }
    }

    @Nested
    class GreaterThanEqual extends BaseTest {

        @Test
        void shouldFindByGreaterThanEqual() {
            GreaterCarModelFilter modelFilter = new GreaterCarModelFilter();
            modelFilter.setIdEqual(3);
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(3);
                assertThat(carModel.getName()).isNull();
            });
        }
    }

    @Nested
    class LessThan extends BaseTest {

        @Test
        void shouldFindByLessThan() {
            LessCarModelFilter modelFilter = new LessCarModelFilter();
            modelFilter.setId(2);
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(1);
                assertThat(carModel.getName()).isNotNull();
            });
        }
    }

    @Nested
    class LessThanEqual extends BaseTest {

        @Test
        void shouldFindByLessThanEqual() {
            LessCarModelFilter modelFilter = new LessCarModelFilter();
            modelFilter.setIdEqual(1);
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(1);
                assertThat(carModel.getName()).isNotNull();
            });
        }
    }

    @Nested
    class Ignore extends BaseTest {

        @Test
        void shouldIgnoreField() {
            IgnoreCarModelFilter modelFilter = new IgnoreCarModelFilter();
            modelFilter.setId(1);
            modelFilter.setName("volvo");
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(1);
                assertThat(carModel.getName()).isNotNull();
            });
        }
    }


    @Nested
    class Like extends BaseTest {

        @Test
        void shouldFindByLeftLike() {
            LikeCarModelFilter modelFilter = new LikeCarModelFilter();
            modelFilter.setLeftName("lvo");
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(2);
                assertThat(carModel.getName()).isEqualTo("volvo");
            });
        }

        @Test
        void shouldFindByRightLike() {
            LikeCarModelFilter modelFilter = new LikeCarModelFilter();
            modelFilter.setRightName("vol");
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(2);
                assertThat(carModel.getName()).isEqualTo("volvo");
            });
        }

        @Test
        void shouldFindByAroundLike() {
            LikeCarModelFilter modelFilter = new LikeCarModelFilter();
            modelFilter.setAroundName("olv");
            List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getId()).isEqualTo(2);
                assertThat(carModel.getName()).isEqualTo("volvo");
            });
        }

        @Test
        void shouldLikeByCastedField() {
            CostToLikeFilter filter = new CostToLikeFilter();
            filter.setKey("20");

            List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(filter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carModel -> {
                assertThat(carModel.getCostTo()).isEqualTo(20);
            });
        }
    }

    @Nested
    class Collection extends BaseTest {

        @Test
        void shouldFindByCollection() {
            CollectionStudentModelFilter filter = new CollectionStudentModelFilter();
            filter.setNames(Arrays.asList("Student 1", "Student 3"));

            List<StudentEntity> students = studentRepository.findAll(specificationBuilder.buildSpecification(filter));

            assertThat(students).extracting("name", String.class).containsExactly("Student 1", "Student 3");
        }

        @Test
        void shouldFindManyToManyCollection() {
            List<String> classes = new ArrayList<>();
            classes.add("Class 3");
            classes.add("Class 2");

            StudentFilter studentFilter = new StudentFilter();
            studentFilter.setClasses(classes);
            List<StudentEntity> students = studentRepository.findAll(specificationBuilder.buildSpecification(studentFilter));

            assertEquals(2, students.size());
            assertThat(students.get(0)).satisfies(studentEntity -> {
                assertThat(studentEntity.getId()).isEqualTo(4);
                assertThat(studentEntity.getClassEntities().stream().map(ClassEntity::getName).collect(Collectors.toList())).containsAll(classes);
            });
            assertThat(students.get(1)).satisfies(studentEntity -> {
                assertThat(studentEntity.getId()).isEqualTo(5);
                assertThat(studentEntity.getClassEntities().stream().map(ClassEntity::getName).collect(Collectors.toList())).containsAll(classes);
            });
        }
    }

    @Nested
    class BySegmentFilter extends BaseTest {

        @Test
        void shouldFilterIntersectionOnlyByFrom() {
            StudentStudyingFilter filter = new StudentStudyingFilter(
                LocalDate.of(2020, 2, 15),
                null
            );

            List<StudentEntity> students = studentRepository.findAll(specificationBuilder.buildSpecification(filter));

            assertEquals(4, students.size());
            assertThat(students).noneSatisfy(studentEntity -> assertThat(studentEntity.getId()).isEqualTo(1));
        }

        @Test
        void shouldFilterIntersectionOnlyByTo() {
            StudentStudyingFilter filter = new StudentStudyingFilter(
                null,
                LocalDate.of(2020, 2, 18)
            );

            List<StudentEntity> students = studentRepository.findAll(specificationBuilder.buildSpecification(filter));

            assertEquals(4, students.size());
            assertThat(students).noneSatisfy(studentEntity -> assertThat(studentEntity.getId()).isEqualTo(5));
        }

        @Test
        void shouldFilterIntersectionByFullSegment() {
            StudentStudyingFilter filter = new StudentStudyingFilter(
                LocalDate.of(2020, 2, 15),
                LocalDate.of(2020, 2, 18)
            );

            List<StudentEntity> students = studentRepository.findAll(specificationBuilder.buildSpecification(filter));

            assertEquals(3, students.size());
            assertThat(students)
                .noneSatisfy(studentEntity -> assertThat(studentEntity.getId()).isEqualTo(1))
                .noneSatisfy(studentEntity -> assertThat(studentEntity.getId()).isEqualTo(5));
        }
    }

    @Nested
    class Names {

        @Nested
        class And extends BaseTest {

            @Test
            void shouldReturnByTwoCostFields() {
                AndCostGreaterThenCarFilter filter = new AndCostGreaterThenCarFilter();
                filter.setValue(40);

                List<CarEntity> cars = carRepository.findAll(specificationBuilder.buildSpecification(filter));
                assertEquals(1, cars.size());
                assertThat(cars.get(0)).satisfies(carEntity -> {
                    assertThat(carEntity.getCostTo()).isGreaterThan(40);
                    assertThat(carEntity.getCostFrom()).isGreaterThan(40);
                });
            }
        }

        @Nested
        class Or extends BaseTest {

            @Test
            void shouldReturnByOneOfTwoCostFields() {
                OrCostGreaterThenCarFilter filter = new OrCostGreaterThenCarFilter();
                filter.setValue(40);

                List<CarEntity> cars = carRepository.findAll(specificationBuilder.buildSpecification(filter));
                assertEquals(2, cars.size());
                assertThat(cars).extracting("id").containsOnly(1, 3);
            }
        }

        @Nested
        class Joined extends BaseTest {

            @Test
            void shouldFindByNameOrSurname() {
                PersonNameOrSurnameFilter filter = new PersonNameOrSurnameFilter();
                filter.setValue("Jon");

                List<PersonEntity> persons = personRepository.findAll(specificationBuilder.buildSpecification(filter));
                assertEquals(2, persons.size());
                assertThat(persons).extracting("id").containsOnly(1, 2);
            }

            @Test
            void shouldReturnEmpty() {
                PersonNameOrSurnameFilter filter = new PersonNameOrSurnameFilter();
                filter.setValue("Andy");

                List<PersonEntity> persons = personRepository.findAll(specificationBuilder.buildSpecification(filter));
                assertThat(persons).isEmpty();
            }

        }

    }

    @Nested
    class Not extends BaseTest {

        @Test
        void shouldReturnCarsExcludeId() {
            ExcludeIdCarFilter filter = new ExcludeIdCarFilter(1);

            List<CarEntity> cars = carRepository.findAll(specificationBuilder.buildSpecification(filter));
            assertEquals(2, cars.size());
            assertThat(cars).extracting("id").containsOnly(2, 3);
        }

        @Test
        void shouldReturnCarsExcludeIdAndCost() {
            ExcludeIdAndCostCarFilter filter = new ExcludeIdAndCostCarFilter(1);

            List<CarEntity> cars = carRepository.findAll(specificationBuilder.buildSpecification(filter));
            assertEquals(2, cars.size());
            assertThat(cars).extracting("id").containsOnly(2, 3);
        }
    }

    @Nested
    class Distinct extends BaseTest {

        @Test
        void shouldReturnDistinctClients() {
            ClientContractNumberFilter filter = new ClientContractNumberFilter();
            filter.setContract("UK");

            List<ClientEntity> clients = clientRepository.findAll(specificationBuilder.buildSpecification(filter));
            assertEquals(2, clients.size());
            assertThat(clients.get(0)).satisfies(client -> {
                assertThat(client.getId()).isEqualTo(1L);
                assertThat(client.getName()).isEqualTo("client 1");
                assertThat(client.getContracts()).hasSize(3).satisfies(contracts -> {
                    assertThat(contracts.get(0).getId()).isEqualTo(1L);
                    assertThat(contracts.get(0).getNumber()).isEqualTo("UK9999");
                    assertThat(contracts.get(0).getIdClient()).isEqualTo(1L);
                    assertThat(contracts.get(1).getId()).isEqualTo(2L);
                    assertThat(contracts.get(1).getNumber()).isEqualTo("UK8888");
                    assertThat(contracts.get(1).getIdClient()).isEqualTo(1L);
                    assertThat(contracts.get(2).getId()).isEqualTo(3L);
                    assertThat(contracts.get(2).getNumber()).isEqualTo("UG129");
                    assertThat(contracts.get(2).getIdClient()).isEqualTo(1L);
                });
            });
            assertThat(clients.get(1)).satisfies(client -> {
                assertThat(client.getId()).isEqualTo(2L);
                assertThat(client.getName()).isEqualTo("client 2");
                assertThat(client.getContracts()).hasSize(3).satisfies(contracts -> {
                    assertThat(contracts.get(0).getId()).isEqualTo(4L);
                    assertThat(contracts.get(0).getNumber()).isEqualTo("UK1119");
                    assertThat(contracts.get(0).getIdClient()).isEqualTo(2L);
                    assertThat(contracts.get(1).getId()).isEqualTo(5L);
                    assertThat(contracts.get(1).getNumber()).isEqualTo("UG3339");
                    assertThat(contracts.get(1).getIdClient()).isEqualTo(2L);
                    assertThat(contracts.get(2).getId()).isEqualTo(6L);
                    assertThat(contracts.get(2).getNumber()).isEqualTo("UK4339");
                    assertThat(contracts.get(2).getIdClient()).isEqualTo(2L);
                });
            });


        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Function extends BaseTest {

        @ParameterizedTest
        @MethodSource("functionProvider")
        void shouldWrapWithFunction(FunctionCarFilter filter) {
            List<CarModel> models = carModelRepository.findAll(specificationBuilder.buildSpecification(filter));
            assertEquals(1, models.size());
            assertThat(models).extracting("id").containsOnly(2);
        }

        private Stream<Arguments> functionProvider() {
            return Stream.of(
                Arguments.of(FunctionCarFilter.bothWrap("VOLVO")),
                Arguments.of(FunctionCarFilter.fieldWrap("VOLVO")),
                Arguments.of(FunctionCarFilter.filterWrap("VOLVO"))
            );
        }
    }
}
