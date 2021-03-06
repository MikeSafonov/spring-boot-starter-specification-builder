package com.github.mikesafonov.specification.builder.starter;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Mike Safonov
 */
@Slf4j
@UtilityClass
class Utils {

    /**
     *
     * @param value
     * @return verify {@code value} not null and (if instance of String) not blank
     */
    static boolean isNotNullAndNotBlank(@Nullable Object value) {
        if (value != null) {
            if (value instanceof String) {
                String stringValue = ((String) value);
                return !stringValue.trim().isEmpty();
            }
            if(value instanceof Collection){
                Collection collection = (Collection) value;
                return !collection.isEmpty();
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @param field field of {@code filter}
     * @param filter
     * @return
     */
    @Nullable
    static Object getFieldValue(@NonNull Field field, @NonNull Object filter) {
        try {
            return FieldUtils.readField(field, filter, true);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @param filter
     * @return all <b>non synthetic</b> fields of the given class and its parents
     */
    @NonNull
    static List<Field> getFields(@NonNull Object filter) {
        return Arrays.stream(FieldUtils.getAllFields(filter.getClass()))
                .filter(field -> !field.isSynthetic())
                .collect(toList());
    }
}
