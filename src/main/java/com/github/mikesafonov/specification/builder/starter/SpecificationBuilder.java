package com.github.mikesafonov.specification.builder.starter;


import com.github.mikesafonov.specification.builder.starter.annotations.Ignore;
import com.github.mikesafonov.specification.builder.starter.annotations.IsNull;
import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    private final PredicateBuilderFactory factory = new PredicateBuilderFactory();

    public <F, S> Specification<S> buildSpecification(@NonNull F filter) {
        List<Field> fields = Utils.getFields(filter);
        return (root, query, cb) -> {
            Predicate[] predicates = fields.stream()
                .filter(this::isFieldSupported)
                .map(field -> toPredicate(field, root, cb, query, filter))
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);
            return cb.and(predicates);
        };
    }

    private boolean isFieldSupported(@NonNull Field field) {
        return !(field.isAnnotationPresent(Ignore.class) || SERIAL_VERSION_UID_NAME.equals(field.getName()));
    }

    @Nullable
    private <F, S> Predicate toPredicate(@NonNull Field field,
                                         @NonNull Root<S> root,
                                         @NonNull CriteriaBuilder cb,
                                         @NonNull CriteriaQuery<?> cq,
                                         @NonNull F filter) {
        Object fieldValue = Utils.getFieldValue(field, filter);
        if (Utils.isNotNullAndNotBlank(fieldValue) || field.isAnnotationPresent(IsNull.class)
            || field.isAnnotationPresent(com.github.mikesafonov.specification.builder.starter.annotations.NonNull.class)) {
            return toPredicate(root, cb, cq, new FieldWithValue(field, fieldValue));
        }
        return null;
    }

    @NonNull
    private <S> Predicate toPredicate(@NonNull Root<S> root, @NonNull CriteriaBuilder cb,
                                      @NonNull CriteriaQuery<?> cq, @NonNull FieldWithValue field) {
        return factory.createPredicateBuilder(root, cb, cq, field).build();
    }
}
