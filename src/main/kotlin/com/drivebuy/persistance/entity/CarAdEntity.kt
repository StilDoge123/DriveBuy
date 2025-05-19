package com.drivebuy.persistance.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "ads")
data class CarAdEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id")
    val userId: String
)

@Entity
data class CarAd(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: String,

    @Column(nullable = false)
    val make: String,

    @Column(nullable = false)
    val model: String,

    @field:Min(1900)
    @field:Max(value = Int.MAX_VALUE.toLong())
    val year: Int,

    val color: String,

    @field:Min(1)
    @field:Max(10000)
    val hp: Int,

    @field:Min(1)
    @field:Max(100000)
    val displacement: Int,

    @field:Min(1)
    val mileage: Int,

    @field:Min(1)
    val price:Int,

    @field:Positive
    val doorCount: Int,

    @field:Min(0)
    val ownerCount: Int,

    val phone: String,

    val location: String,

    @ManyToMany
    @JoinTable(
        name = "car_ad_features",
        joinColumns = [JoinColumn(name = "car_ad_id")],
        inverseJoinColumns = [JoinColumn(name = "car_feature_id")]
    )
    val features: List<CarAdFeature> = mutableListOf()
) {
    init {
        val currentYear = java.time.Year.now().value
        require(year in 1900..currentYear) {
            "Year must be between 1900 and $currentYear"
        }
    }
}