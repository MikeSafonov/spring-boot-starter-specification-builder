package com.github.mikesafonov.specification.builder.starter.base.studens;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author MikeSafonov
 */
@Data
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_classes",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_class"))
    private Set<ClassEntity> classEntities;

    @Column(name = "date_start_studying")
    private LocalDate studyingDateStart;

    @Column(name = "date_end_studying")
    private LocalDate studyingDateEnd;
}
