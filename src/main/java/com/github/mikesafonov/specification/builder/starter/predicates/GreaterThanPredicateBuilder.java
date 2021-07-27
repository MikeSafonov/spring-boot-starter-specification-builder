package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author MikeSafonov
 */
public class GreaterThanPredicateBuilder extends SimplePredicateBuilder {

    private final CriteriaBuilder cb;
    private final Expression valueExpression;

    public GreaterThanPredicateBuilder(CriteriaBuilder cb, Expression valueExpression, Expression expression) {
        super(expression);
        this.cb = cb;
        this.valueExpression = valueExpression;
    }

    @Override
    public Predicate build() {
        return cb.greaterThan(expression, valueExpression);
    }
}
