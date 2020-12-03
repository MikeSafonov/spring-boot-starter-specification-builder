package com.github.mikesafonov.specification.builder.starter;


import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.IsNull;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilder;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilderFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * @author MikeSafonov
 */
public class SpecificationBuilder {

    private static final String SERIAL_VERSION_UID_NAME = "serialVersionUID";

    public <F, S> Specification<S> buildSpecification(@NonNull F filter) {
        Objects.requireNonNull(filter);
        PredicateBuilderFactory factory = new PredicateBuilderFactory();
        List<PredicateBuilder> predicateBuilders = Utils.getFields(filter).stream()
            .filter(this::isFieldSupported)
            .map(field -> toFieldWithValue(field, filter))
            .filter(Objects::nonNull)
            .map(factory::createPredicateBuilder)
            .collect(toList());

        return new SelfBuildSpecification<>(predicateBuilders);
    }

    private boolean isFieldSupported(@NonNull Field field) {
        return !(field.isAnnotationPresent(Ignore.class) || SERIAL_VERSION_UID_NAME.equals(field.getName()));
    }

    @Nullable
    private FieldWithValue toFieldWithValue(@NonNull Field field, @NonNull Object filter) {
        Object fieldValue = Utils.getFieldValue(field, filter);
        if (Utils.isNotNullAndNotBlank(fieldValue) || field.isAnnotationPresent(IsNull.class)
            || field.isAnnotationPresent(com.github.mikesafonov.specification.builder.starter.annotations.NonNull.class)) {
            return new FieldWithValue(field, fieldValue);
        }
        return null;
    }
}
