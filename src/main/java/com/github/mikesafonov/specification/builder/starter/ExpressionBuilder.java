package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Joins;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * @author MikeSafonov
 */
public class ExpressionBuilder {

    private final JoinBuilder joinBuilder = new JoinBuilder();
    private final Map<String, javax.persistence.criteria.Join<?, ?>> joinCache = new HashMap<>();

    @NonNull
    public <E> FieldWithValueExpression toFieldWithValueExpression(@NonNull FieldWithValue field,
                                                                   @NonNull CriteriaBuilder cb,
                                                                   @NonNull Root<E> root,
                                                                   @NonNull Predicate... restrictions) {
        return new FieldWithValueExpression(
            getExpression(root, field, restrictions),
            valueAsExpression(field, cb)
        );
    }

    @NonNull
    public <E> Expression valueAsExpression(@NonNull FieldWithValue field, @NonNull CriteriaBuilder cb) {
        return field.getValue() != null ? cb.literal(field.getValue()) : cb.nullLiteral(Object.class);
    }

    /**
     * @param root         entity root
     * @param field        field for expression
     * @param restrictions on restrictions
     * @param <E>          entity type
     * @return attribute expression from root or joined attribute expression via {@link Join}
     */
    @NonNull
    public <E> Expression getExpression(@NonNull Root<E> root, @NonNull FieldWithValue field,
                                        @NonNull Predicate... restrictions) {
        return getExpression(root, field.getField(), field.getFieldName(), restrictions);
    }

    /**
     * @param root         entity root
     * @param field        field for expression
     * @param restrictions on restrictions
     * @param <E>          entity type
     * @return attribute expression from root or joined attribute expression via {@link Join}
     */
    @NonNull
    public <E> Expression getExpressionForSubquery(@NonNull Root<E> root, @NonNull FieldWithValue field,
                                                   @NonNull Predicate... restrictions) {
        return getFrom(root, field.getField(), restrictions).get(field.getFieldName());
    }

    /**
     * @param root          entity root
     * @param field         field for expression
     * @param attributeName attribute name
     * @param restrictions  on restrictions
     * @param <E>           entity type
     * @return attribute expression from root or joined attribute expression via {@link Join}
     */
    @NonNull
    public <E> Expression getExpression(@NonNull Root<E> root, @NonNull Field field,
                                        @NonNull String attributeName, @NonNull Predicate... restrictions) {
        return getCachedFrom(root, field, restrictions).get(attributeName);
    }

    /**
     * @param root           entity root
     * @param field          field for expression
     * @param attributeNames array of attributes names
     * @param <E>            entity type
     * @return array of attributes expressions for given attribute names
     */
    @NonNull
    public <E> Expression[] getExpressions(Root<E> root, FieldWithValue field, String[] attributeNames) {
        Expression[] expressions = new Expression[attributeNames.length];
        From<?, ?> from = getCachedFrom(root, field.getField());
        for (int i = 0; i < attributeNames.length; i++) {
            expressions[i] = from.get(attributeNames[i]);
        }
        return expressions;
    }

    /**
     * Detects {@link From}. Returns root joined with field if annotation {@link Join} presents and root otherwise.
     *
     * @param root         entity root
     * @param field        field for expression
     * @param restrictions on restrictions
     * @param <E>          entity type
     * @return {@link From} for building expression
     */
    private <E> From<?, ?> getCachedFrom(@NonNull Root<E> root,
                                         @NonNull Field field,
                                         @NonNull Predicate... restrictions) {
        if (field.isAnnotationPresent(Join.class) || field.isAnnotationPresent(Joins.class)) {
            return getCachedJoin(root, field, restrictions);
        }
        return root;
    }

    /**
     * Detects {@link From}. Returns root joined with field if annotation {@link Join} presents and root otherwise.
     *
     * @param root         entity root
     * @param field        field for expression
     * @param restrictions on restrictions
     * @param <E>          entity type
     * @return {@link From} for building expression
     */
    private <E> From<?, ?> getFrom(@NonNull Root<E> root,
                                   @NonNull Field field,
                                   @NonNull Predicate... restrictions) {
        if (field.isAnnotationPresent(Join.class) || field.isAnnotationPresent(Joins.class)) {
            return getJoin(root, field, restrictions);
        }
        return root;
    }

    /**
     * Get {@link javax.persistence.criteria.Join} from cache or
     * creates {@link javax.persistence.criteria.Join} of root with field if absent
     *
     * @param root         entity root
     * @param field        field for expression
     * @param restrictions on restrictions
     * @param <E>          entity type
     * @return root joined with field
     */
    @NonNull
    private <E> javax.persistence.criteria.Join<?, ?> getCachedJoin(@NonNull Root<E> root,
                                                                    @NonNull Field field,
                                                                    @NonNull Predicate... restrictions) {

        Join[] joins = field.getAnnotationsByType(Join.class);
        String rootKey = buildKey(singletonList(joins[0]));
        javax.persistence.criteria.Join<?, ?> currentJoin =
            joinCache.computeIfAbsent(rootKey, s -> joinBuilder.buildRootJoin(joins[0], root, restrictions));
        List<Join> processedJoins = new ArrayList<>();
        processedJoins.add(joins[0]);
        for (int i = 1; i < joins.length; i++) {
            final int index = i;
            final javax.persistence.criteria.Join<?, ?> tmpCurrentJoin = currentJoin;
            processedJoins.add(joins[i]);
            String key = buildKey(processedJoins);
            currentJoin = joinCache.computeIfAbsent(key, s -> joinBuilder.buildJoin(joins[index], tmpCurrentJoin));
        }
        return currentJoin;
    }

    /**
     * Creates {@link javax.persistence.criteria.Join} of root with field
     *
     * @param root         entity root
     * @param field        field for expression
     * @param restrictions on restrictions
     * @param <E>          entity type
     * @return root joined with field
     */
    @NonNull
    private <E> javax.persistence.criteria.Join<?, ?> getJoin(@NonNull Root<E> root,
                                                              @NonNull Field field,
                                                              @NonNull Predicate... restrictions) {
        Join[] joins = field.getAnnotationsByType(Join.class);
        return joinBuilder.buildAllJoins(joins, root, restrictions);
    }

    private String buildKey(List<Join> joins) {
        return joins.stream()
            .map(join -> join.value() + "(" + join.type() + ")")
            .collect(Collectors.joining("."));
    }
}
