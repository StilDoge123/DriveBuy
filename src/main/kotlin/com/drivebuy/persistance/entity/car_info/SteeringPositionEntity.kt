package com.drivebuy.persistance.entity.car_info

import jakarta.persistence.*

@Entity
@Table(name = "steering_positions")
class SteeringPositionEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    var steeringPositionName: String
)