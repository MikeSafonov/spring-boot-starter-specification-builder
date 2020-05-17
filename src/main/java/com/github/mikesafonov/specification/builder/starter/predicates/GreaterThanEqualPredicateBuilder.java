package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author MikeSafonov
 */
public class GreaterThanEqualPredicateBuilder extends SimplePredicateBuilder {
    private final CriteriaBuilder cb;
    private final Object fieldValue;

    public GreaterThanEqualPredicateBuilder(CriteriaBuilder cb, Object fieldValue, Expression expression) {
        super(expression);
        this.cb = cb;
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate build() {
        return cb.greaterThanOrEqualTo(expression, (Comparable) fieldValue);
    }
}
