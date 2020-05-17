package com.github.mikesafonov.specification.builder.starter.base.studens;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author MikeSafonov
 */
@Data
@Entity
@Table(name = "classes")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
}
