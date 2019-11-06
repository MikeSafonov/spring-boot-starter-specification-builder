package com.github.mikesafonov.specification.builder.starter.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarModelRepository extends JpaRepository<CarModel, Integer>, JpaSpecificationExecutor<CarModel> {
}
