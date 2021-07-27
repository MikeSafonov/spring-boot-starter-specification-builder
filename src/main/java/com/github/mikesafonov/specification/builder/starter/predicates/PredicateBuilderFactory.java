package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.FieldWithValueExpression;
import com.github.mikesafonov.specification.builder.starter.FunctionWrapper;
import com.github.mikesafonov.specification.builder.starter.annotations.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
public class PredicateBuilderFactory {

    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder();
    private final FunctionWrapper functionWrapper = new FunctionWrapper();

    @org.springframework.lang.NonNull
    public <T> PredicateBuilder createPredicateBuilder(
        @org.springframework.lang.NonNull Root<T> root,
        @org.springframework.lang.NonNull CriteriaBuilder cb,
        @org.springframework.lang.NonNull CriteriaQuery<?> cq,
        @org.springframework.lang.NonNull FieldWithValue field) {

        if (field.isAnnotatedBy(Names.class)) {
            Names names = field.getAnnotation(Names.class);
            Expression[] expressions = expressionBuilder.getExpressions(root, field, names.value());
            if (names.type() == Names.SearchType.AND) {
                return new AndPredicateBuilder(cb, expressions,
                    expression -> getSimplePredicateBuilder(cb, field, expression));
            } else {
                return new OrPredicateBuilder(cb, expressions,
                    expression -> getSimplePredicateBuilder(cb, field, expression));
            }
        }

        if (field.isAnnotatedBy(ManyToManyCollection.class) && field.isValueCollection()) {
            return new ManyToManyCollectionPredicateBuilder<>(root, cb, cq, field, expressionBuilder);
        }

        if (field.isAnnotatedBy(SegmentIntersection.class) && field.isValueSegmentFilter()) {
            return new SegmentIntersectionPredicateBuilder<>(
                root,
                field.getField(),
                field.getValueAsSegmentFilter(),
                field.getAnnotation(SegmentIntersection.class),
                cb,
                expressionBuilder
            );
        }

        return getSimplePredicateBuilder(cb, field, expressionBuilder.getExpression(root, field));
    }

    private PredicateBuilder getSimplePredicateBuilder(CriteriaBuilder cb,
                                                       FieldWithValue field, Expression expression) {

        FieldWithValueExpression fieldWithValueExpression =
            new FieldWithValueExpression(
                expression,
                field.getValue() != null ? cb.literal(field.getValue()) : cb.nullLiteral(Object.class)
            );
        if (field.isAnnotatedBy(Function.class)) {
            fieldWithValueExpression =
                functionWrapper.wrapByFunction(cb, field.getAnnotation(Function.class), fieldWithValueExpression);
        }
        SimplePredicateBuilder builder = createBuilder(cb, field, fieldWithValueExpression);
        if (field.isAnnotatedBy(Not.class)) {
            return new NotPredicateBuilder(cb, builder);
        }
        return builder;
    }


    private SimplePredicateBuilder createBuilder(CriteriaBuilder cb, FieldWithValue field,
                                                 FieldWithValueExpression fieldWithValueExpression) {
        if (field.isAnnotatedBy(IsNull.class)) {
            return new NullPredicateBuilder(cb, fieldWithValueExpression.getFieldExpression());
        }
        if (field.isAnnotatedBy(NonNull.class)) {
            return new NotNullPredicateBuilder(cb, fieldWithValueExpression.getFieldExpression());
        }
        if (field.isValueCollection()) {
            return new CollectionPredicateBuilder(field.getValueAsCollection(),
                fieldWithValueExpression.getFieldExpression());
        }
        if (field.isAnnotatedBy(Like.class)) {
            return new LikePredicateBuilder(cb, field.getAnnotation(Like.class),
                fieldWithValueExpression.getValueExpression(),
                fieldWithValueExpression.getFieldExpression());
        } else if (field.isAnnotatedBy(GreaterThan.class)) {
            return new GreaterThanPredicateBuilder(cb, fieldWithValueExpression.getValueExpression(),
                fieldWithValueExpression.getFieldExpression());
        } else if (field.isAnnotatedBy(GreaterThanEqual.class)) {
            return new GreaterThanEqualPredicateBuilder(cb, fieldWithValueExpression.getValueExpression(),
                fieldWithValueExpression.getFieldExpression());
        } else if (field.isAnnotatedBy(LessThan.class)) {
            return new LessThanPredicateBuilder(cb, fieldWithValueExpression.getValueExpression(),
                fieldWithValueExpression.getFieldExpression());
        } else if (field.isAnnotatedBy(LessThanEqual.class)) {
            return new LessThanEqualPredicateBuilder(cb, fieldWithValueExpression.getValueExpression(),
                fieldWithValueExpression.getFieldExpression());
        }
        return new EqualsPredicateBuilder(cb, fieldWithValueExpression.getValueExpression(),
            fieldWithValueExpression.getFieldExpression());
    }
}
