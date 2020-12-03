package com.github.mikesafonov.specification.builder.starter.predicates;

import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    @NonNull Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb);
}
