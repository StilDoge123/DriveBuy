package com.drivebuy.persistance.dto

enum class SortOption(val fieldName: String) {
    PRICE_LOW_TO_HIGH("price"),
    PRICE_HIGH_TO_LOW("price"),
    YEAR_NEWEST_FIRST("year"),
    YEAR_OLDEST_FIRST("year"),
    MILEAGE_LOW_TO_HIGH("mileage"),
    MILEAGE_HIGH_TO_LOW("mileage"),
    DATE_NEWEST_FIRST("createdAt"),
    DATE_OLDEST_FIRST("createdAt"),
    HP_LOW_TO_HIGH("hp"),
    HP_HIGH_TO_LOW("hp");

    fun isAscending(): Boolean {
        return when (this) {
            PRICE_LOW_TO_HIGH, YEAR_OLDEST_FIRST, 
            MILEAGE_LOW_TO_HIGH, DATE_OLDEST_FIRST, HP_LOW_TO_HIGH -> true

            PRICE_HIGH_TO_LOW, YEAR_NEWEST_FIRST, 
            MILEAGE_HIGH_TO_LOW, DATE_NEWEST_FIRST, HP_HIGH_TO_LOW -> false
        }
    }
}
