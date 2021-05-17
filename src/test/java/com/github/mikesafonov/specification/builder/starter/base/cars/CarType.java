package com.github.mikesafonov.specification.builder.starter.base.cars;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author MikeSafonov
 */
@Data
@Entity
@Table(name = "car_types")
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;
}
