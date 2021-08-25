package com.github.mikesafonov.specification.builder.starter;


import com.github.mikesafonov.specification.builder.starter.annotations.Distinct;
import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.IsNull;
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
        List<FieldWithValue> fieldsForPredicate = Utils.getFields(filter).stream()
            .filter(this::isFieldSupported)
            .map(field -> toFieldWithValue(field, filter))
            .filter(Objects::nonNull)
            .collect(toList());

        return SelfBuildSpecificationBuilder.get()
            .useDistinct(filter.getClass().isAnnotationPresent(Distinct.class))
            .fields(fieldsForPredicate)
            .build();
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
