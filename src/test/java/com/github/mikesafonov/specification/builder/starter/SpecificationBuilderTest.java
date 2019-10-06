package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.base.CarEntity;
import com.github.mikesafonov.specification.builder.starter.base.CarFilter;
import com.github.mikesafonov.specification.builder.starter.base.CarRepository;
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
}
