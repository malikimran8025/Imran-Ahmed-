package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ServiceType(val displayName: String) {
    WATER_TANK("Water Tank Cleaning"),
    SOLAR_PLATE("Solar Plate Washing"),
    BOTH("Both (Water Tank & Solar Plate)")
}

enum class BookingStatus(val label: String) {
    PENDING("Pending Confirmation"),
    CONFIRMED("Confirmed by Business"),
    COMPLETED("Service Completed"),
    CANCELLED("Cancelled")
}

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val mobileNumber: String,
    val fullAddress: String,
    val area: String,
    val serviceType: String,
    val capacityOrPanels: String,
    val preferredDate: String,
    val preferredTime: String,
    val additionalInstructions: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = BookingStatus.PENDING.label
)
