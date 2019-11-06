package com.github.mikesafonov.specification.builder.starter.base;

import com.github.mikesafonov.specification.builder.starter.annotations.*;

import java.util.Collection;

public class IsNullCarModelFilter {
    @IsNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
