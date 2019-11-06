package com.github.mikesafonov.specification.builder.starter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that for the desired record all values ​​from the collection should be presented in the intersection table
 *
 * @author MikeSafonov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToManyCollection {
}
