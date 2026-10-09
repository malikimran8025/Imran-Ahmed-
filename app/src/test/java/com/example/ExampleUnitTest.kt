package com.example

import com.example.data.model.BusinessInfo
import com.example.data.model.DefaultPrices
import com.example.data.model.PriceCategory
import com.example.data.model.ServiceType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testBusinessInformationConstants() {
        assertEquals("I.S Water Tank Cleaner & Solar Plate Wash", BusinessInfo.BUSINESS_NAME)
        assertEquals("M Salman", BusinessInfo.OWNER_NAME)
        assertEquals("03134771115", BusinessInfo.PHONE_NUMBER)
        assertTrue(BusinessInfo.OFFICE_LOCATION.contains("Faisal Town, B Block"))
        assertEquals("1115", BusinessInfo.DEFAULT_OWNER_PIN)
    }

    @Test
    fun testDefaultPricesList() {
        val waterPrices = DefaultPrices.items.filter { it.category == PriceCategory.WATER_TANK }
        val solarPrices = DefaultPrices.items.filter { it.category == PriceCategory.SOLAR_PLATE }

        assertTrue("Should have water tank price options", waterPrices.isNotEmpty())
        assertTrue("Should have solar plate price options", solarPrices.isNotEmpty())

        val smallTank = waterPrices.find { it.id == "tank_small" }
        assertNotNull(smallTank)
        assertTrue(smallTank!!.pricePkr > 0)

        val customSolar = solarPrices.find { it.id == "solar_custom" }
        assertNotNull(customSolar)
        assertTrue(customSolar!!.isCustomQuote)
    }

    @Test
    fun testServiceTypes() {
        assertEquals("Water Tank Cleaning", ServiceType.WATER_TANK.displayName)
        assertEquals("Solar Plate Washing", ServiceType.SOLAR_PLATE.displayName)
        assertEquals("Both (Water Tank & Solar Plate)", ServiceType.BOTH.displayName)
    }
}
