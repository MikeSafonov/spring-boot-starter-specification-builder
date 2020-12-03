package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.Names;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.reflect.annotation.AnnotationParser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.*;

/**
 * @author Mike Safonov
 */
class OrPredicateBuilderTest {
    private CriteriaBuilder cb;
    private Root root;
    private CriteriaQuery criteriaQuery;
    private ExpressionBuilder expressionBuilder;
    private FieldWithValue fieldWithValue;
    private Names names;

    @BeforeEach
    void setUp() {
        cb = mock(CriteriaBuilder.class);
        root = mock(Root.class);
        criteriaQuery = mock(CriteriaQuery.class);
        expressionBuilder = mock(ExpressionBuilder.class);
        Object value = "some value";
        Field field = mock(Field.class);
        names = getNames();
        when(field.getAnnotationsByType(Names.class)).thenReturn(new Names[]{names});

        fieldWithValue = new FieldWithValue(field, value);
    }

    @Test
    void shouldCallAndOnExpressionsArray() {

        Function<FieldWithValue, PredicateBuilder> function = f -> new NullPredicateBuilder(expressionBuilder, f);

        PredicateBuilder[] expectedBuilders = new PredicateBuilder[]{
            new NullPredicateBuilder(expressionBuilder, new FieldWithValue(fieldWithValue, names.value()[0])),
            new NullPredicateBuilder(expressionBuilder, new FieldWithValue(fieldWithValue, names.value()[0]))
        };
        Predicate[] expected = new Predicate[]{
            expectedBuilders[0].build(root, criteriaQuery, cb),
            expectedBuilders[1].build(root, criteriaQuery, cb)
        };

        OrPredicateBuilder builder = new OrPredicateBuilder(fieldWithValue, function);

        builder.build(root, criteriaQuery, cb);

        verify(cb).or(expected);
    }

    private Names getNames() {
        Map<String, Object> annotationParameters = new HashMap<>();
        annotationParameters.put("value", new String[]{"one", "two"});
        annotationParameters.put("type", Names.SearchType.OR);

        return (Names) AnnotationParser.annotationForMap(Names.class, annotationParameters);
    }

}
