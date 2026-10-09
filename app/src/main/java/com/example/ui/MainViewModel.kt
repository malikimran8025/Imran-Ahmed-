package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PriceManager
import com.example.data.model.BookingEntity
import com.example.data.model.PriceCategory
import com.example.data.model.PriceItem
import com.example.data.model.ServiceType
import com.example.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val priceManager = PriceManager(application)
    private val bookingRepository = BookingRepository(AppDatabase.getDatabase(application).bookingDao())

    val prices: StateFlow<List<PriceItem>> = priceManager.prices

    val bookings: StateFlow<List<BookingEntity>> = bookingRepository.allBookings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _prefilledService = MutableStateFlow(ServiceType.WATER_TANK)
    val prefilledService: StateFlow<ServiceType> = _prefilledService.asStateFlow()

    private val _prefilledCapacity = MutableStateFlow("")
    val prefilledCapacity: StateFlow<String> = _prefilledCapacity.asStateFlow()

    private val _bookingSubmittedEvent = MutableSharedFlow<BookingEntity>()
    val bookingSubmittedEvent: SharedFlow<BookingEntity> = _bookingSubmittedEvent.asSharedFlow()

    fun setPrefilledBooking(service: ServiceType, capacity: String = "") {
        _prefilledService.value = service
        _prefilledCapacity.value = capacity
    }

    fun submitBooking(
        customerName: String,
        phone: String,
        address: String,
        area: String,
        serviceType: ServiceType,
        capacityOrPanels: String,
        preferredDate: String,
        preferredTime: String,
        instructions: String,
        onComplete: (BookingEntity) -> Unit
    ) {
        viewModelScope.launch {
            val booking = BookingEntity(
                customerName = customerName.trim(),
                mobileNumber = phone.trim(),
                fullAddress = address.trim(),
                area = area.trim(),
                serviceType = serviceType.displayName,
                capacityOrPanels = capacityOrPanels.trim(),
                preferredDate = preferredDate.trim(),
                preferredTime = preferredTime.trim(),
                additionalInstructions = instructions.trim()
            )
            val generatedId = bookingRepository.insertBooking(booking)
            val savedBooking = booking.copy(id = generatedId)
            _bookingSubmittedEvent.emit(savedBooking)
            onComplete(savedBooking)
        }
    }

    fun updatePrice(id: String, newPrice: Int, newDesc: String? = null) {
        priceManager.updatePrice(id, newPrice, newDesc)
    }

    fun resetPrices() {
        priceManager.resetToDefaults()
    }

    fun updateBookingStatus(id: Long, newStatus: String) {
        viewModelScope.launch {
            bookingRepository.updateStatus(id, newStatus)
        }
    }

    fun deleteBooking(id: Long) {
        viewModelScope.launch {
            bookingRepository.deleteBooking(id)
        }
    }

    fun getPricesByCategory(category: PriceCategory): List<PriceItem> {
        return priceManager.getPricesByCategory(category)
    }
}
