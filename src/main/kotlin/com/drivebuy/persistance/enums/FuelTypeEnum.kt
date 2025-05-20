package com.drivebuy.persistance.enums

enum class FuelTypeEnum(val typeName: String) {
    PETROL("Бензин"), DIESEL("Дизел"),
    LPG("Газ/Бензин"), CNG("Метан/Бензин"),
    HYDROGEN("Водород"), ELECTRIC("Електричество"), HYBRID("Хибрид")
}
