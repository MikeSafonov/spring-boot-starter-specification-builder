package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.FieldWithValueExpression;
import com.github.mikesafonov.specification.builder.starter.annotations.Like;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Builder for {@code like} predicate
 *
 * @author MikeSafonov
 */
public class LikePredicateProcessor implements PredicateProcessor {

    private static final String PERCENT = "%";


    @Override
    public <T> void process(PredicateContainer container, FieldWithValue field, Root<T> root,
                            CriteriaBuilder cb, CriteriaQuery<?> cq, ExpressionBuilder expressionBuilder) {
        if (field.isAnnotatedBy(Like.class)) {
            FieldWithValueExpression fieldWithValueExpression = expressionBuilder
                .toFieldWithValueExpression(field, cb, root);
            Like like = field.getAnnotation(Like.class);
            Expression<String> searchExpression = getSearchValue(like, cb,
                fieldWithValueExpression.getValueExpression().as(String.class));
            Expression<String> expr = fieldWithValueExpression.getFieldExpression().as(String.class);
            if (!like.caseSensitive()) {
                expr = cb.upper(expr);
                searchExpression = cb.upper(searchExpression);
            }
            container.add(cb.like(expr, searchExpression));
        }
    }

    private Expression<String> getSearchValue(Like like, CriteriaBuilder cb, Expression<String> valueExpression) {
        switch (like.direction()) {
            case LEFT:
                return cb.concat(PERCENT, valueExpression);
            case RIGHT:
                return cb.concat(valueExpression, PERCENT);
            case NONE:
                return valueExpression;
            default:
                return cb.concat(cb.concat(PERCENT, valueExpression), PERCENT);
        }
    }
}
