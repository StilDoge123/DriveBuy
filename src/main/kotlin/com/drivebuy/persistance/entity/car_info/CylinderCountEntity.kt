package com.drivebuy.persistance.entity.car_info

import jakarta.persistence.*

@Entity
@Table(name = "cylinder_counts")
class CylinderCountEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val cylinderCount: String
)