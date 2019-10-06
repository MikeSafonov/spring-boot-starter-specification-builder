package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code isNotNull} predicate
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class NotNullPredicateBuilder implements PredicateBuilder {
    private final CriteriaBuilder cb;

    @Override
    public Predicate build(Expression expression) {
        return cb.isNotNull(expression);
    }
}
