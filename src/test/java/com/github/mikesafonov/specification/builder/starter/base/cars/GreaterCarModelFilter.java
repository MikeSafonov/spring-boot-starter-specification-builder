package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.GreaterThan;
import com.github.mikesafonov.specification.builder.starter.annotations.GreaterThanEqual;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;

public class GreaterCarModelFilter {
    @GreaterThan
    private Integer id;

    @GreaterThanEqual
    @Name("id")
    private Integer idEqual;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEqual() {
        return idEqual;
    }

    public void setIdEqual(Integer idEqual) {
        this.idEqual = idEqual;
    }
}
