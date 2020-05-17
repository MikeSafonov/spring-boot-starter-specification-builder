package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 * @author Mike Safonov
 */
@Data
public class CostToLikeFilter {
    @Like
    @Name("costTo")
    private String key;
}
