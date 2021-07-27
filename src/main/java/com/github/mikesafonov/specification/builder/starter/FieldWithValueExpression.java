package com.github.mikesafonov.specification.builder.starter;

import lombok.Value;

import javax.persistence.criteria.Expression;

@Value
public class FieldWithValueExpression {

    private final Expression<?> fieldExpression;
    private final Expression<?> valueExpression;
}
