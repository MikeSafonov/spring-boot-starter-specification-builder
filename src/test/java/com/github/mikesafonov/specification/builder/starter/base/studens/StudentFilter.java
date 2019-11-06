package com.github.mikesafonov.specification.builder.starter.base.studens;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.ManyToManyCollection;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;

import java.util.List;

public class StudentFilter {
    @ManyToManyCollection
    @Join(value = "classEntities")
    @Name(value = "name")
    private List<String> classes;

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }
}
