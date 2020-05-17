package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code equals} predicate
 *
 * @author MikeSafonov
 */
public class EqualsPredicateBuilder extends SimplePredicateBuilder {

    private final CriteriaBuilder cb;
    private final Object fieldValue;

    public EqualsPredicateBuilder(CriteriaBuilder cb, Object fieldValue, Expression expression) {
        super(expression);
        this.cb = cb;
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate build() {
        return cb.equal(expression, fieldValue);
    }
}
