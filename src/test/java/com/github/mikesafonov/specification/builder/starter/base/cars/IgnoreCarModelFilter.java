package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;

public class IgnoreCarModelFilter {
    private Integer id;

    @Name("id")
    @Ignore
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
