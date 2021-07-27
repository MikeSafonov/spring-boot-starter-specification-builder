package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.reflect.annotation.AnnotationParser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 *
 * @author MikeSafonov
 */
class LikePredicateBuilderTest {

    private static final String PERCENT = "%";

    private CriteriaBuilder cb;
    private Expression expression;
    private Expression valueExpression;

    @BeforeEach
    void setUp() {
        cb = mock(CriteriaBuilder.class);
        expression = mock(Expression.class);
        valueExpression = mock(Expression.class);

        when(valueExpression.as(String.class)).thenReturn(valueExpression);
        when(expression.as(String.class)).thenReturn(expression);
    }

    @Test
    void shouldCallLikeCaseSensitiveAround() {
        Expression leftLikeExpression = mock(Expression.class);
        Expression aroundLikeExpression = mock(Expression.class);
        Like like = getLike(Like.DIRECTION.AROUND, true);

        when(cb.concat(PERCENT, valueExpression)).thenReturn(leftLikeExpression);
        when(cb.concat(leftLikeExpression, PERCENT)).thenReturn(aroundLikeExpression);

        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, valueExpression, expression);
        builder.build();

        verify(cb).like(expression, aroundLikeExpression);
    }

    @Test
    void shouldCallLikeCaseSensitiveLeft() {
        Expression leftLikeExpression = mock(Expression.class);
        Like like = getLike(Like.DIRECTION.LEFT, true);

        when(cb.concat(PERCENT, valueExpression)).thenReturn(leftLikeExpression);

        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, valueExpression, expression);
        builder.build();

        verify(cb).like(expression, leftLikeExpression);
    }

    @Test
    void shouldCallLikeCaseSensitiveRight() {
        Expression rightLikeExpression = mock(Expression.class);
        Like like = getLike(Like.DIRECTION.RIGHT, true);

        when(cb.concat(valueExpression, PERCENT)).thenReturn(rightLikeExpression);

        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, valueExpression, expression);
        builder.build();

        verify(cb).like(expression, rightLikeExpression);
    }

    @Test
    void shouldCallLikeCaseSensitiveNone() {
        Like like = getLike(Like.DIRECTION.NONE, true);

        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, valueExpression, expression);
        builder.build();

        verify(cb).like(expression, valueExpression);
    }

    @Test
    void shouldCallLikeUpper() {
        Expression rightLikeExpression = mock(Expression.class);
        Expression upperValueExpression = mock(Expression.class);
        Expression upperFieldExpression = mock(Expression.class);
        Like like = getLike(Like.DIRECTION.RIGHT, false);

        when(cb.concat(valueExpression, PERCENT)).thenReturn(rightLikeExpression);
        when(cb.upper(rightLikeExpression)).thenReturn(upperValueExpression);
        when(cb.upper(expression)).thenReturn(upperFieldExpression);

        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, valueExpression, expression);
        builder.build();

        verify(cb).like(upperFieldExpression, upperValueExpression);
    }

    private Like getLike(Like.DIRECTION direction, boolean caseSensitive) {
        Map<String, Object> annotationParameters = new HashMap<>();
        annotationParameters.put("direction", direction);
        annotationParameters.put("caseSensitive", caseSensitive);

        return (Like) AnnotationParser.annotationForMap(Like.class, annotationParameters);
    }

}
