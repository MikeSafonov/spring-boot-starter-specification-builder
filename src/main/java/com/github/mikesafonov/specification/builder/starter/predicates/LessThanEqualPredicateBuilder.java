package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class LessThanEqualPredicateBuilder implements PredicateBuilder {

    private final CriteriaBuilder cb;
    private final Object fieldValue;
    private final Expression expression;

    @Override
    public Predicate build() {
        return cb.lessThanOrEqualTo(expression, (Comparable) fieldValue);
    }
}
