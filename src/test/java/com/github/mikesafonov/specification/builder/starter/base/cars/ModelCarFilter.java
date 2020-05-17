package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class ModelCarFilter {
    @Name("number")
    private String car;
    @Join(value = "model")
    private String name;
    @Join(value = "model")
    @Name(value = "name")
    private String model;
}
