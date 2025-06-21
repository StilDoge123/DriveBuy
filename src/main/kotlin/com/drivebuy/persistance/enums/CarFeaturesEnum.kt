package com.drivebuy.persistance.enums

enum class CarFeaturesEnum(val featureName: String) {
    AIR_CONDITIONER("Климатик"), CLIMATE_CONTROL("Климатроник"), TINTED_WINDOWS("Затъмнени стъкла"),
    ELECTRIC_WINDOWS("Ел. стъкла"), ELECTRIC_MIRRORS("Ел. огледала"), ELECTRIC_SEATS("Ел. седалки"),
    HEATED_SEATS("Подгрев на седалки"), VENTILATED_SEATS("Вентилирани седалки"),
    HEATED_REAR_SEATS("Подгрев на задни седалки"), VENTILATED_REAR_SEATS("Вентилирани задни седалки"),
    MASSAGING_SEATS("Масажиращи седалки"), SPORT_SEATS("Спортни седалки"),
    HEATED_STEERING_WHEEL("Подгрев на волана"), HEATED_MIRRORS("Подгрев на огледалата"),
    MULTIFUNCTIONAL_STEERING_WHEEL("Мултифункционален волан"), PARKING_SENSORS("Парктроник"),
    REAR_CAMERA("Задна камера"), FRONT_REAR_CAMERA("Предна и задна камера"), FULL_CAMERA("360° камера"),
    BLUETOOTH("Блутут"), WIRED_CARPLAY("Кабелен Apple Carplay"), WIRED_ANDROID_AUTO("Кабелен Android Auto"),
    WIRELESS_CARPLAY("Безжичен Apple Carplay"), WIRELESS_ANDROID_AUTO("Безжичен Android Auto"),
    GPS_NAVIGATION("Навигация"), PANORAMIC_SUNROOF("Панорамен покрив"), SUNROOF("Шибедах"),
    CRUISE_CONTROL("Круиз контрол"), ADAPTIVE_CRUISE_CONTROL("Адаптивен круиз контрол"),
    ABS("ABS"), ESC("ESC"), TC("Тракшън контрол"), ALARM("Аларма"), IMMOBILISER("Имобилайзер"),
    CENTRAL_LOCKING("Централно заключване"), AIRBAGS("Въздушни възглавници"), POWER_STEERING("Серво усилване на волана"),
    KEYLESS_ENTRY("Безключов достъп"), ALLOY_WHEELS("Алуминиеви джанти"), TOW_BAR("Теглич"), TAXI("Такси"),
    SPARE_TYRE("Резервна гума"), CARJACK("Крик"), SEVEN_SEATS("7 Места"),
    LEATHER_INTERIOR("Кожен салон")
}
