package com.example.data.repository

import com.example.data.local.BookingDao
import com.example.data.model.BookingEntity
import kotlinx.coroutines.flow.Flow

class BookingRepository(private val bookingDao: BookingDao) {
    val allBookings: Flow<List<BookingEntity>> = bookingDao.getAllBookings()

    suspend fun insertBooking(booking: BookingEntity): Long {
        return bookingDao.insertBooking(booking)
    }

    suspend fun updateStatus(id: Long, status: String) {
        bookingDao.updateStatus(id, status)
    }

    suspend fun deleteBooking(id: Long) {
        bookingDao.deleteBooking(id)
    }
}
