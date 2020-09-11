package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Not;
import lombok.Value;

@Value
public class ExcludeIdCarFilter {

    @Not
    private final Integer id;
}
