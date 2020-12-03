package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mike Safonov
 */
public abstract class BasePredicateTest {

    protected CriteriaBuilder cb;
    protected Root root;
    protected CriteriaQuery criteriaQuery;
    protected ExpressionBuilder expressionBuilder;
    protected FieldWithValue fieldWithValue;
    protected Expression expression;

    @BeforeEach
    void setUp() {
        cb = mock(CriteriaBuilder.class);
        root = mock(Root.class);
        criteriaQuery = mock(CriteriaQuery.class);
        expressionBuilder = mock(ExpressionBuilder.class);
        Object value = "some value";
        Field field = mock(Field.class);

        fieldWithValue = new FieldWithValue(field, value);

        expression = mock(Expression.class);
        when(expressionBuilder.getExpression(root, fieldWithValue)).thenReturn(expression);
    }

}
