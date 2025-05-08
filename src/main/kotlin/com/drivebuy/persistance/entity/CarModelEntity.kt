package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
@Table(name = "car_model")
data class CarModelEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    val brand: CarBrandEntity,

    @Column(nullable = false)
    val modelName: String
)