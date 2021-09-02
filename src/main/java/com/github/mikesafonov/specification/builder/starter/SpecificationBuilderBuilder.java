package com.github.mikesafonov.specification.builder.starter;

/**
 * @author Mike Safonov
 */
public class SpecificationBuilderBuilder {

    private FieldResolver fieldResolver;

    public SpecificationBuilderBuilder fieldResolver(FieldResolver fieldResolver) {
        this.fieldResolver = fieldResolver;
        return this;
    }

    public SpecificationBuilder build() {
        return new SpecificationBuilder(getFieldResolver());
    }


    private FieldResolver getFieldResolver() {
        return (fieldResolver == null) ? new DefaultFieldResolver()
            : fieldResolver;
    }
}
