package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.base.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql("/init.sql")
class SpecificationBuilderTest {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarModelRepository carModelRepository;

    private SpecificationBuilder specificationBuilder;

    @BeforeEach
    void setUp() {
        specificationBuilder = new SpecificationBuilder();
    }

    @Test
    void shouldFindById() {
        CarFilter carFilter = new CarFilter();
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

    @Test
    void shouldFindByJoin() {
        CarFilter carFilter = new CarFilter();
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

    @Test
    void shouldFindByNonNull() {
        NonNullCarModelFilter modelFilter = new NonNullCarModelFilter();
        List<CarModel> data = carModelRepository.findAll(specificationBuilder.buildSpecification(modelFilter));
        assertEquals(2, data.size());
        assertThat(data.get(0)).satisfies(carModel -> assertThat(carModel.getName()).isNotNull());
        assertThat(data.get(1)).satisfies(carModel -> assertThat(carModel.getName()).isNotNull());
    }
}
