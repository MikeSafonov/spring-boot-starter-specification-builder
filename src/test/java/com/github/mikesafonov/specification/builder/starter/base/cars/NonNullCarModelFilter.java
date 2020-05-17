package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.NonNull;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class NonNullCarModelFilter {
    @NonNull
    private String name;
}
