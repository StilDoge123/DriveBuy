package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
@Table(name = "countries")
data class Country(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String
)

@Entity
@Table(name = "regions")
data class Region(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "region_name", nullable = false, unique = true)
    var regionName: String
)

@Entity
@Table(name = "cities")
data class City(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    var region: Region,
    var cityName: String
)