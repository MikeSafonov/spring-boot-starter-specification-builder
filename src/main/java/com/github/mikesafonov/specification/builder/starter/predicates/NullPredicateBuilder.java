package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code isNull} predicate
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class NullPredicateBuilder implements PredicateBuilder {
    private final CriteriaBuilder cb;
    private final Expression expression;

    @Override
    public Predicate build() {
        return cb.isNull(expression);
    }
}
