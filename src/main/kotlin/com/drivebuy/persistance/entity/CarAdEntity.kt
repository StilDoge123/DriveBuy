package com.drivebuy.persistance.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max
import java.time.LocalDateTime

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

    @Column(nullable = true)
    var title: String?,

    @Column(nullable = true)
    var description: String?,

    @field:Min(1900)
    @field:Max(value = Int.MAX_VALUE.toLong())
    @Column(nullable = false)
    var year: Int,

    @Column(nullable = true)
    var color: String?,

    @field:Min(1)
    @field:Max(10000)
    @Column(nullable = false)
    var hp: Int,

    @field:Min(1)
    @field:Max(100000)
    @Column(nullable = false)
    var displacement: Int,

    @field:Min(1)
    @Column(nullable = false)
    var mileage: Int,

    @field:Min(1)
    @Column(nullable = false)
    var price: Int,

    @Column(nullable = true)
    var bodyType: String?,

    @Column(nullable = true)
    var condition: String?,

    @Column(nullable = true)
    var doorCount: String?,

    @Column(nullable = true)
    var cylinderCount: String?,

    @Column(nullable = true)
    var transmissionType: String?,

    @Column(nullable = true)
    var fuelType: String?,

    @Column(nullable = true)
    var steeringPosition: String?,

    @Column(nullable = true)
    var driveType: String?,

    @field:Min(0)
    @Column(nullable = false)
    var ownerCount: Int,

    @Column(nullable = true)
    var phone: String?,

    @Column(nullable = true)
    var region: String?,

    @Column(nullable = true)
    var city: String?,

    var imageUrls: MutableList<String> = mutableListOf(),

    var features: MutableList<String> = mutableListOf(),

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        val currentYear = java.time.Year.now().value
        require(year in 1900..currentYear) {
            "Year must be between 1900 and $currentYear"
        }
    }
}