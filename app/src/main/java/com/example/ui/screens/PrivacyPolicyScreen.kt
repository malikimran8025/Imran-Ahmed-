package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusinessInfo
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CleanGreenTertiary

@Composable
fun PrivacyPolicyScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("privacy_policy_screen"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Play Store Publishing Status Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Publish,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Google Play Store Ready",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "This app meets all Google Play Store developer guidelines, including targetSdk 36, zero runtime dangerous permissions, Material You adaptive icons, and strict user privacy.",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Privacy Policy Details
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        title = "Privacy Policy",
                        subtitle = "Last Updated: October 2026",
                        icon = Icons.Default.Policy
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    val sections = listOf(
                        "1. Overview" to "${BusinessInfo.BUSINESS_NAME} (\"we\", \"our\", \"us\") is committed to protecting your privacy. This application is designed solely to help customers in Faisal Town and Lahore browse cleaning services, check rates, and request bookings directly with M Salman.",
                        "2. Information We Collect" to "When you submit a service booking or contact us, you provide: Full Name, Contact Mobile Number, House Address / Area in Lahore, Service Selection (Water Tank / Solar Plates), and preferred cleaning date/time. This information is stored strictly locally on your Android device in secure SQLite storage and shared with M Salman via WhatsApp upon your manual consent.",
                        "3. Zero Background Tracking" to "We do NOT track your GPS coordinates in the background, collect advertising identifiers, or sell any customer data to third-party ad networks or brokers.",
                        "4. Device Permissions" to "This application requires only standard Android Internet connectivity to launch WhatsApp, phone dialer, and Google Maps links. No access to camera, microphone, photo library, or contacts is requested.",
                        "5. Data Storage & Deletion" to "Your booking history remains on your local device storage. You can delete individual bookings at any time directly through the 'My Bookings' tab or by clearing app data in Android settings.",
                        "6. Contact Person" to "If you have any questions or data concerns regarding this policy, please contact:\nM Salman\nI.S Water Tank Cleaner & Solar Plate Wash\nFaisal Town, B Block, Lahore, Pakistan\nPhone / WhatsApp: ${BusinessInfo.PHONE_NUMBER}"
                    )

                    sections.forEach { (heading, body) ->
                        Text(
                            text = heading,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = body,
                            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Play Store Release Details Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "App Release Information",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val releaseInfo = listOf(
                        "App Name" to BusinessInfo.BUSINESS_NAME,
                        "Version" to "1.0.0 (Release Build)",
                        "Target OS" to "Android 14 / 15 / 16 (API 36)",
                        "Architecture" to "Modern Jetpack Compose & Material 3",
                        "Package ID" to "com.aistudio.istankwash.qwrxpz",
                        "Developer Location" to "Faisal Town, Lahore, Pakistan"
                    )

                    releaseInfo.forEach { (key, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = key, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}
