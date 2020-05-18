package com.github.mikesafonov.specification.builder.starter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify column names to search in
 *
 * @author MikeSafonov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Names {

    String[] value() default {};

    SearchType type() default SearchType.OR;

    /**
     * Type of result predicate by specified column names
     */
    enum SearchType{
        OR, AND
    }

}
