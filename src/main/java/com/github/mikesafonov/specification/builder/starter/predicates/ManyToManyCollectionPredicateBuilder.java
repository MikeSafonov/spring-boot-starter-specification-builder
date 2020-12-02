package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The predicate is constructed according to the following scheme:
 * for each element of the collection a subquery is created that checks the existence of an
 * entry in the intersection table for the link between the related and target table.
 *
 * @param <T>
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class ManyToManyCollectionPredicateBuilder<T> implements PredicateBuilder {

    private final Root<T> root;
    private final CriteriaBuilder cb;
    private final CriteriaQuery<?> cq;
    private final FieldWithValue field;
    private final ExpressionBuilder expressionBuilder;

    @Override
    public Predicate build() {
        Collection collection = field.getValueAsCollection();
        List<Predicate> predicates = new ArrayList<>();
        for (Object filter : collection) {
            Subquery<T> sq = cq.subquery((Class<T>) root.getJavaType());
            Root<T> project = sq.from((Class<T>) root.getJavaType());

            Expression<T> expr = expressionBuilder.getExpressionForSubquery(project, field, cb.equal(project, root));
            sq.select(project).where(cb.equal(expr, filter));
            predicates.add(cb.exists(sq));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
