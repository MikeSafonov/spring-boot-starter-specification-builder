package com.github.mikesafonov.specification.builder.starter.base.cars;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author MikeSafonov
 */
@Data
@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
}
