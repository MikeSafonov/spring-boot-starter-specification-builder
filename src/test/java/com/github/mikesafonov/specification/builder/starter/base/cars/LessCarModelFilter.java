package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.LessThan;
import com.github.mikesafonov.specification.builder.starter.annotations.LessThanEqual;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class LessCarModelFilter {
    @LessThan
    private Integer id;

    @LessThanEqual
    @Name("id")
    private Integer idEqual;
}
