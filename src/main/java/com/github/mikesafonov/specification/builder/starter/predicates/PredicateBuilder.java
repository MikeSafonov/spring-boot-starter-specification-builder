package com.github.mikesafonov.specification.builder.starter.predicates;

import org.springframework.lang.NonNull;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Extension for building specific {@link Predicate}
 *
 * @author MikeSafonov
 */
public interface PredicateBuilder {

    /**
     * Builds specific {@link Predicate} from {@link Expression}
     *
     * @param expression {@link Expression}
     * @return {@link Predicate}
     */
    @NonNull Predicate build(@NonNull Expression expression);
}
