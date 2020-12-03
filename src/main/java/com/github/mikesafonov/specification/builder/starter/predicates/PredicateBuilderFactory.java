package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.*;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
public class PredicateBuilderFactory {

    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder();

    @org.springframework.lang.NonNull
    public <T> PredicateBuilder createPredicateBuilder(@org.springframework.lang.NonNull FieldWithValue field) {

        if (field.isAnnotatedBy(Names.class)) {
            Names names = field.getAnnotation(Names.class);
            if (names.type() == Names.SearchType.AND) {
                return new AndPredicateBuilder(field, f -> getSimplePredicateBuilder(expressionBuilder, f));
            } else {
                return new OrPredicateBuilder(field, f -> getSimplePredicateBuilder(expressionBuilder, f));
            }
        }

        if (field.isAnnotatedBy(ManyToManyCollection.class) && field.isValueCollection()) {
            return new ManyToManyCollectionPredicateBuilder<>(expressionBuilder, field);
        }

        if (field.isAnnotatedBy(SegmentIntersection.class) && field.isValueSegmentFilter()) {
            return new SegmentIntersectionPredicateBuilder<>(
                field,
                expressionBuilder
            );
        }

        return getSimplePredicateBuilder(expressionBuilder, field);
    }

    private PredicateBuilder getSimplePredicateBuilder(ExpressionBuilder expressionBuilder, FieldWithValue field) {

        SimplePredicateBuilder builder = createBuilder(expressionBuilder, field);
        if (field.isAnnotatedBy(Not.class)) {
            return new NotPredicateBuilder(builder);
        }
        return builder;
    }

    private SimplePredicateBuilder createBuilder(ExpressionBuilder expressionBuilder, FieldWithValue field) {
        if (field.isAnnotatedBy(IsNull.class)) {
            return new NullPredicateBuilder(expressionBuilder, field);
        }
        if (field.isAnnotatedBy(NonNull.class)) {
            return new NotNullPredicateBuilder(expressionBuilder, field);
        }
        if (field.isValueCollection()) {
            return new CollectionPredicateBuilder(expressionBuilder, field);
        }
        if (field.isAnnotatedBy(Like.class)) {
            return new LikePredicateBuilder(expressionBuilder, field, field.getAnnotation(Like.class));
        } else if (field.isAnnotatedBy(GreaterThan.class)) {
            return new GreaterThanPredicateBuilder(expressionBuilder, field);
        } else if (field.isAnnotatedBy(GreaterThanEqual.class)) {
            return new GreaterThanEqualPredicateBuilder(expressionBuilder, field);
        } else if (field.isAnnotatedBy(LessThan.class)) {
            return new LessThanPredicateBuilder(expressionBuilder, field);
        } else if (field.isAnnotatedBy(LessThanEqual.class)) {
            return new LessThanEqualPredicateBuilder(expressionBuilder, field);
        }
        return new EqualsPredicateBuilder(expressionBuilder, field);
    }
}
