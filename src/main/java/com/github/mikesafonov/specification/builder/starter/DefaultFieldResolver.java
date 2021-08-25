package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.IsNull;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

/**
 * @author Mike Safonov
 */
@AllArgsConstructor
public class DefaultFieldResolver implements FieldResolver {

    private static final List<String> FIELD_NAME_IGNORE_LIST = singletonList("serialVersionUID");

    private final List<String> ignoreList;

    public DefaultFieldResolver() {
        this(FIELD_NAME_IGNORE_LIST);
    }

    @Override
    public Stream<FieldWithValue> resolve(Object filter) {
        return Utils.getFields(filter).stream()
            .filter(this::isFieldSupported)
            .map(field -> toFieldWithValue(field, filter))
            .filter(Objects::nonNull);
    }

    private boolean isFieldSupported(@NonNull Field field) {
        return !(field.isAnnotationPresent(Ignore.class) || ignoreList.contains(field.getName()));
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
