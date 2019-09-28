package com.github.mikesafonov.specification.builder.starter;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Mike Safonov
 */
@Slf4j
class Utils {

    static boolean isNotNullAndNotBlank(Object value) {
        if (value != null) {
            if (value instanceof String) {
                String stringValue = ((String) value);
                return !stringValue.trim().isEmpty();
            }
            return true;
        }
        return false;
    }

    static <F> Object getFieldValue(Field field, F filter) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            return field.get(filter);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } finally {
            field.setAccessible(accessible);
        }
        return null;
    }

    static List<Field> getFields(Class clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            List<String> fieldNames = fields.stream()
                    .map(Field::getName)
                    .collect(toList());
            fields.addAll(getSuperclassFields(fieldNames, superclass));
        }
        return fields;
    }

    private static List<Field> getSuperclassFields(List<String> fieldNames, Class superclass) {
        List<Field> superclassFields = getFields(superclass);
        return superclassFields.stream()
                .filter(superclassField -> !fieldNames.contains(superclassField.getName()))
                .collect(toList());
    }
}
