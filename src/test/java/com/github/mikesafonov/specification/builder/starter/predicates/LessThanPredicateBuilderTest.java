package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

/**
 *
 * @author MikeSafonov
 */
class LessThanPredicateBuilderTest {
    @Test
    void shouldCallLessThanOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        Expression valueExpression = mock(Expression.class);
        LessThanPredicateBuilder builder = new LessThanPredicateBuilder(cb, valueExpression, expression);

        builder.build();

        verify(cb).lessThan(expression, valueExpression);
        verifyNoMoreInteractions(expression);
    }
}
