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
    private final Expression<?> valueExpression;

    public EqualsPredicateBuilder(CriteriaBuilder cb, Expression<?> valueExpression, Expression expression) {
        super(expression);
        this.cb = cb;
        this.valueExpression = valueExpression;
    }

    @Override
    public Predicate build() {
        return cb.equal(expression, valueExpression);
    }
}
