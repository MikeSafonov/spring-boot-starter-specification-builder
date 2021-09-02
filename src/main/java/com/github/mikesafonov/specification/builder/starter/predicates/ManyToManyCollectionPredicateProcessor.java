package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.ManyToManyCollection;
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
public class ManyToManyCollectionPredicateProcessor implements PredicateProcessor {


    @Override
    public <T> void process(PredicateContainer container, FieldWithValue field, Root<T> root,
                            CriteriaBuilder cb, CriteriaQuery<?> cq, ExpressionBuilder expressionBuilder) {
        if (match(field)) {
            Collection collection = field.getValueAsCollection();
            List<Predicate> predicates = new ArrayList<>();
            for (Object filter : collection) {
                Subquery<T> sq = cq.subquery((Class<T>) root.getJavaType());
                Root<T> project = sq.from((Class<T>) root.getJavaType());

                Expression<T> expr = expressionBuilder.getExpressionForSubquery(project, field, cb.equal(project, root));
                sq.select(project).where(cb.equal(expr, filter));
                predicates.add(cb.exists(sq));
            }
            container.add(cb.and(predicates.toArray(new Predicate[0])));
        }

    }

    private boolean match(FieldWithValue field) {
        return field.isAnnotatedBy(ManyToManyCollection.class) && field.isValueCollection();
    }
}
