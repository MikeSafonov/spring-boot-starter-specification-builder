package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code isNull} predicate
 *
 * @author MikeSafonov
 */
public class NullPredicateBuilder extends SimplePredicateBuilder {

    private final CriteriaBuilder cb;

    public NullPredicateBuilder(CriteriaBuilder cb, Expression expression) {
        super(expression);
        this.cb = cb;
    }

    @Override
    public Predicate build() {
        return cb.isNull(expression);
    }
}
