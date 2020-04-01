package com.github.mikesafonov.specification.builder.starter.annotations;

import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that is segment bounded by {@code fromField} and {@code toField} must intersect with
 * segment from filter {@link SegmentFilter}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SegmentIntersection {

    /**
     * Name of the field indicating start of period
     */
    String fromField();

    /**
     * Name of the field indicating end of period
     */
    String toField();
}
