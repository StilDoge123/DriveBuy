package com.drivebuy.persistance.entity

import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive

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

    @field:Positive
    var doorCount: Int,

    @field:Min(0)
    var ownerCount: Int,

    var phone: String,

    var location: String,

    @ElementCollection
    @CollectionTable(name = "ad_images", joinColumns = [JoinColumn(name = "ad_id")])
    @Column(name = "image_url")
    val imageUrls: MutableList<String> = mutableListOf(),

    @ManyToMany(targetEntity = CarFeaturesEntity::class)
    @JoinTable(
        name = "car_ad_features",
        joinColumns = [JoinColumn(name = "car_ad_id")],
        inverseJoinColumns = [JoinColumn(name = "car_feature_id")]
    )
    var features: MutableList<CarFeaturesEntity> = mutableListOf()
) {
    init {
        val currentYear = java.time.Year.now().value
        require(year in 1900..currentYear) {
            "Year must be between 1900 and $currentYear"
        }
    }
}