package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code isNotNull} predicate
 *
 * @author MikeSafonov
 */
public class NotNullPredicateBuilder extends SimplePredicateBuilder {
    private final CriteriaBuilder cb;

    public NotNullPredicateBuilder(CriteriaBuilder cb, Expression expression) {
        super(expression);
        this.cb = cb;
    }

    @Override
    public Predicate build() {
        return cb.isNotNull(expression);
    }
}
