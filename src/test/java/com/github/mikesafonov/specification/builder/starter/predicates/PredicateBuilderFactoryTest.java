package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.base.cars.CarFilter;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 *
 * @author MikeSafonov
 */
class PredicateBuilderFactoryTest {

    @Test
    void shouldReturnCollectionPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        String fieldName = "collection";
        Field field = CarFilter.class.getDeclaredField(fieldName);
        Object value = new ArrayList<>();
        Root root = mock(Root.class);
        CriteriaQuery cq = mock(CriteriaQuery.class);
        PredicateBuilder builder = new PredicateBuilderFactory().createPredicateBuilder(root, cb, cq, new FieldWithValue(field, value));

        assertThat(builder).isInstanceOf(CollectionPredicateBuilder.class);
    }

    @Test
    void shouldReturnManyToManyCollectionPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("manyToManyCollection",  new ArrayList<>()))
            .isInstanceOf(ManyToManyCollectionPredicateBuilder.class);
    }

    @Test
    void shouldReturnLikePredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("likeValue")).isInstanceOf(LikePredicateBuilder.class);
    }

    @Test
    void shouldReturnNotNullPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("nonNullValue")).isInstanceOf(NotNullPredicateBuilder.class);
    }

    @Test
    void shouldReturnNullPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("nullValue")).isInstanceOf(NullPredicateBuilder.class);
    }

    @Test
    void shouldReturnEqualsPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("id")).isInstanceOf(EqualsPredicateBuilder.class);
    }

    @Test
    void shouldReturnGreaterThanEqualsPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("idGreaterThanEqual")).isInstanceOf(GreaterThanEqualPredicateBuilder.class);
    }

    @Test
    void shouldReturnGreaterThanPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("idGreaterThan")).isInstanceOf(GreaterThanPredicateBuilder.class);
    }

    @Test
    void shouldReturnLessThanEqualsPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("idLessThanEqual")).isInstanceOf(LessThanEqualPredicateBuilder.class);
    }

    @Test
    void shouldReturnSegmentIntersectionPredicateBuilder() throws NoSuchFieldException {
        assertThat(createBuilderForField("costFilter", new SegmentFilter<>(1, 5)))
            .isInstanceOf(SegmentIntersectionPredicateBuilder.class);
    }

    private PredicateBuilder createBuilderForField(String fieldName) throws NoSuchFieldException {
       return createBuilderForField(fieldName, "");
    }

    private PredicateBuilder createBuilderForField(String fieldName, Object value) throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Root root = mock(Root.class);
        CriteriaQuery cq = mock(CriteriaQuery.class);

        Field field = CarFilter.class.getDeclaredField(fieldName);
        return new PredicateBuilderFactory().createPredicateBuilder(root, cb, cq, new FieldWithValue(field, value));
    }
}
