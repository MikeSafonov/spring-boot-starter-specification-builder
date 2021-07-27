package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Function;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter(AccessLevel.PRIVATE)
public class FunctionCarFilter {

    @Name("name")
    @Function(name = "LOWER")
    private String bothWrap;

    @Name("name")
    @Function(name = "UPPER", wrapping = Function.FunctionWrapping.FIELD)
    private String fieldWrap;

    @Name("name")
    @Function(name = "LOWER", wrapping = Function.FunctionWrapping.FILTER)
    private String filterWrap;

    public static FunctionCarFilter bothWrap(String name) {
        FunctionCarFilter functionCarFilter = new FunctionCarFilter();
        functionCarFilter.setBothWrap(name);
        return functionCarFilter;
    }

    public static FunctionCarFilter fieldWrap(String name) {
        FunctionCarFilter functionCarFilter = new FunctionCarFilter();
        functionCarFilter.setFieldWrap(name);
        return functionCarFilter;
    }

    public static FunctionCarFilter filterWrap(String name) {
        FunctionCarFilter functionCarFilter = new FunctionCarFilter();
        functionCarFilter.setFilterWrap(name);
        return functionCarFilter;
    }
}
