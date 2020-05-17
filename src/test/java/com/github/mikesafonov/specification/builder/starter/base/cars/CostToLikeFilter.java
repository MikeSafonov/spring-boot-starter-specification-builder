package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;

/**
 * @author Mike Safonov
 */
public class CostToLikeFilter {
    @Like
    @Name("costTo")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
