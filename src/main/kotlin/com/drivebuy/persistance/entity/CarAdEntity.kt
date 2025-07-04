package com.drivebuy.persistance.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max

@Entity
@Table(name = "ads")
data class CarAdEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: String,

    @Column(nullable = false)
    var make: String,

    @Column(nullable = false)
    var model: String,

    var title: String,

    var description: String,

    @field:Min(1900)
    @field:Max(value = Int.MAX_VALUE.toLong())
    var year: Int,

    var color: String,

    @field:Min(1)
    @field:Max(10000)
    var hp: Int,

    @field:Min(1)
    @field:Max(100000)
    var displacement: Int,

    @field:Min(1)
    var mileage: Int,

    @field:Min(1)
    var price:Int,

    var bodyType: String,

    var condition: String,

    var doorCount: String,

    val cylinderCount: String,

    val transmissionType: String,

    val fuelType: String,

    val steeringPosition: String,

    @field:Min(0)
    var ownerCount: Int,

    var phone: String,

    var region: String,

    var city: String,

    val imageUrls: MutableList<String> = mutableListOf(),

    var features: MutableList<String> = mutableListOf()
) {
    init {
        val currentYear = java.time.Year.now().value
        require(year in 1900..currentYear) {
            "Year must be between 1900 and $currentYear"
        }
    }
}