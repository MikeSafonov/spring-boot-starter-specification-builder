package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class LessThanEqualPredicateBuilderTest {
    @Test
    void shouldCallLessThanEqualOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        String value = "Some value";
        Expression expression = mock(Expression.class);
        LessThanEqualPredicateBuilder builder = new LessThanEqualPredicateBuilder(cb, value, expression);

        builder.build();

        verify(cb).lessThanOrEqualTo(expression, value);
        verifyNoMoreInteractions(expression);
    }
}
