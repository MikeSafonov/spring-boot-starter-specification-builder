package com.github.mikesafonov.specification.builder.starter.base.studens;

import com.github.mikesafonov.specification.builder.starter.annotations.Name;

import java.util.List;

/**
 * @author Mike Safonov
 */
public class CollectionStudentModelFilter {
    @Name("name")
    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
