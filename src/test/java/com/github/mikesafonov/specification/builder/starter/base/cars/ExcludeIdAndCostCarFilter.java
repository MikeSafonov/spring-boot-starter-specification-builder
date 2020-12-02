package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Names;
import com.github.mikesafonov.specification.builder.starter.annotations.Not;
import lombok.Value;

@Value
public class ExcludeIdAndCostCarFilter {

    @Not
    @Names(value = {"id", "costFrom"}, type = Names.SearchType.AND)
    private final Integer value;
}
