package com.github.mikesafonov.specification.builder.starter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike Safonov
 */
public class SelfBuildSpecificationBuilder {

    private final List<FieldWithValue> fields = new ArrayList<>();
    private boolean useDistinct = false;

    public static SelfBuildSpecificationBuilder get() {
        return new SelfBuildSpecificationBuilder();
    }

    public SelfBuildSpecificationBuilder useDistinct() {
        return useDistinct(true);
    }

    public SelfBuildSpecificationBuilder useDistinct(boolean value) {
        this.useDistinct = value;
        return this;
    }

    public SelfBuildSpecificationBuilder fields(List<FieldWithValue> fieldWithValues) {
        this.fields.addAll(fieldWithValues);
        return this;
    }

    public <S> SelfBuildSpecification<S> build() {
        return new SelfBuildSpecification<>(fields, useDistinct);
    }

}
