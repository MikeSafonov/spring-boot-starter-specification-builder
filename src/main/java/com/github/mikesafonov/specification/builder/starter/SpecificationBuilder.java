package com.github.mikesafonov.specification.builder.starter;


import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilder;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author MikeSafonov
 */
@Slf4j
public class SpecificationBuilder {

    private static final String SERIAL_VERSION_UID_NAME = "serialVersionUID";

    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder();

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
        Expression expression = ExpressionBuilder.getExpression(root, field, fieldName);
        PredicateBuilder predicateBuilder = PredicateBuilderFactory.createPredicateBuilder(cb, field, fieldValue);
        return predicateBuilder.build(expression);
    }
}