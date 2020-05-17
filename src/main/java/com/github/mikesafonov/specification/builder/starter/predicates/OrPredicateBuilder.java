package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Mike Safonov
 */
@RequiredArgsConstructor
public class OrPredicateBuilder implements PredicateBuilder {

    private final CriteriaBuilder cb;
    private final Expression[] expressions;
    private final Function<Expression, PredicateBuilder> predicateBuilderFunction;

    @Override
    public Predicate build() {
        Predicate[] predicates = Arrays.stream(expressions)
            .map(predicateBuilderFunction)
            .map(PredicateBuilder::build)
            .toArray(Predicate[]::new);
        return cb.or(predicates);
    }

}
