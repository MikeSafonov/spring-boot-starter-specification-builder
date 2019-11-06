package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.base.CarFilter;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PredicateBuilderFactoryTest {

    @Test
    void shouldReturnCollectionPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("collection");
        Object value = new ArrayList<>();
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(CollectionPredicateBuilder.class);
    }

    @Test
    void shouldReturnLikePredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("likeValue");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(LikePredicateBuilder.class);
    }

    @Test
    void shouldReturnNotNullPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("nonNullValue");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(NotNullPredicateBuilder.class);
    }

    @Test
    void shouldReturnNullPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("nullValue");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(NullPredicateBuilder.class);
    }

    @Test
    void shouldReturnEqualsPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("id");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(EqualsPredicateBuilder.class);
    }

    @Test
    void shouldReturnGreaterThanEqualsPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("idGreaterThanEqual");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(GreaterThanEqualPredicateBuilder.class);
    }

    @Test
    void shouldReturnGreaterThanPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("idGreaterThan");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(GreaterThanPredicateBuilder.class);
    }

    @Test
    void shouldReturnLessThanEqualsPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("idLessThanEqual");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(LessThanEqualPredicateBuilder.class);
    }

    @Test
    void shouldReturnLessThanPredicateBuilder() throws NoSuchFieldException {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Field field = CarFilter.class.getDeclaredField("idLessThan");
        Object value = "";
        PredicateBuilder builder = PredicateBuilderFactory.createPredicateBuilder(cb, field, value);

        assertThat(builder).isInstanceOf(LessThanPredicateBuilder.class);
    }
}
