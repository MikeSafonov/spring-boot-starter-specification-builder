package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.NonNull;

/**
 *
 * @author MikeSafonov
 */
public class NonNullCarModelFilter {
    @NonNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
