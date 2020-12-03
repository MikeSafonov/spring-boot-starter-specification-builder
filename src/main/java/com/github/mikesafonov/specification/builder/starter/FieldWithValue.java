package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import lombok.Value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author Mike Safonov
 */
@Value
public class FieldWithValue {

    private Field field;
    private Object value;
    private String name;

    public FieldWithValue(Field field, Object value) {
        this.field = field;
        this.value = value;
        name = field.isAnnotationPresent(Name.class)
            ? field.getAnnotation(Name.class).value()
            : field.getName();
    }

    public FieldWithValue(FieldWithValue fieldWithValue, String name) {
        this.field = fieldWithValue.field;
        this.value = fieldWithValue.value;
        this.name = name;
    }

    public FieldWithValue(Field field, Object value, String name) {
        this.field = field;
        this.value = value;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }

    public boolean isAnnotatedBy(Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    public boolean isValueCollection() {
        return Collection.class.isAssignableFrom(value.getClass());
    }

    public Collection getValueAsCollection() {
        return (Collection) value;
    }

    public boolean isValueSegmentFilter() {
        return SegmentFilter.class.isAssignableFrom(value.getClass());
    }

    public <T> SegmentFilter<T> getValueAsSegmentFilter() {
        return (SegmentFilter<T>) value;
    }

}
