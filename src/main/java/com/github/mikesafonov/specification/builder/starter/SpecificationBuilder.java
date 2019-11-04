package com.github.mikesafonov.specification.builder.starter;


import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilder;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

    public <F, S> Specification<S> buildSpecification(@NonNull F filter) {
        List<Field> fields = Utils.getFields(filter);
        return (root, query, cb) -> {
            Predicate[] predicates = fields.stream()
                    .filter(this::isFieldSupported)
                    .map(field -> toPredicate(field, root, cb, filter))
                    .filter(Objects::nonNull)
                    .toArray(Predicate[]::new);
            return cb.and(predicates);
        };
    }

    private boolean isFieldSupported(@NonNull Field field) {
        return !(field.isAnnotationPresent(Ignore.class) || SERIAL_VERSION_UID_NAME.equals(field.getName()));
    }

    @Nullable
    private <F, S> Predicate toPredicate(@NonNull Field field, @NonNull Root<S> root, @NonNull CriteriaBuilder cb, @NonNull F filter) {
        Object fieldValue = Utils.getFieldValue(field, filter);
        if (Utils.isNotNullAndNotBlank(fieldValue)) {
            String fieldName = getFieldName(field);
            return toPredicate(root, cb, field, fieldName, fieldValue);
        }
        return null;
    }

    @NonNull
    private String getFieldName(@NonNull Field field) {
        return field.isAnnotationPresent(Name.class)
                ? field.getAnnotation(Name.class).value()
                : field.getName();
    }

    @NonNull
    private <S> Predicate toPredicate(@NonNull Root<S> root, @NonNull CriteriaBuilder cb,
                                      @NonNull Field field, @NonNull String fieldName,
                                      @NonNull Object fieldValue) {
        Expression expression = ExpressionBuilder.getExpression(root, field, fieldName);
        PredicateBuilder predicateBuilder = PredicateBuilderFactory.createPredicateBuilder(cb, field, fieldValue);
        return predicateBuilder.build(expression);
    }
}