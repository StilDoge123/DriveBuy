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
    var brand: CarBrandEntity,

    @Column(nullable = false)
    var modelName: String
)