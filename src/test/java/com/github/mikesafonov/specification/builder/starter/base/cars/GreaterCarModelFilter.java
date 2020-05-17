package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.GreaterThan;
import com.github.mikesafonov.specification.builder.starter.annotations.GreaterThanEqual;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class GreaterCarModelFilter {
    @GreaterThan
    private Integer id;

    @GreaterThanEqual
    @Name("id")
    private Integer idEqual;
}
