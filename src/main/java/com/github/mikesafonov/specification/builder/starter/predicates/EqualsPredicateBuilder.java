package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code equals} predicate
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class EqualsPredicateBuilder implements PredicateBuilder {
    private final CriteriaBuilder cb;
    private final Object fieldValue;

    @Override
    public Predicate build(Expression expression) {
        return cb.equal(expression, fieldValue);
    }
}
