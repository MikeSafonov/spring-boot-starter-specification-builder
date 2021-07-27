package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

/**
 *
 * @author MikeSafonov
 */
class GreaterThanPredicateBuilderTest {
    @Test
    void shouldCallGreaterThanOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        Expression valueExpression = mock(Expression.class);
        GreaterThanPredicateBuilder builder = new GreaterThanPredicateBuilder(cb, valueExpression, expression);

        builder.build();

        verify(cb).greaterThan(expression, valueExpression);
        verifyNoMoreInteractions(expression);
    }
}
