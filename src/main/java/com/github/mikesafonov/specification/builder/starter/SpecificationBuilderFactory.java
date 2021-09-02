package com.github.mikesafonov.specification.builder.starter;

import lombok.experimental.UtilityClass;

/**
 * @author Mike Safonov
 */
@UtilityClass
public class SpecificationBuilderFactory {

    public static SpecificationBuilder common() {
        return new SpecificationBuilderBuilder().build();
    }

    public static SpecificationBuilderBuilder custom() {
        return new SpecificationBuilderBuilder();
    }

}
