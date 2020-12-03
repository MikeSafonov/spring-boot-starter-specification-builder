package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.Names;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Mike Safonov
 */
@RequiredArgsConstructor
public class AndPredicateBuilder implements PredicateBuilder {

    private final FieldWithValue field;
    private final Function<FieldWithValue, PredicateBuilder> predicateBuilderFunction;

    @Override
    public Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        Names names = field.getAnnotation(Names.class);
        Predicate[] predicates = Arrays.stream(names.value())
            .map(s -> new FieldWithValue(field, s))
            .map(predicateBuilderFunction)
            .map(builder -> builder.build(root, q, cb))
            .toArray(Predicate[]::new);
        return cb.and(predicates);
    }
}
