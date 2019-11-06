package com.github.mikesafonov.specification.builder.starter.base.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<CarEntity, Integer>, JpaSpecificationExecutor<CarEntity> {
}
