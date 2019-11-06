package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class LessThanPredicateBuilderTest {
    @Test
    void shouldCallLessThanOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        String value = "Some value";
        Expression expression = mock(Expression.class);
        LessThanPredicateBuilder builder = new LessThanPredicateBuilder(cb, value, expression);

        builder.build();

        verify(cb).lessThan(expression, value);
        verifyNoMoreInteractions(expression);
    }
}
