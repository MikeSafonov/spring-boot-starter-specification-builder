package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.annotations.SegmentIntersection;
import com.github.mikesafonov.specification.builder.starter.base.cars.CarFilter;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SegmentIntersectionPredicateBuilderTest {

    private SegmentFilter<Integer> filter;
    private Root root;
    private Field field;
    private SegmentIntersection segmentIntersection;
    private CriteriaBuilder cb;
    private ExpressionBuilder expressionBuilder;
    private Expression fromField;
    private Expression toField;
    private Expression fromExpression;
    private Expression toExpression;

    private SegmentIntersectionPredicateBuilder<Integer> predicateBuilder;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        root = mock(Root.class);
        field = CarFilter.class.getDeclaredField("costFilter");
        segmentIntersection = mock(SegmentIntersection.class);
        cb = mock(CriteriaBuilder.class);
        expressionBuilder = mock(ExpressionBuilder.class);
        fromField = mock(Expression.class);
        toField = mock(Expression.class);
        fromExpression = mock(Expression.class);
        toExpression = mock(Expression.class);
        String fromFieldName = "fromField";
        String toFieldName = "toField";

        when(segmentIntersection.fromField()).thenReturn(fromFieldName);
        when(expressionBuilder.getExpression(root, field, fromFieldName)).thenReturn(fromField);
        when(segmentIntersection.toField()).thenReturn(toFieldName);
        when(expressionBuilder.getExpression(root, field, toFieldName)).thenReturn(toField);
    }

    @Nested
    class WhenFilterSegmentBoundedFromEnd {

        @BeforeEach
        void setUp() {
            filter = new SegmentFilter<>(null, 1);

            when(cb.literal(filter.getTo())).thenReturn(toExpression);
        }

        @Test
        void shouldReturnLessThenEqualsPredicate() {
            Predicate lessThanEqualsPredicate = new LessThanEqualPredicateBuilder(cb, toExpression, fromField).build();

            predicateBuilder = new SegmentIntersectionPredicateBuilder<>(
                root,
                field,
                filter,
                segmentIntersection,
                cb,
                expressionBuilder
            );

            Predicate predicate = predicateBuilder.build();

            assertThat(predicate).isEqualTo(lessThanEqualsPredicate);
        }
    }

    @Nested
    class WhenFilterSegmentBoundedFromStart {

        @BeforeEach
        void setUp() {
            filter = new SegmentFilter<>(1, null);

            when(cb.literal(filter.getFrom())).thenReturn(fromExpression);
        }

        @Test
        void shouldReturnIsNullOrGreaterThenEqualsPredicate() {
            Predicate toFieldIsNull = mock(Predicate.class);
            Predicate orPredicate = mock(Predicate.class);
            Predicate greaterThanEqualsPredicate = new GreaterThanEqualPredicateBuilder(cb, fromExpression, toField).build();

            when(toField.isNull()).thenReturn(toFieldIsNull);
            when(cb.or(toFieldIsNull, greaterThanEqualsPredicate)).thenReturn(orPredicate);

            predicateBuilder = new SegmentIntersectionPredicateBuilder<>(
                root,
                field,
                filter,
                segmentIntersection,
                cb,
                expressionBuilder
            );

            Predicate predicate = predicateBuilder.build();

            assertThat(predicate).isEqualTo(orPredicate);
        }
    }

    @Nested
    class WhenFilterSegmentFullBounded {

        @BeforeEach
        void setUp() {
            filter = new SegmentFilter<>(1, 2);

            when(cb.literal(filter.getFrom())).thenReturn(fromExpression);
            when(cb.literal(filter.getTo())).thenReturn(toExpression);
        }

        @Test
        void shouldReturnIntersectionPredicate() {
            Predicate fieldFromLessThanEqualsFilterFromPredicate
                = new LessThanEqualPredicateBuilder(cb, fromExpression, fromField).build();
            Predicate toFieldIsNull = mock(Predicate.class);
            Predicate fieldToGreaterThanEqualsFilterFromPredicate
                = new GreaterThanEqualPredicateBuilder(cb, fromExpression, toField).build();
            Predicate entitySegmentIncludesFilterToPredicate = mock(Predicate.class);
            Predicate filterSegmentRightOffsetPredicate = mock(Predicate.class);
            Predicate fieldFromGreaterThanEqualsFilterFromPredicate
                = new GreaterThanEqualPredicateBuilder(cb, fromExpression, fromField).build();
            Predicate fieldFromLessThanEqualsFilterToPredicate
                = new LessThanEqualPredicateBuilder(cb, toExpression, fromField).build();
            Predicate filterSegmentLeftOffsetPredicate = mock(Predicate.class);
            Predicate orPredicate = mock(Predicate.class);

            when(toField.isNull()).thenReturn(toFieldIsNull);
            when(cb.or(toFieldIsNull, fieldToGreaterThanEqualsFilterFromPredicate))
                .thenReturn(entitySegmentIncludesFilterToPredicate);
            when(cb.and(fieldFromLessThanEqualsFilterFromPredicate, entitySegmentIncludesFilterToPredicate))
                .thenReturn(filterSegmentRightOffsetPredicate);
            when(cb.and(fieldFromGreaterThanEqualsFilterFromPredicate, fieldFromLessThanEqualsFilterToPredicate))
                .thenReturn(filterSegmentLeftOffsetPredicate);
            when(cb.or(filterSegmentRightOffsetPredicate, filterSegmentLeftOffsetPredicate))
                .thenReturn(orPredicate);

            predicateBuilder = new SegmentIntersectionPredicateBuilder<>(
                root,
                field,
                filter,
                segmentIntersection,
                cb,
                expressionBuilder
            );

            Predicate predicate = predicateBuilder.build();

            assertThat(predicate).isEqualTo(orPredicate);
        }
    }
}