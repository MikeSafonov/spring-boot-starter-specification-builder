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


    public String getFieldName() {
        return field.isAnnotationPresent(Name.class)
            ? field.getAnnotation(Name.class).value()
            : field.getName();
    }

    public boolean isAnnotatedBy(Class<? extends Annotation> annotationClass){
        return field.isAnnotationPresent(annotationClass);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    public boolean isValueCollection(){
        return Collection.class.isAssignableFrom(value.getClass());
    }

    public Collection getValueAsCollection(){
        return (Collection) value;
    }

    public boolean isValueSegmentFilter(){
        return SegmentFilter.class.isAssignableFrom(value.getClass());
    }

    public SegmentFilter<?> getValueAsSegmentFilter(){
        return (SegmentFilter<?>) value;
    }

}
