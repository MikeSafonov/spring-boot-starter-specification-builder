package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class GreaterThanPredicateBuilderTest {
    @Test
    void shouldCallGreaterThanOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        String value = "Some value";
        Expression expression = mock(Expression.class);
        GreaterThanPredicateBuilder builder = new GreaterThanPredicateBuilder(cb, value, expression);

        builder.build();

        verify(cb).greaterThan(expression, value);
        verifyNoMoreInteractions(expression);
    }
}
