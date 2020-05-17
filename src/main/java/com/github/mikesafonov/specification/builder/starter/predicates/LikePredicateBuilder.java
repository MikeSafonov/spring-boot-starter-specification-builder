package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code like} predicate
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class LikePredicateBuilder implements PredicateBuilder {

    private static final String PERCENT = "%";

    private final CriteriaBuilder cb;
    private final Like like;
    private final Object fieldValue;
    private final Expression expression;

    @Override
    public Predicate build() {
        String searchValue = getSearchValue();
        Expression<String> expr = expression.as(String.class);
        if (!like.caseSensitive()) {
            expr = cb.upper(expr);
            searchValue = searchValue.toUpperCase();
        }
        return cb.like(expr, searchValue);
    }

    private String getSearchValue() {
        switch (like.direction()) {
            case LEFT:
                return PERCENT + fieldValue;
            case RIGHT:
                return fieldValue + PERCENT;
            default:
                return PERCENT + fieldValue + PERCENT;
        }
    }
}
