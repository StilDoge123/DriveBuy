package com.drivebuy.persistance.entity.car_info

import jakarta.persistence.*

@Entity
@Table(name = "car_conditions")
data class CarConditionEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val conditionName: String
)