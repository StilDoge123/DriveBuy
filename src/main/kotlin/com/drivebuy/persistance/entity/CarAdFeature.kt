package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
data class CarAdFeature(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "car_ad_id")
    val carAd: CarAd,

    val featureName: String
)