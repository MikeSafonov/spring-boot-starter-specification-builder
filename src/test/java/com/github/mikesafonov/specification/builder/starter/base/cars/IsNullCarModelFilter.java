package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.IsNull;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class IsNullCarModelFilter {
    @IsNull
    private String name;
}
