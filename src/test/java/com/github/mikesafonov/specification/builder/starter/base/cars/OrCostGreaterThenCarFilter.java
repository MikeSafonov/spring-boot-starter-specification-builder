package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.GreaterThan;
import com.github.mikesafonov.specification.builder.starter.annotations.Names;
import lombok.Data;

/**
 * @author Mike Safonov
 */
@Data
public class OrCostGreaterThenCarFilter {

    @GreaterThan
    @Names({"costFrom", "costTo"})
    private Integer value;
}
