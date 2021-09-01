package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.query.DistinctQueryProcessor;
import com.github.mikesafonov.specification.builder.starter.query.QueryProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike Safonov
 */
public class SelfBuildSpecificationBuilder {

    private final Class<?> filterClass;
    private final List<QueryProcessor> queryProcessors = new ArrayList<>();
    private final List<FieldWithValue> fields = new ArrayList<>();

    private SelfBuildSpecificationBuilder(Class<?> filterClass) {
        this.filterClass = filterClass;
    }

    public static SelfBuildSpecificationBuilder get(Class<?> filterClass) {
        return new SelfBuildSpecificationBuilder(filterClass);
    }

    public SelfBuildSpecificationBuilder queryProcessors(List<QueryProcessor> queryProcessors) {
        this.queryProcessors.addAll(queryProcessors);
        return this;
    }

    public SelfBuildSpecificationBuilder useDistinct() {
        queryProcessors.add(new DistinctQueryProcessor(filterClass));
        return this;
    }

    public SelfBuildSpecificationBuilder fields(List<FieldWithValue> fieldWithValues) {
        this.fields.addAll(fieldWithValues);
        return this;
    }

    public <S> SelfBuildSpecification<S> build() {
        return new SelfBuildSpecification<>(queryProcessors, fields);
    }

}
