package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * @author Mike Safonov
 */
@RequiredArgsConstructor
public abstract class SimplePredicateBuilder implements PredicateBuilder {

    protected final ExpressionBuilder expressionBuilder;
    protected final FieldWithValue field;


    protected Expression getExpression(Root<?> root) {
        return expressionBuilder.getExpression(root, field);
    }
}
