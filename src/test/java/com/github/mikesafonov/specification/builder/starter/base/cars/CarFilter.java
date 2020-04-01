package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.*;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;

import java.util.Collection;

/**
 *
 * @author MikeSafonov
 */
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
    @Name("number")
    private String nonNullValue;
    @IsNull
    @Name("number")
    private String nullValue;
    private Collection collection;
    @ManyToManyCollection
    private Collection manyToManyCollection;

    @GreaterThanEqual
    @Name("id")
    private int idGreaterThanEqual;

    @GreaterThan
    @Name("id")
    private int idGreaterThan;

    @LessThanEqual
    @Name("id")
    private int idLessThanEqual;

    @LessThan
    @Name("id")
    private int idLessThan;

    @SegmentIntersection(fromField = "costFrom", toField = "costTo")
    private SegmentFilter<Integer> costFilter;

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

    public int getIdGreaterThan() {
        return idGreaterThan;
    }

    public void setIdGreaterThan(int idGreaterThan) {
        this.idGreaterThan = idGreaterThan;
    }

    public int getIdGreaterThanEqual() {
        return idGreaterThanEqual;
    }

    public void setIdGreaterThanEqual(int idGreaterThanEqual) {
        this.idGreaterThanEqual = idGreaterThanEqual;
    }

    public int getIdLessThan() {
        return idLessThan;
    }

    public void setIdLessThan(int idLessThan) {
        this.idLessThan = idLessThan;
    }

    public int getIdLessThanEqual() {
        return idLessThanEqual;
    }

    public void setIdLessThanEqual(int idLessThanEqual) {
        this.idLessThanEqual = idLessThanEqual;
    }

    public SegmentFilter<Integer> getCostFilter() {
        return costFilter;
    }
}
