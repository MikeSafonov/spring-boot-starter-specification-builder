package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Joins;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

/**
 * @author MikeSafonov
 */
public class ExpressionBuilder {

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
        return getFrom(root, field, restrictions).get(attributeName);
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
        From<?, ?> from = getFrom(root, field.getField());
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
    private <E> From<?, ?> getFrom(@NonNull Root<E> root,
                                   @NonNull Field field,
                                   @NonNull Predicate... restrictions) {
        if (field.isAnnotationPresent(Join.class) || field.isAnnotationPresent(Joins.class)) {
            return getJoin(root, field, restrictions);
        }
        return root;
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
        javax.persistence.criteria.Join<?, ?> join = root.join(joins[0].value(), joins[0].type()).on(restrictions);
        for (int i = 1; i < joins.length; i++) {
            join = join.join(joins[i].value(), joins[i].type());
        }
        return join;
    }
}
