package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.Expression;

/**
 * @author Mike Safonov
 */
@RequiredArgsConstructor
public abstract class SimplePredicateBuilder implements PredicateBuilder {

    protected final Expression expression;
}
