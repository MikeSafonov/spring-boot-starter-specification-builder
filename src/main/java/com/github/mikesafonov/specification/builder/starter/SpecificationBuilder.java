package com.github.mikesafonov.specification.builder.starter;


import com.github.mikesafonov.specification.builder.starter.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author MikeSafonov
 */
@Slf4j
public class SpecificationBuilder {

    private static final String PERCENT = "%";
    private static final String SERIAL_VERSION_UID_NAME = "serialVersionUID";

    public <F, S> Specification<S> buildSpecification(Class<S> specClass, F filter) {
        List<Field> fields = Utils.getFields(filter);
        return (root, query, cb) -> cb.and(fields.stream()
                .filter(this::isFieldSupported)
                .map(field -> toPredicate(field, root, cb, filter))
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new));
    }

    private boolean isFieldSupported(Field field) {
        return !(field.isAnnotationPresent(Ignore.class) || SERIAL_VERSION_UID_NAME.equals(field.getName()));
    }

    private <F, S> Predicate toPredicate(Field field, Root<S> root, CriteriaBuilder cb, F filter) {
        String fieldName = getFieldName(field);
        Object fieldValue = Utils.getFieldValue(field, filter);
        if (Utils.isNotNullAndNotBlank(fieldValue)) {
            return toPredicate(root, cb, field, fieldName, fieldValue);
        }
        return null;
    }

    private String getFieldName(Field field) {
        return field.isAnnotationPresent(Name.class)
                ? field.getAnnotation(Name.class).value()
                : field.getName();
    }

    private <S> Predicate toPredicate(Root<S> root, CriteriaBuilder cb, Field field, String fieldName,
                                      Object fieldValue) {
        Expression expression = getExpression(root, field, fieldName);
        if(Collection.class.isAssignableFrom(fieldValue.getClass())){
            return expression.in(fieldValue);
        }
        if (field.isAnnotationPresent(Like.class)) {
            return toLikePredicate(cb, field.getAnnotation(Like.class),
                    expression, fieldValue);
        } else if (field.isAnnotationPresent(NonNull.class)) {
            return cb.isNotNull(expression);
        } else if (field.isAnnotationPresent(IsNull.class)) {
            return cb.isNull(expression);
        } else {
            return cb.equal(expression, fieldValue);
        }
    }

    private <S> Expression getExpression(Root<S> root, Field field, String fieldName) {
        if (field.isAnnotationPresent(Join.class)) {
            Join[] joins = field.getAnnotationsByType(Join.class);
            javax.persistence.criteria.Join<Object, Object> join = root.join(joins[0].value(), joins[0].type());
            for (int i = 1; i < joins.length; i++) {
                join = join.join(joins[i].value(), joins[i].type());
            }
            return join.get(fieldName);
        } else {
            return root.get(fieldName);
        }
    }

    private Predicate toLikePredicate(CriteriaBuilder cb, Like like,
                                      Expression<String> expression,
                                      Object fieldValue) {
        String searchValue = getSearchValue(fieldValue, like.direction());
        Expression<String> expr = expression;
        if (!like.caseSensitive()) {
            expr = cb.upper(expr);
            searchValue = searchValue.toUpperCase();
        }
        return cb.like(expr, searchValue);
    }

    private String getSearchValue(Object fieldValue, Like.DIRECTION direction) {
        switch (direction) {
            case LEFT: {
                return PERCENT + fieldValue;
            }
            case RIGHT: {
                return fieldValue + PERCENT;
            }
            default: {
                return PERCENT + fieldValue + PERCENT;
            }
        }
    }
}
