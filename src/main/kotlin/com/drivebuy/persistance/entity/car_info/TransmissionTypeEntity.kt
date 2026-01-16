package com.drivebuy.persistance.entity.car_info

import jakarta.persistence.*

@Entity
@Table(name = "transmission_types")
class TransmissionTypeEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    var transmissionTypeName: String
)