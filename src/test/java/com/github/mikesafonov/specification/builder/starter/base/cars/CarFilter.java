package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.*;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import lombok.Data;

import java.util.Collection;

/**
 * @author MikeSafonov
 */
@Data
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

    @Names({"id", "number"})
    @NonNull
    private Object namesNonNull;

    @Names(value = {"id", "number"}, type = Names.SearchType.AND)
    @NonNull
    private Object namesNonNullAnd;

    @Not
    @IsNull
    @Name("number")
    private String notNullValue;
}
