package com.example.data.model

enum class PriceCategory {
    WATER_TANK,
    SOLAR_PLATE
}

data class PriceItem(
    val id: String,
    val category: PriceCategory,
    val title: String,
    val description: String,
    val pricePkr: Int,
    val isCustomQuote: Boolean = false,
    val unit: String = ""
)

object DefaultPrices {
    val items = listOf(
        // Water Tank Cleaning
        PriceItem(
            id = "tank_small",
            category = PriceCategory.WATER_TANK,
            title = "Small Tank",
            description = "Overhead or plastic tanks up to 1,000 Gallons. Complete sludge drain, deep brush wash & chlorine sanitizer.",
            pricePkr = 3500,
            unit = "per tank"
        ),
        PriceItem(
            id = "tank_medium",
            category = PriceCategory.WATER_TANK,
            title = "Medium Tank",
            description = "Overhead or masonry tanks 1,500 – 2,500 Gallons. High pressure cleaning, bacterial treatment & sediment suction.",
            pricePkr = 5500,
            unit = "per tank"
        ),
        PriceItem(
            id = "tank_large",
            category = PriceCategory.WATER_TANK,
            title = "Large Tank",
            description = "Tanks 3,000 – 5,000 Gallons. Industrial pump de-watering, intensive algae removal & anti-fungal rinse.",
            pricePkr = 8500,
            unit = "per tank"
        ),
        PriceItem(
            id = "tank_underground",
            category = PriceCategory.WATER_TANK,
            title = "Underground Tank",
            description = "Underground concrete storage tank. Full mud extraction, motorized pressure scrub, and food-grade disinfectant.",
            pricePkr = 9500,
            unit = "starting from"
        ),

        // Solar Plate Washing
        PriceItem(
            id = "solar_per_plate",
            category = PriceCategory.SOLAR_PLATE,
            title = "Per Solar Plate",
            description = "Individual panel wash with deionized water, scratch-free microfiber telescopic brushes & frame wipe.",
            pricePkr = 200,
            unit = "per plate"
        ),
        PriceItem(
            id = "solar_5_plate",
            category = PriceCategory.SOLAR_PLATE,
            title = "5-Plate Package",
            description = "Standard rooftop system (approx 2.5 kW – 3 kW). Surface dust/smog washing, glass restoration & quick wipe.",
            pricePkr = 1000,
            unit = "package"
        ),
        PriceItem(
            id = "solar_10_plate",
            category = PriceCategory.SOLAR_PLATE,
            title = "10-Plate Package",
            description = "Ideal for 5 kW rooftop solar systems. Deep streak-free panel cleaning, bird dropping removal & angle rinse.",
            pricePkr = 1800,
            unit = "package"
        ),
        PriceItem(
            id = "solar_15_plate",
            category = PriceCategory.SOLAR_PLATE,
            title = "15-Plate Package",
            description = "Ideal for 8 kW – 10 kW residential systems. Full multi-tier panel wash, frame clean & efficiency boost.",
            pricePkr = 2500,
            unit = "package"
        ),
        PriceItem(
            id = "solar_custom",
            category = PriceCategory.SOLAR_PLATE,
            title = "Custom Package",
            description = "Commercial 20+ panel arrays, factories, plazas or high-incline roofs. Contact M Salman for customized discount quote.",
            pricePkr = 0,
            isCustomQuote = true,
            unit = "Contact us"
        )
    )
}
