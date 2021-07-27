package com.github.mikesafonov.specification.builder.starter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field or/and filer value must be wrapped by function
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {

    /**
     * @return function name
     */
    String name();

    /**
     * @return wrapping mode
     */
    FunctionWrapping wrapping() default FunctionWrapping.BOTH;

    enum FunctionWrapping {
        /**
         * Wrap field by function
         */
        FIELD,
        /**
         * Wrap filter value by function
         */
        FILTER,
        /**
         * Wrap field and filter value by function
         */
        BOTH,
    }
}
