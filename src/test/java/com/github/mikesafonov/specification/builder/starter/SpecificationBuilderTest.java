package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.base.CarEntity;
import com.github.mikesafonov.specification.builder.starter.base.CarFilter;
import com.github.mikesafonov.specification.builder.starter.base.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@Sql("/init.sql")
class SpecificationBuilderTest {

    @Autowired
    private CarRepository carRepository;

    private SpecificationBuilder specificationBuilder;

    @BeforeEach
    void setUp(){
        specificationBuilder = new SpecificationBuilder();
    }

    @Test
    void shouldFindById(){
        CarFilter carFilter = new CarFilter();
        carFilter.setId(1);
        List<CarEntity> data = carRepository.findAll(specificationBuilder.buildSpecification(CarEntity.class, carFilter));
        Assertions.assertEquals(1, data.size());
    }
}
