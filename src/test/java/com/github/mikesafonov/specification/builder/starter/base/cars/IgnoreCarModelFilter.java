package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class IgnoreCarModelFilter {
    private Integer id;

    @Name("id")
    @Ignore
    private String name;
}
