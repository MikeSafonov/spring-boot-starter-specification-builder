package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.base.cars.*;
import com.github.mikesafonov.specification.builder.starter.base.studens.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MikeSafonov
 */

class SpecificationBuilderTest {

    @DataJpaTest
    @Sql("/init.sql")
    abstract class BaseTest {

        @Autowired
        CarRepository carRepository;
        @Autowired
        CarModelRepository carModelRepository;
        @Autowired
        StudentRepository studentRepository;

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
            carFilter.setModel("volvo");
            List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(carFilter));
            assertEquals(1, data.size());
            assertThat(data.get(0)).satisfies(carEntity -> {
                assertThat(carEntity.getId()).isEqualTo(2);
                assertThat(carEntity.getNumber()).isEqualTo("2312");
                assertThat(carEntity.getModel()).satisfies(carModel -> {
                    assertThat(carModel.getId()).isEqualTo(2);
                    assertThat(carModel.getName()).isEqualTo("volvo");
                });
            });
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
    class GreaterThan extends BaseTest{

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
    class GreaterThanEqual extends BaseTest{

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
}
