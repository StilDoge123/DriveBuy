package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
@Table(name = "countries")
data class Country(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val name: String
)

@Entity
@Table(name = "regions")
data class Region(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false) val country: Country,
    val name: String
)

@Entity
@Table(name = "cities")
data class City(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false) val region: Region,
    val name: String
)