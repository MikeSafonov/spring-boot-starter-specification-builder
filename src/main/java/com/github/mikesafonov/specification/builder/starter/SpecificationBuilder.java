package com.github.mikesafonov.specification.builder.starter;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * @author MikeSafonov
 */
public class SpecificationBuilder {

    private final FieldResolver fieldResolver;

    public SpecificationBuilder() {
        this(new DefaultFieldResolver());
    }

    public SpecificationBuilder(FieldResolver fieldResolver) {
        this.fieldResolver = fieldResolver;
    }

    public <F, S> Specification<S> buildSpecification(@NonNull F filter) {
        Objects.requireNonNull(filter);
        List<FieldWithValue> fieldsForPredicate = fieldResolver.resolve(filter).collect(toList());

        return SelfBuildSpecificationBuilder.get(filter.getClass())
            .useDistinct()
            .fields(fieldsForPredicate)
            .build();
    }
}
