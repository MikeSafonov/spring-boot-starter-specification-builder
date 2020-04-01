package com.github.mikesafonov.specification.builder.starter.base.studens;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author MikeSafonov
 */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ClassEntity> getClassEntities() {
        return classEntities;
    }

    public void setClassEntities(Set<ClassEntity> classEntities) {
        this.classEntities = classEntities;
    }

    public LocalDate getStudyingDateStart() {
        return studyingDateStart;
    }

    public void setStudyingDateStart(LocalDate studyingDateStart) {
        this.studyingDateStart = studyingDateStart;
    }

    public LocalDate getStudyingDateEnd() {
        return studyingDateEnd;
    }

    public void setStudyingDateEnd(LocalDate studyingDateEnd) {
        this.studyingDateEnd = studyingDateEnd;
    }
}
