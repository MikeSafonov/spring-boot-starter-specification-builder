package com.github.mikesafonov.specification.builder.starter.base.studens;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.ManyToManyCollection;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

import java.util.List;

/**
 *
 * @author MikeSafonov
 */
@Data
public class StudentFilter {
    @ManyToManyCollection
    @Join(value = "classEntities")
    @Name(value = "name")
    private List<String> classes;
}
