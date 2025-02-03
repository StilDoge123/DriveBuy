package com.drivebuy.persistance.enums

import java.io.File

fun generateBrandsCsv() {
    File("src/main/resources/csv/brands.csv").bufferedWriter().use { writer ->
        writer.write("id,name\n")
        CarBrand.entries.forEachIndexed { index, brand ->
            writer.write("${index + 1},${brand.name}\n")
        }
    }
}

fun generateModelsCsv() {
    File("src/main/resources/csv/models.csv").bufferedWriter().use { writer ->
        writer.write("brand_id,model_name\n")
        CarBrand.entries.forEachIndexed { index, brand ->
            val brandId = index + 1
            brand.models.forEach { model ->
                writer.write("$brandId,$model\n")
            }
        }
    }
}

fun main() {
    File("src/main/resources/csv").mkdirs()

    generateBrandsCsv()
    generateModelsCsv()
    println("CSV files generated successfully!")
}

enum class BodyType(val type: String) {
    SEDAN("Седан"), WAGON("Комби"),
    HATCHBACK("Хечбек"), COUPE("Купе"),
    CABRIO("Кабрио"), SUV("Джип"),
    VAN("Ван"), PICKUP("Пикап")
}

enum class TransmissionType(val type: String) {
    AUTOMATIC("Автоматична"), MANUAL("Ръчна")
}

enum class DoorCount(val count: String) {
    TWO("2/3"), FOUR("4/5")
}

enum class SteeringPosition(val position: String) {
    LEFT("Ляво"), RIGHT("Дясно")
}

enum class CarCondition(val type: String) {
    NEW("Нов"), USED("Използван"), WRECKED("Катастрофирал"), PARTS("За части")
}

enum class FuelType(val type: String) {
    PETROL("Бензин"), DIESEL("Дизел"),
    LPG("Газ/Бензин"), CNG("Метан/Бензин"),
    HYDROGEN("Водород"), ELECTRIC("Електричество"), HYBRID("Хибрид")
}

enum class CylinderCount(val count: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
    EIGHT(8), TEN(10), TWELVE(12), SIXTEEN(16)
}

enum class DriveType(val type: String) {
    FRONT("Предно"), REAR("Задно"), ALL_WHEEL_DRIVE("4x4")
}

enum class CarFeatures(val type: String) {
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
    SPARE_TYRE("Резервна гума"), CARJACK("Крик"), SEVEN_SEATS("7 Места")
}

enum class Color(val type: String) {
    RED("Червен"), BLUE("Син"), GREEN("Зелен"),
    YELLOW("Жълт"), BLACK("Черен"), WHITE("Бял"),
    GRAY("Сив"), SILVER("Сребрист"), GOLD("Златист"),
    ORANGE("Оранжев"), PURPLE("Лилав"), PINK("Розов"),
    BROWN("Кафяв"), BEIGE("Бежов"), BRONZE("Бронзов"),
    MAROON("Бордо"), CHAMELEON("Хамелеон"),
    MULTICOLOR("Многоцветен"), OTHER("Друг")
}

