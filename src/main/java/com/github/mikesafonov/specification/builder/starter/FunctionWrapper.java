package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class FunctionWrapper {

    public FieldWithValueExpression wrapByFunction(CriteriaBuilder cb, Function function,
                                                   FieldWithValueExpression fieldWithValueExpression) {
        Expression<?> fieldExpression = fieldWithValueExpression.getFieldExpression();
        Expression<?> valueExpression = fieldWithValueExpression.getValueExpression();
        Function.FunctionWrapping wrapping = function.wrapping();
        if (wrapping == Function.FunctionWrapping.FIELD) {
            fieldExpression = wrapByFunction(cb, function, fieldExpression);
        } else if (wrapping == Function.FunctionWrapping.FILTER) {
            valueExpression = wrapByFunction(cb, function, valueExpression);
        } else {
            fieldExpression = wrapByFunction(cb, function, fieldExpression);
            valueExpression = wrapByFunction(cb, function, valueExpression);
        }
        return new FieldWithValueExpression(fieldExpression, valueExpression);
    }


    private Expression<?> wrapByFunction(CriteriaBuilder cb, Function function, Expression<?> expression) {
        return cb.function(function.name(), Object.class, expression);
    }
}
