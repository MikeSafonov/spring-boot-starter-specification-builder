package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

/**
 *
 * @author MikeSafonov
 */
class EqualsPredicateBuilderTest {
    @Test
    void shouldCallEqualsOnExpression(){
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        Expression valueExpression = mock(Expression.class);
        EqualsPredicateBuilder builder = new EqualsPredicateBuilder(cb, valueExpression, expression);

        builder.build();

        verify(cb).equal(expression, valueExpression);
        verifyNoMoreInteractions(expression);
    }
}
