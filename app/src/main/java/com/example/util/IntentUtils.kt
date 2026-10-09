package com.example.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.data.model.BookingEntity
import com.example.data.model.BusinessInfo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object IntentUtils {

    fun dialPhoneNumber(context: Context, phoneNumber: String = BusinessInfo.PHONE_NUMBER) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open dialer: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsAppChat(
        context: Context,
        message: String = "Assalam-o-Alaikum M Salman, I would like to inquire about I.S Water Tank Cleaner & Solar Plate Wash services."
    ) {
        try {
            val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
            val url = "https://wa.me/${BusinessInfo.WHATSAPP_NUMBER}?text=$encodedMessage"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open WhatsApp: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGoogleMaps(context: Context) {
        try {
            val geoUri = Uri.parse("geo:31.4820,74.3045?q=" + Uri.encode("Faisal Town, B Block, Lahore, Pakistan"))
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode("Faisal Town, B Block, Lahore, Pakistan"))
                )
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://maps.google.com/?q=" + Uri.encode("Faisal Town, B Block, Lahore"))
            )
            context.startActivity(webIntent)
        }
    }

    fun sendBookingToWhatsApp(context: Context, booking: BookingEntity) {
        val text = buildString {
            append("💧 *NEW SERVICE BOOKING REQUEST* ☀️\n")
            append("*I.S Water Tank Cleaner & Solar Plate Wash*\n\n")
            append("👤 *Customer:* ${booking.customerName}\n")
            append("📞 *Phone:* ${booking.mobileNumber}\n")
            append("📍 *Area:* ${booking.area}\n")
            append("🏠 *Full Address:* ${booking.fullAddress}\n\n")
            append("🛠️ *Service Required:* ${booking.serviceType}\n")
            append("📊 *Capacity / Panels:* ${booking.capacityOrPanels}\n")
            append("📅 *Preferred Date:* ${booking.preferredDate}\n")
            append("⏰ *Preferred Time:* ${booking.preferredTime}\n")
            if (booking.additionalInstructions.isNotBlank()) {
                append("📝 *Instructions:* ${booking.additionalInstructions}\n")
            }
            append("\n_Sent from I.S Customer App. Please confirm my appointment._")
        }
        openWhatsAppChat(context, text)
    }

    fun shareApp(context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "${BusinessInfo.BUSINESS_NAME}\nProfessional Water Tank Cleaning & Solar Plate Wash in Faisal Town, Lahore.\nContact M Salman: ${BusinessInfo.PHONE_NUMBER}\nClean water, maximum solar efficiency!"
            )
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share I.S Cleaning Services"))
    }
}
