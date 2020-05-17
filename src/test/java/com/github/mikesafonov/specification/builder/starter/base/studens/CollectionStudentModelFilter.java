package com.github.mikesafonov.specification.builder.starter.base.studens;

import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

import java.util.List;

/**
 * @author Mike Safonov
 */
@Data
public class CollectionStudentModelFilter {
    @Name("name")
    private List<String> names;
}
