package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class EqualsPredicateBuilderTest {
    @Test
    void shouldCallEqualsOnExpression(){
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Object value = "Some value";
        Expression expression = mock(Expression.class);
        EqualsPredicateBuilder builder = new EqualsPredicateBuilder(cb, value);

        builder.build(expression);

        verify(cb).equal(expression, value);
        verifyNoMoreInteractions(expression);
    }
}
