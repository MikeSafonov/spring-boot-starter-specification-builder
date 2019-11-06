package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class GreaterThanEqualPredicateBuilderTest {
    @Test
    void shouldCallGreaterThanEqualOnExpression() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        String value = "Some value";
        Expression expression = mock(Expression.class);
        GreaterThanEqualPredicateBuilder builder = new GreaterThanEqualPredicateBuilder(cb, value,expression);

        builder.build();

        verify(cb).greaterThanOrEqualTo(expression, value);
        verifyNoMoreInteractions(expression);
    }
}
