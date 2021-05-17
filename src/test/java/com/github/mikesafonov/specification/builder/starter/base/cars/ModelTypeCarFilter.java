package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class ModelTypeCarFilter {
    @Join(value = "model")
    @Name("name")
    private String model;

    @Join(value = "model")
    @Join(value = "type")
    @Name(value = "type")
    private String type;
}