enum class CarBrand (val models: List<String>){
    OTHER(listOf("Друго")),
    ABARTH(listOf("124", "595")),
    ACURA(listOf(
        "ILX", "Integra", "Mdx", "NSX", "Rdx", "Rl", "Rsx", "Slx", "TLX", "Tl", "Tsx", "ZDX"
    )),
    ALFA_ROMEO(listOf(
        "145", "146", "147", "155", "156", "156 sportwagon", "159",
        "159 sportwagon", "164", "166", "33", "4C", "75", "76",
        "8C Competizione", "90", "Alfetta", "Berlina", "Brera",
        "Crosswagon q4", "Giulia", "Giulietta", "Gt", "Gtv",
        "Junior", "MiTo", "Spider", "Sprint", "Stelvio",
        "Sud", "Tonale"
    )),
    ALPINA(listOf(
        "B10", "B11", "B12", "B3", "B4", "B5", "B6", "B7",
        "B8", "B9", "C1", "C2", "D3", "D4", "D5",
        "Roadster", "XD3"
    )),
    ASTON_MARTIN(listOf(
        "DBS", "DBX", "Db7", "Db9", "Rapide",
        "V12 Vantage", "V8 Vantage", "Vanquish", "Други"
    )),
    AUDI(listOf(
        "A1", "A2", "A3", "A4 Allroad", "A4", "A5", "A6", "A6 Allroad",
        "A7", "A8", "Allroad", "Cabriolet", "Coupe", "E-Tron", "E-Tron GT",
        "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8", "Quattro", "R8", "RSQ3",
        "RSQ8", "RS2", "RS3", "RS4", "RS5", "RS6", "RS7", "S1", "S2", "S3", "S4",
        "S5", "S6", "S7", "S8", "SQ5", "SQ7", "SQ8", "Tt"
    )),
    BENTLEY(listOf(
        "Arnage", "Azure", "Bentayga", "Continental",
        "Continental gt", "Flying Spur", "GT Convertible",
        "Mulsanne", "T-series"
    )),
    BMW(listOf(
        "1-Series", "114", "116", "118", "120", "123", "125", "128",
        "130", "135", "140", "1500", "1600", "1602", "1800", "1M",
        "2-Series", "2 Active Tourer", "2 Gran Coupe", "2 Gran Tourer",
        "2000", "2002", "216", "218", "220", "220 d", "225", "228",
        "230", "235", "240", "2800", "3-Series", "315", "316", "318",
        "320", "323", "324", "325", "328", "330", "335", "340",
        "3gt", "4-Series", "418", "420", "425", "428", "430", "435",
        "440", "5-Series", "5 Gran Turismo", "501", "518", "520",
        "523", "524", "525", "528", "530", "530E", "535", "540",
        "545", "550", "6-Series", "6 GT", "620", "628", "630",
        "633", "635", "640", "645", "650", "7-Series", "700",
        "721", "723", "725", "728", "730", "732", "733", "735",
        "740", "745", "750", "760", "840", "850", "Izetta", "M-Series",
        "M Coupе", "M135", "M140", "M2", "M3", "M4", "M5", "M6",
        "M8", "X1", "X2", "X3", "X4", "X5", "X5M", "X6", "X7",
        "XM", "Z1", "Z3", "Z4", "Z8", "i3", "i4", "i5", "i7",
        "i8", "iX", "iX1", "iX2", "iX3"
    )),
    BUGATTI(listOf("Chiron", "Veyron")),
    BUICK(listOf(
        "Century", "Electra", "Enclave", "Invicta", "Park avenue",
        "Regal", "Rendezvous", "Riviera", "Skylark", "Skyline"
    )),
    BYD(listOf(
        "Atto3", "HAN", "Seal", "Tang"
    )),
    CADILLAC(listOf(
        "ATS", "Allante", "BLS", "Brougham", "CT6",
        "Cts", "DTS", "Deville", "Eldorado", "Escalade",
        "Fleetwood", "STS", "Seville", "Srx", "XT4",
        "XT5", "XT6", "XTS", "Xlr"
    )),
    CHEVROLET(listOf(
        "Alero", "Astro", "Avalanche", "Aveo", "Beretta",
        "Blazer", "Bolt", "Camaro", "Caprice", "Captiva",
        "Cavalier", "Cobalt", "Colorado", "Corvette", "Cruze",
        "Diamante", "El Camino", "Epica", "Equinox", "Evanda",
        "Gmc", "Hhr", "Impala", "Kalos", "Lacetti",
        "Lumina", "Malibu", "Matiz", "Niva", "Nova",
        "Nubira", "Orlando", "Rezzo", "S-10", "Silverado",
        "Spark", "Ssr", "Suburban", "Tacuma", "Tahoe",
        "Tracker", "Trailblazer", "Transsport", "Traverse",
        "Trax", "Volt"
    )),
    CHRYSLER(listOf(
        "200", "300c", "300m", "Cherokee", "Crossfire",
        "Daytona", "Es", "Gr.voyager", "Grand cherokee",
        "Gts", "Interpid", "Lebaron", "Neon", "New yorker",
        "Pacifica", "Pt cruiser", "Saratoga", "Sebring",
        "Stratus", "Town and Country", "Vision", "Voyager",
        "Wrangler"
    )),
    CITROEN(listOf(
        "2cv", "Ami", "Ami Ami", "Ami Vibe", "Ax",
        "Axel", "Berlingo", "Bx", "C - Zero", "C-Crosser",
        "C-Elysee", "C1", "C15", "C2", "C3", "C3 Aircross",
        "C3 Picasso", "C3 pluriel", "C4", "C4 AIRCROSS",
        "C4 Cactus", "C4 Picasso", "C4X", "C5", "C5 Aircross",
        "C5X", "C6", "C8", "Cx", "DS 3 Crossback",
        "DS 4 Crossback", "DS 7 Crossback", "DS3", "DS4",
        "DS5", "DS7", "Ds", "E-Mehari", "Evasion",
        "Grand C4 Picasso", "Gsa", "Gx", "Jumpy", "Ln",
        "My Ami", "My Ami Pop", "My Ami Vibe", "Nemo",
        "Oltcit", "Saxo", "Spacetourer", "Visa", "Xantia",
        "Xm", "Xsara", "Xsara picasso", "Zx"
    )),
    CUPRA(listOf(
        "Ateca", "Born", "Formentor", "Leon"
    )),
    DACIA(listOf(
        "1100", "1300", "1304", "1307", "1310", "1350", "Dokker", "Duster",
        "Jogger", "Liberta", "Lodgy", "Logan", "Nova", "Pickup", "Sandero",
        "Solenza", "Spring"
    )),
    DAEWOO(listOf(
        "Ace", "Chairman", "Cielo", "Espero", "Evanda", "Fso", "Kalos", "Korando",
        "Lacetti", "Lanos", "Leganza", "Magnus", "Matiz", "Musso", "Nexia", "Nubira",
        "Prince", "Racer", "Rezzo", "Super", "Tacuma", "Tico"
    )),
    DAIHATSU(listOf(
        "Applause", "Charade", "Charmant", "Copen", "Cuore", "Feroza", "Gran move",
        "Hijet", "Materia", "Move", "Rocky", "Sharade", "Sirion", "Taft", "Terios",
        "Trevis", "Wildcat", "Yrv"
    )),
    DODGE(listOf(
        "Avenger", "Caliber", "Caravan", "Challenger", "Charger", "Coronet",
        "Dakota", "Dart", "Daytona", "Durango", "Interpid", "Journey",
        "Magnum", "Neon", "Nitro", "RAM 1500", "RAM 2500", "RAM 3500",
        "Ram", "Shadow", "Stealth", "Stratus", "Viper"
    )),
    DONGFENG(listOf(
        "580", "E3", "GLORY 500", "IX5", "M4-YACHT", "T5-EVO"
    )),
    DS(listOf(
        "DS 3", "DS 3 Crossback", "DS 4", "DS 4 Crossback", "DS 5",
        "DS 7", "DS 7 Crossback", "DS 9"
    )),
    FERRARI(listOf(
        "296GTB", "308", "348", "360 modena", "360 spider",
        "458 Italia", "488", "599", "812 GTS", "812 Superfast",
        "California", "Enzo", "F12berlinetta", "F430", "F456m",
        "F575m maranello", "F612 scaglietti", "F8", "FF",
        "GTC4Lusso", "LaFerrari", "Mondial 8", "Portofino",
        "Purosangue", "Roma", "SF 90", "Testarossa"
    )),
    FIAT(listOf(
        "1100", "124", "125", "126", "127", "128",
        "131", "132", "1400", "1500", "1800", "500",
        "500L", "500X", "600", "650", "750", "Albea",
        "Argenta", "Barchetta", "Bertone", "Brava",
        "Bravo", "Campagnola", "Cinquecento", "Coupe",
        "Croma", "Doblo", "Duna", "Fiorino", "Freemont",
        "Fullback", "Idea", "Linea", "Marea", "Multipla",
        "Palio", "Panda", "Punto", "Qubo", "Regata",
        "Ritmo", "Scudo", "Sedici", "Seicento", "Siena",
        "Stilo", "Strada", "Tempra", "Tipo", "Topolino",
        "Ulysse", "Uno"
    )),
    FORD(listOf(
        "12m", "15m", "17m", "20m",
        "Aerostar", "B-Max", "Bronco",
        "C-max", "Capri", "Connect",
        "Consul", "Cortina", "Cosworth",
        "Cougar", "Countur", "Courier",
        "Crown victoria", "EcoSport", "Ecoline",
        "Edge", "Escape", "Escort",
        "Everest", "Excursion", "Expedition",
        "Explorer", "F150", "F250",
        "F350", "F450", "F550",
        "F650", "F750", "Fiesta",
        "Flex", "Focus", "Fusion",
        "GT", "Galaxy", "Granada",
        "Grand C-Max", "Ka", "Kuga",
        "Maverick", "Mercury", "Mondeo",
        "Mustang", "Orion", "Probe",
        "Puma", "Ranger", "Raptor",
        "Rs", "S-Max", "Scorpio",
        "Sierra", "Sportka", "Streetka",
        "Taunus", "Taurus", "Thunderbird",
        "Windstar", "Zephyr"
    )),
    GENESIS(listOf(
        "G70", "G80", "G90", "GV60", "GV70", "GV80"
    )),
    GEELY(listOf(
        "Atlas", "Atlas Pro", "Coolray", "Tugella"
    )),
    GMC(listOf(
        "Acadia", "Canyon", "Envoy", "Jimmy", "Saturn",
        "Savana", "Sierra", "Sonoma", "Terrain",
        "Tracker", "Typhoon", "Yukon"
    )),
    GREAT_WALL(listOf(
        "Haval H2", "Haval H6", "Hover Cuv", "Hover H5",
        "Hover H6", "ORA 03", "Poer", "Safe",
        "Steed 3", "Steed 5", "Steed 6", "Steed 7",
        "Voleex C10", "Voleex C20", "Voleex C30",
        "WEY 03", "WEY 05"
    )),
    HAVAL(listOf(
        "Dargo", "F7x", "H2", "H6", "Jolion"
    )),
    HONDA(listOf(
        "Accord", "Cbr", "Cbx", "City", "Civic", "Civic ballade",
        "Concerto", "Cr-v", "Crosstour", "Crx", "Crz", "Electric",
        "Element", "Elysion", "Fit", "Fr-v", "Hr-v", "Insight",
        "Integra", "Jazz", "Legend", "Logo", "M-NV", "Nsx",
        "Odyssey", "Passport", "Pilot", "Prelude", "Quintet",
        "Ridgeline", "S2000", "Shuttle", "Stream", "ZR-V", "e"
    )),
    HUMMER(listOf(
        "H1", "H2", "H3"
    )),
    HYUNDAI(listOf(
        "Accent", "Atos", "Bayon", "Coupe", "Elantra", "Equus", "Excel",
        "Galloper", "Genesis", "Getz", "Grace", "Grandeur", "I10", "I20",
        "I30", "I40", "IX35", "IX55", "Ioniq", "Ioniq 5", "Ioniq 6", "Ix20",
        "Kona", "Landskape", "Lantra", "Matrix", "Palisade", "Pony",
        "Porter", "S", "Santa fe", "Santamo", "Sonata", "Sonica",
        "Starex", "Staria", "Stelar", "Tb", "Terracan", "Trajet",
        "Tucson", "Veloster", "Venue", "Veracruz", "Xg"
    )),
    INFINITI(listOf(
        "Ex30", "Ex35", "Ex37", "Fx 30", "Fx 35", "Fx 37",
        "Fx 45", "Fx 50", "Fx45", "G", "G coupe", "G sedan",
        "G37", "I", "J", "M", "Q", "Q30", "Q40", "Q45",
        "Q50", "Q70", "QX30", "QX50", "QX56", "QX60",
        "QX70", "QX80", "Qx", "Qx4"
    )),
    ISUZU(listOf(
        "Amigo", "D-max", "Gemini", "Piazza", "Pickup",
        "Rodeo", "Tfs", "Trooper", "Vehi cross"
    )),
    IVECO(listOf("Massive")),
    INEOS_GRENADIES(listOf("Ineos Grenadier")),
    JAC(listOf(
        "E-S2", "E-S4", "J7", "JS3",
        "JS4", "JS7", "T8 PRO"
    )),
    JAGUAR(listOf(
        "Daimler", "Daimler double six", "Daimler six",
        "E-pace", "F-PACE", "F-Type",
        "I-Pace", "S-type", "Sovereign",
        "Super v8", "X-type", "XE",
        "Xf", "Xj", "Xjr",
        "Xjs", "Xjsc", "Xk8",
        "Xkr"
    )),
    JEEP(listOf(
        "Avenger", "Cherokee", "Commander",
        "Compass", "Grand Wagoneer", "Grand cherokee",
        "Patriot", "Renegade", "Wrangler"
    )),
    KIA(listOf(
        "Avella delta", "Cadenza", "Carens", "Carnival",
        "Ceed", "Cerato", "Clarus", "EV6", "EV9",
        "Forte", "Joecs", "Joyce", "K3", "K5",
        "K7", "K8", "K9", "Magentis", "Mohave",
        "Morning", "Niro", "Opirus", "Optima",
        "Picanto", "Pride", "Pro ceed", "Quoris",
        "Ray", "Retona", "Rio", "Sedona",
        "Seltos", "Sephia", "Shuma", "Sorento",
        "Soul", "Spectra", "Sportage", "Stinger",
        "Stonic", "Telluride", "Venga", "Visto",
        "X-Trek", "XCeed"
    )),
    KOENIGSEGG(listOf(
        "Koenigsegg CC Prototype", "Koenigsegg CC8S", "Koenigsegg CCR", "Koenigsegg CCX",
        "Koenigsegg CCGT", "Koenigsegg CCXR", "Koenigsegg CCX Edition", "Koenigsegg CCXR Edition",
        "Koenigsegg CCXR Special", "Koenigsegg CCXR Trevita", "Koenigsegg Agera",
        "Koenigsegg Agera R", "Koenigsegg Agera S", "Koenigsegg One:1", "Koenigsegg Agera RS",
        "Koenigsegg Agera Final Edition", "Koenigsegg Regera", "Koenigsegg Jesko",
        "Koenigsegg Jesko Absolut", "Koenigsegg Gemera", "Koenigsegg CC850"
    )
    ),
    LADA(listOf(
        "1200", "1300", "1500", "1600",
        "2101", "21011", "21012", "21013",
        "21015", "2102", "2103", "2104",
        "21043", "2105", "21051", "21053",
        "2106", "21061", "21063", "2107",
        "21074", "2108", "21083", "2109",
        "21093", "21099", "2110", "21213",
        "Granta", "Kalina", "Niva", "Nova",
        "Oka", "Priora", "Samara", "Travel",
        "Urban", "Vesta", "XRAY"
    )),
    LAMBORGHINI(listOf(
        "Aventador", "Countach", "Diablo",
        "Gallardo", "Huracan", "Murcielago",
        "Reventon", "Revuelto", "Urus",
        "Veneno"
    )),
    LANCIA(listOf(
        "A112", "Ardea", "Aurelia",
        "Beta", "Dedra", "Delta",
        "Flaminia", "Flavia", "Fulvia",
        "Kappa", "Lybra", "Musa",
        "Phedra", "Prisma", "Thema",
        "Thesis", "Unior", "Voyager",
        "Y", "Y10", "Ypsilon",
        "Zeta"
    )),
    LAND_ROVER(listOf(
        "Defender", "Discovery", "Discovery Sport",
        "Evoque", "Freelander",
        "Range Rover Evoque", "Range Rover Sport",
        "Range Rover Velar", "Range rover"
    )),
    LEXUS(listOf(
        "CT 200h", "ES-Series", "ES 300", "ES 330", "ES 350",
        "GS-Series", "GS 200t", "GS 250", "GS 300", "GS 350",
        "GS 430", "GS 450h", "GS 460", "GS F", "GX-Series",
        "GX 460", "GX 470", "GX 550", "IS-Series", "IS 200",
        "IS 220d", "IS 250", "IS 300", "IS 350", "IS F",
        "LBX", "LC-Series", "LC 500", "LC 500h", "LC F",
        "LFA", "LS-Series", "LS 400", "LS 430", "LS 460",
        "LS 500", "LS 600", "LX-Series", "LX 450d", "LX 470",
        "LX 500d", "LX 570", "LX 600", "NX-Series", "NX 200t",
        "NX 300", "NX 300h", "NX 350h", "NX 450", "RC-Series",
        "RC 200t", "RC 300h", "RC 350", "RC F", "RX-Series",
        "RX 200t", "RX 300", "RX 330", "RX 350", "RX 350h",
        "RX 400", "RX 400h", "RX 450", "RX 450h", "RX 450h plus",
        "RX 500", "RX 500h", "RZ", "SC-Series", "SC 400",
        "SC 430", "TX 350", "UX-Series", "UX 200", "UX 250h",
        "UX 300e", "UX 300h"
    )),
    LINCOLN(listOf(
        "Aviator", "Continental", "Ls", "MKC", "MKS",
        "MTK", "Mark", "Mark Lt", "Mkx", "Mkz",
        "Navigator", "Town car", "Zephyr"
    )),
    LOTUS(listOf(
        "Elise", "Europe", "Evora", "Exige", "Eletre", "Emira"
    )),
    LYNK_AND_CO(listOf(
        "01", "Linc & Co", "Link & Co"
    )),
    MAHINDRA(listOf(
        "Armada", "Bolero", "Cl", "Commander",
        "Goa", "KUV 100", "Marshall", "Quanto",
        "Scorpio", "XUV 500"
    )),
    MASERATI(listOf(
        "3200 gt", "Biturbo", "Coupe gt", "Ghibli",
        "GranCabrio", "GranTurismo", "Gransport",
        "Grecale", "Levante", "MC20",
        "Quattroporte", "Spyder", "Zagato"
    )),
    MAYBACH(listOf(
        "57", "62", "650", "S 560", "S580"
    )),
    MAZDA(listOf(
        "121", "2", "3", "323", "5", "6", "626", "929", "B2200",
        "B2500", "B2600", "BT-50", "CX-30", "CX-5", "CX-60",
        "CX-7", "CX-9", "Demio", "MX-30", "Mpv", "Mx-3",
        "Mx-5", "Mx-6", "Premacy", "Rx-7", "Rx-8", "Tribute",
        "Xedos", "СХ-3"
    )),
    MCLAREN(listOf(
        "540C Coupe", "570S Coupe", "650S", "675LT", "720 S",
        "F1", "GT", "MP4-12C", "P1"
    )),
    MERCEDES_BENZ(listOf(
        "110", "111", "113", "114", "115", "116", "123", "124", "126", "126-260",
        "150", "170", "180", "190", "200", "220", "230", "240", "250", "260",
        "280", "290", "300", "320", "350", "380", "420", "450", "500", "560",
        "600", "A-class", "A 140", "A 150", "A 160", "A 170", "A 180", "A 190",
        "A 200", "A 210", "A 220", "A 250", "A 35", "A 45", "A45 AMG", "AMG GT",
        "AMG GT C", "AMG GT R", "AMG GT S", "Adenauer", "B-class", "B 150",
        "B 160", "B 170", "B 180", "B 200", "B 220", "B 250", "C-class", "C 160",
        "C 180", "C 200", "C 220", "C 230", "C 240", "C 250", "C 270", "C 280",
        "C 30 AMG", "C 300", "C 32 AMG", "C 320", "C 350", "C 36 AMG", "C 400",
        "C 43 AMG", "C 450 AMG", "C 55 AMG", "C 63 AMG", "CL-class", "CL 230",
        "CL 320", "CL 420", "CL 500", "CL 55 AMG", "CL 600", "CL 63 AMG", "CL 65 AMG",
        "CLA-class", "CLA 180", "CLA 200", "CLA 220", "CLA 250", "CLA 350", "CLA 350 AMG",
        "CLA 45 AMG", "CLC-class", "CLC 160", "CLC 180", "CLC 200", "CLC 220", "CLC 230",
        "CLC 250", "CLC 350", "CLE", "CLK-class", "CLK 55 AMG", "CLK 63 AMG", "CLS-class",
        "CLS 220", "CLS 250", "CLS 300", "CLS 320", "CLS 350", "CLS 400", "CLS 450",
        "CLS 500", "E-class", "E 200", "E 220", "E 230", "E 240", "E 250", "E 260",
        "E 280", "E 300", "E 320", "E 350", "E 400", "E 500", "E 55 AMG", "E 63 AMG",
        "G-class", "G 320", "G 350", "G 500", "G 55 AMG", "G 63 AMG", "G 65 AMG",
        "GLA-class", "GLA 200", "GLA 220", "GLA 250", "GLA 35 AMG", "GLA 45 AMG",
        "GLB-class", "GLB 200", "GLB 220", "GLB 250", "GLC-class", "GLC 200",
        "GLC 220", "GLC 250", "GLC 300", "GLC 43 AMG", "GLC 63 AMG", "GLE-class",
        "GLE 300", "GLE 350", "GLE 400", "GLE 450", "GLE 500", "GLE 63 AMG",
        "GLS-class", "GLS 350", "GLS 400", "GLS 450", "GLS 500", "GLS 63 AMG",
        "S-class", "S 320", "S 350", "S 400", "S 450", "S 500", "S 550", "S 600",
        "S 63 AMG", "S 65 AMG", "SLS AMG", "SL-class", "SL 300", "SL 320", "SL 350",
        "SL 500", "SL 55 AMG", "SL 63 AMG", "SL 65 AMG", "V-class", "V 220", "V 250"
    )),
    MG(listOf(
        "4", "5", "EHS",
        "GS Exclusive", "MG4", "Marvel R",
        "Mga", "Mgb", "Mgf",
        "Tf", "ZS EV", "Zr",
        "Zs", "Zt", "Zt-t"
    )),
    Mini(listOf(
        "Clubman", "Cooper", "Cooper cabrio",
        "Cooper s", "Cooper s cabrio", "Countryman",
        "Coupe", "D one", "John Cooper Works",
        "One", "One cabrio", "Paceman"
    )),
    MITSUBISHI(listOf(
        "3000 gt", "ASX", "Attrage",
        "Carisma", "Colt", "Cordia",
        "Eclipse", "Eclipse Cross", "Galant",
        "Grandis", "I-MiEV", "L200",
        "Lancer", "Mirage", "Montero",
        "Outlander", "Pajero", "Pajero pinin",
        "Pajero sport", "RVR", "Sapporo",
        "Sigma", "Space gear", "Space runner",
        "Space star", "Space wagon", "Starion",
        "Tredia"
    )),
    MORGAN(listOf("Auro8")),
    MOSKVICH(listOf(
        "1360", "1361", "1500",
        "2136", "2138", "2140",
        "2141", "21412", "21417",
        "2142", "2715", "401",
        "403", "407", "408",
        "412", "426", "427",
        "503", "Aleko", "Иж"
    )),
    NISSAN(listOf(
        "100 nx", "200 sx", "240 z",
        "280 z", "300 zx", "350z",
        "370Z", "Acura", "Almera",
        "Almera tino", "Altima", "Ariya",
        "Armada", "Bluebird", "Cedric",
        "Cherry", "Cube", "Figaro",
        "Frontier", "Gt-r", "Juke",
        "Kubistar", "Laurel", "Leaf",
        "Maxima", "Micra", "Murano",
        "NP300", "Navara", "Note",
        "Pathfinder", "Patrol", "Pickup",
        "Pixo", "Prairie", "Primera",
        "Pulsar", "Qashqai", "Quest",
        "Rogue", "Sentra", "Serena",
        "Silvia", "Skyline", "Stantza",
        "Sunny", "Teana", "Terrano",
        "Tiida", "Titan crew cab", "Titan king",
        "Versa", "X-trail", "Xterra",
        "e-NV200"
    )),
    OPEL(listOf(
        "Adam", "Admiral", "Agila",
        "Ampera", "Antara", "Ascona",
        "Astra", "Calibra", "Campo",
        "Cascada", "Combo", "Commodore",
        "Corsa", "Crossland X", "Diplomat",
        "Frontera", "Grandland X", "Gt",
        "Insignia", "Kadett", "Kapitaen",
        "Karl", "Manta", "Meriva",
        "Mokka", "Mokka X", "Monterey",
        "Monza", "Omega", "Rekord",
        "Senator", "Signum", "Sintra",
        "Speedster", "Tigra", "Vectra",
        "Zafira"
    )),
    PAGANI(listOf(
        "Zonda", "Zonda S", "Zonda Roadster", "Zonda F", "Zonda F Roadster",
        "Zonda Cinque Roadster", "Zonda Cinque", "Huayra", "Huayra Roadster",
        "Imola", "Utopia"
    )),
    PEUGEOT(listOf(
        "1007", "104", "106",
        "107", "108", "2008",
        "202", "204", "205",
        "206", "207", "208",
        "3008", "301", "304",
        "305", "306", "307",
        "308", "309", "4007",
        "4008", "402", "403",
        "404", "405", "406",
        "407", "408", "5008",
        "504", "505", "508",
        "604", "605", "607",
        "806", "807", "Bipper",
        "Expert", "Partner", "RCZ",
        "Range", "Rifter", "Traveler",
        "iOn"
    )),
    PONTIAC(listOf(
        "Aztec", "Bonneville", "Fiero",
        "Firebird", "G6", "Grand am",
        "Grand prix", "Gto", "Lemans",
        "Solstice", "Sunbird", "Sunfire",
        "Tempest", "Torrent", "Trans am",
        "Trans sport", "Vibe"
    )),
    PLYMOUTH(listOf(
        "Acclaim", "Barracuda", "Breeze",
        "Colt", "Grand voyager", "Horizon",
        "Laser", "Neon", "Prowler",
        "Reliant", "Road runner", "Sundance",
        "Volare", "Voyager"
    )),
    PORSCHE(listOf(
        "356 Speedster", "911", "918 Spyder",
        "924", "928", "935",
        "944", "956", "968",
        "991", "993", "996",
        "Boxster", "Cayenne",
        "Cayman", "Macan", "Panamera",
        "Taycan"
    )),
    RAM(listOf(
        "1500", "2500", "3500"
    )),
    RENAULT(listOf(
        "10", "11", "12", "14",
        "16", "18", "19", "20",
        "21", "25", "29", "30",
        "4", "5", "8", "9",
        "Alpine", "Arkana", "Austral",
        "Avantime", "Bakara", "Bulgar",
        "Captur", "Caravelle", "Chamade",
        "Clio", "Duster", "Espace",
        "Express", "Floride", "Fluence",
        "Fuego", "Grand espace", "Grand scenic",
        "K-ZE", "Kadjar", "Kangoo",
        "Koleos", "Laguna", "Laguna Coupe",
        "Latitude", "Megane", "Modus",
        "Nevada", "Rapid", "Safrane",
        "Scenic", "Scenic rx4", "Symbol",
        "Talisman", "Twingo", "Twizy",
        "Vel satis", "Wind", "Zoe"
    )),
    RIMAC(listOf("Nevera")),
    RIVIAN(listOf("R1S", "R1T")),
    ROLLS_ROYCE(listOf(
        "Cullinan", "Dawn", "Ghost",
        "Phantom", "Rieju", "Silver Cloud",
        "Silver Seraph", "Silver Spur",
        "Speter", "Wraith"
    )),
    ROVER(listOf(
        "111", "114", "200", "213",
        "214", "216", "220", "25",
        "400", "414", "416", "418",
        "420", "45", "600", "618",
        "620", "623", "75", "800",
        "820", "825", "827", "City",
        "Estate", "Maestro", "Metro",
        "Mini", "Montego", "Streetwise"
    )),
    SAAB(listOf(
        "9-3", "9-4X", "9-5",
        "9-7x", "900", "9000"
    )),
    SEAT(listOf(
        "Alhambra", "Altea", "Arona",
        "Arosa", "Ateca", "Cordoba",
        "Cupra", "Exeo", "Fura",
        "Ibiza", "Inka", "Leon",
        "Malaga", "Marbella", "Mii",
        "Ronda", "Tarraco", "Terra",
        "Toledo", "Vario"
    )),
    SKODA(listOf(
        "100", "1000", "105",
        "120", "125", "130",
        "135", "136", "Citigo",
        "Enyaq", "Fabia", "Favorit",
        "Felicia", "Forman", "Kamiq",
        "Karoq", "Kodiaq", "Octavia",
        "Praktik", "Rapid", "Roomster",
        "Scala", "Superb", "Yeti"
    )),
    SMART(listOf(
        "Forfour", "Fortwo", "Mc",
        "Roadster", "Smart #1", "Smart #3"
    )),
    SSANGYONG(listOf(
        "Actyon", "Actyon Sports", "Korando",
        "Korando Sports", "Kyron", "Musso",
        "Rexton", "Tivoli", "Torres", "XLV"
    )),
    SUBARU(listOf(
        "1800", "Ascent", "B10 Tribeka", "B9 tribeca",
        "BRZ", "Baja", "E12", "Forester",
        "G3x justy", "Impreza", "Justy", "Legacy",
        "Leone", "Levorg", "Libero", "Outback",
        "Solterra", "Svx", "Trezia", "Vivio",
        "XT", "XV"
    )),
    SUZUKI(listOf(
        "Across", "Alto", "Baleno",
        "Celerio", "Forenza", "Grand Vitara",
        "Ignis", "Jimny", "Kizashi",
        "Liana", "Maruti", "Reno",
        "SX4", "SX4 S-Cross", "Samurai",
        "Santana", "Sg", "Sidekick",
        "Sj", "Splash", "Swace",
        "Swift", "Vitara", "Wagon R",
        "X-90", "XL-7"
    )),
    TATA(listOf(
        "Aria", "Estate", "Indica",
        "Mint", "Nano", "Safari",
        "Sierra", "Sumo", "Telcoline",
        "Xenon"
    )),
    TESLA(listOf(
        "Cybertruck", "Model 3", "Model S",
        "Model X", "Model Y", "Roadster",
        "Roadster Sport"
    )),
    TOYOTA(listOf(
        "4runner", "Alphard", "Auris",
        "Avalon", "Avensis", "Avensis verso",
        "Aygo", "C-HR", "Camry",
        "Carina", "Celica", "Corolla",
        "Corolla Cross", "Corolla Matrix", "Corolla verso",
        "Cressida", "Crown", "Fj cruiser",
        "GR86", "GT86", "Harrier",
        "Highlander", "Hilux", "IQ",
        "Land cruiser", "Matrix", "Mirai",
        "Mr2", "Paseo", "Picnic",
        "Previa", "Prius", "Proace City",
        "Proace City Verso", "Rav4", "Scion",
        "Sequoia", "Sienna", "Starlet",
        "Suarer", "Supra", "Tacoma",
        "Tercel", "Tundra", "Urban Cruiser",
        "Venza", "Verso", "Verso S",
        "Yaris", "Yaris Cross", "Yaris verso",
        "bZ4X"
    )),
    TRABANT(listOf(
        "600", "601", "Combi",
        "T 1.1"
    )),
    VW(listOf(
        "1200", "1300", "1302",
        "1303", "1500", "1600",
        "Alltrack", "Amarok", "Arteon",
        "Atlas", "Beetle", "Bora",
        "CC", "Caddy", "Corrado",
        "Derby", "Eos", "Fox",
        "Golf", "Golf Plus", "Golf Variant",
        "ID.3", "ID.4", "ID.5",
        "ID.6", "ID.7", "ID.Buzz",
        "Jetta", "K 70", "Karmann-ghia",
        "Lupo", "Multivan", "New beetle",
        "Passat", "Phaeton", "Polo",
        "Rabbit", "Santana", "Scirocco",
        "Sharan", "Sportsvan", "T-Cross",
        "T-Roc", "Taigo", "Taro",
        "Tiguan", "Touareg", "Touran",
        "Up", "Vento"
    )),
    VOLVO(listOf(
        "142", "144", "145",
        "164", "1800 es", "240",
        "244", "245", "262 c",
        "264", "340", "343",
        "344", "345", "360",
        "440", "460", "480",
        "66", "740", "744",
        "745", "760", "765",
        "770", "780", "850",
        "940", "960", "C30",
        "C40", "C70", "EX30",
        "P 1800", "S40", "S60",
        "S70", "S80", "S90",
        "V40", "V40 Cross Country", "V50",
        "V60", "V60 Cross Country", "V70",
        "V90", "V90 Cross Country", "XC40",
        "XC60", "Xc70", "Xc90"
    )),
    VOLGA(listOf(
        "22", "24", "3102",
        "3110", "3111", "M 20",
        "M 21", "Siber", "VS"
    )),
    WEY(listOf(
        "03", "05", "Coffee 01"
    )),
    ZENVO(listOf(
        "ST1", "TS1", "TS1 GT", "TSR", "TSR-S", "TSR-GT", "Aurora"
    ))
}