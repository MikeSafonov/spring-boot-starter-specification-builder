package com.github.mikesafonov.specification.builder.starter.predicates;

import org.springframework.lang.NonNull;

import javax.persistence.criteria.Predicate;

/**
 * Extension for building specific {@link Predicate}
 *
 * @author MikeSafonov
 */
public interface PredicateBuilder {

    /**
     * Builds specific {@link Predicate}
     *
     * @return {@link Predicate}
     */
    @NonNull Predicate build();
}
