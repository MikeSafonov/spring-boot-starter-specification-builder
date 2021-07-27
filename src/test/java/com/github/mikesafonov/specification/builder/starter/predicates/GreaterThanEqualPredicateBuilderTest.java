package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

/**
 *
 * @author MikeSafonov
 */
class GreaterThanEqualPredicateBuilderTest {
    @Test
    void shouldCallGreaterThanEqualOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        Expression valueExpression = mock(Expression.class);
        GreaterThanEqualPredicateBuilder builder = new GreaterThanEqualPredicateBuilder(cb, valueExpression, expression);

        builder.build();

        verify(cb).greaterThanOrEqualTo(expression, valueExpression);
        verifyNoMoreInteractions(expression);
    }
}
