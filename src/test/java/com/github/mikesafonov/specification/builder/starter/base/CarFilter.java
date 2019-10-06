package com.github.mikesafonov.specification.builder.starter.base;

import com.github.mikesafonov.specification.builder.starter.annotations.*;

import java.util.Collection;

public class CarFilter {
    private Integer id;
    @Name("number")
    private String car;
    @Join(value = "model")
    private String name;
    @Join(value = "model")
    @Name(value = "name")
    private String model;
    @Like
    private String likeValue;

    @NonNull
    private String nonNullValue;
    @IsNull
    private String nullValue;
    @IsNull
    private Collection collection;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
