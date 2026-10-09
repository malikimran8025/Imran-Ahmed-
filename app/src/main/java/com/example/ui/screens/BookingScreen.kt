package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookingEntity
import com.example.data.model.BusinessInfo
import com.example.data.model.ServiceType
import com.example.ui.MainViewModel
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CleanGreenTertiary
import com.example.ui.theme.OceanBluePrimary
import com.example.ui.theme.SolarGold
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    viewModel: MainViewModel,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val prefilledService by viewModel.prefilledService.collectAsState()
    val prefilledCapacity by viewModel.prefilledCapacity.collectAsState()

    var customerName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var fullAddress by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("Faisal Town, Lahore") }
    var selectedService by remember { mutableStateOf(prefilledService) }
    var capacityOrPanels by remember { mutableStateOf(prefilledCapacity) }
    var preferredDate by remember { mutableStateOf("") }
    var preferredTime by remember { mutableStateOf("10:00 AM - 1:00 PM") }
    var additionalInstructions by remember { mutableStateOf("") }

    var submittedBooking by remember { mutableStateOf<BookingEntity?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    // Validation states
    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    LaunchedEffect(prefilledService, prefilledCapacity) {
        selectedService = prefilledService
        if (prefilledCapacity.isNotBlank()) {
            capacityOrPanels = prefilledCapacity
        }
    }

    val lahoreAreas = listOf(
        "Faisal Town, Lahore",
        "Model Town, Lahore",
        "Garden Town, Lahore",
        "Johar Town, Lahore",
        "Gulberg, Lahore",
        "Township, Lahore",
        "Wapda Town, Lahore",
        "DHA (Defence), Lahore",
        "Bahria Town, Lahore",
        "Allama Iqbal Town, Lahore",
        "Other Area in Lahore"
    )
    var areaDropdownExpanded by remember { mutableStateOf(false) }

    val timeSlots = listOf(
        "Morning (8:00 AM - 11:00 AM)",
        "Midday (11:00 AM - 2:00 PM)",
        "Afternoon (2:00 PM - 5:00 PM)",
        "Evening (5:00 PM - 7:30 PM)"
    )
    var timeDropdownExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("booking_screen"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Request a Cleaning Service",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fill in your details below. Once submitted, your request will be sent to M Salman via WhatsApp for scheduling confirmation.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // Section: Customer Information
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        title = "1. Customer Information",
                        subtitle = "We will contact you on this number to confirm",
                        icon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = customerName,
                        onValueChange = {
                            customerName = it
                            nameError = false
                        },
                        label = { Text("Your Full Name *") },
                        placeholder = { Text("e.g. Usman Malik") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        isError = nameError,
                        supportingText = if (nameError) {
                            { Text("Customer name is required", color = MaterialTheme.colorScheme.error) }
                        } else null,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("customer_name_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = {
                            mobileNumber = it
                            phoneError = false
                        },
                        label = { Text("Mobile Number (WhatsApp) *") },
                        placeholder = { Text("03xx-xxxxxxx") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        isError = phoneError,
                        supportingText = if (phoneError) {
                            { Text("Valid mobile number is required", color = MaterialTheme.colorScheme.error) }
                        } else null,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("mobile_number_input")
                    )
                }
            }
        }

        // Section: Location & Address
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        title = "2. Service Location",
                        subtitle = "Serving Faisal Town and all Lahore areas",
                        icon = Icons.Default.LocationOn
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Area Dropdown
                    ExposedDropdownMenuBox(
                        expanded = areaDropdownExpanded,
                        onExpandedChange = { areaDropdownExpanded = !areaDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = area,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Area / Sector in Lahore *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = areaDropdownExpanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = areaDropdownExpanded,
                            onDismissRequest = { areaDropdownExpanded = false }
                        ) {
                            lahoreAreas.forEach { itemArea ->
                                DropdownMenuItem(
                                    text = { Text(itemArea) },
                                    onClick = {
                                        area = itemArea
                                        areaDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = fullAddress,
                        onValueChange = {
                            fullAddress = it
                            addressError = false
                        },
                        label = { Text("House / Street / Plaza Address *") },
                        placeholder = { Text("e.g. House 42, Block B, Faisal Town") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        isError = addressError,
                        supportingText = if (addressError) {
                            { Text("Full address is required", color = MaterialTheme.colorScheme.error) }
                        } else null,
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("address_input")
                    )
                }
            }
        }

        // Section: Service Details
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        title = "3. Required Service",
                        subtitle = "Select one or both services",
                        icon = Icons.Default.WaterDrop
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ServiceType.values().forEach { sType ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedService = sType },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = if (selectedService == sType) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedService == sType,
                                    onClick = { selectedService = sType }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = sType.displayName,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    val desc = when (sType) {
                                        ServiceType.WATER_TANK -> "Underground/overhead tank sludge drainage & sanitization"
                                        ServiceType.SOLAR_PLATE -> "Rooftop solar plates washing & power output restoration"
                                        ServiceType.BOTH -> "Combined special package: Complete water tank + solar wash"
                                    }
                                    Text(
                                        text = desc,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = capacityOrPanels,
                        onValueChange = { capacityOrPanels = it },
                        label = {
                            Text(
                                when (selectedService) {
                                    ServiceType.WATER_TANK -> "Tank Capacity (e.g. Small / 2000 gal / Underground)"
                                    ServiceType.SOLAR_PLATE -> "Number of Solar Plates (e.g. 10 plates, 5 kW system)"
                                    ServiceType.BOTH -> "Tank Size & Number of Solar Plates"
                                }
                            )
                        },
                        placeholder = { Text("e.g. Medium 2,000 Gallon Tank + 10 Solar Plates") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("capacity_panels_input")
                    )
                }
            }
        }

        // Section: Date & Time Schedule
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        title = "4. Preferred Date & Time",
                        subtitle = "We work 7 days a week from 7 AM to 8 PM",
                        icon = Icons.Default.DateRange
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = preferredDate,
                        onValueChange = {
                            preferredDate = it
                            dateError = false
                        },
                        label = { Text("Preferred Date *") },
                        placeholder = { Text("e.g. Tomorrow / 15 October") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        isError = dateError,
                        supportingText = if (dateError) {
                            { Text("Preferred date is required", color = MaterialTheme.colorScheme.error) }
                        } else null,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("date_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ExposedDropdownMenuBox(
                        expanded = timeDropdownExpanded,
                        onExpandedChange = { timeDropdownExpanded = !timeDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = preferredTime,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Preferred Time Slot *") },
                            leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = timeDropdownExpanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = timeDropdownExpanded,
                            onDismissRequest = { timeDropdownExpanded = false }
                        ) {
                            timeSlots.forEach { slot ->
                                DropdownMenuItem(
                                    text = { Text(slot) },
                                    onClick = {
                                        preferredTime = slot
                                        timeDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = additionalInstructions,
                        onValueChange = { additionalInstructions = it },
                        label = { Text("Additional Instructions / Notes (Optional)") },
                        placeholder = { Text("e.g. Tank is underground in garage, bring long water hose") },
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("instructions_input")
                    )
                }
            }
        }

        // Policy Notice: Booking is Pending until confirmed
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.HourglassTop,
                        contentDescription = "Pending notice",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Booking Policy: Submitting this form sends your request directly to M Salman. Bookings are marked as PENDING until verified by the business via phone call or WhatsApp message.",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        // Submit Booking Button
        item {
            Button(
                onClick = {
                    var hasError = false
                    if (customerName.isBlank()) {
                        nameError = true
                        hasError = true
                    }
                    if (mobileNumber.isBlank()) {
                        phoneError = true
                        hasError = true
                    }
                    if (fullAddress.isBlank()) {
                        addressError = true
                        hasError = true
                    }
                    if (preferredDate.isBlank()) {
                        dateError = true
                        hasError = true
                    }

                    if (hasError) {
                        Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.submitBooking(
                        customerName = customerName,
                        phone = mobileNumber,
                        address = fullAddress,
                        area = area,
                        serviceType = selectedService,
                        capacityOrPanels = if (capacityOrPanels.isBlank()) "Standard" else capacityOrPanels,
                        preferredDate = preferredDate,
                        preferredTime = preferredTime,
                        instructions = additionalInstructions,
                        onComplete = { savedEntity ->
                            submittedBooking = savedEntity
                            showConfirmationDialog = true
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("submit_booking_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Submit Booking & Send to WhatsApp",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        item {
            OutlinedButton(
                onClick = onNavigateToHistory,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("View My Booking History & Status")
            }
        }
    }

    // Confirmation Dialog
    if (showConfirmationDialog && submittedBooking != null) {
        val booking = submittedBooking!!
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.HourglassTop,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "Booking Request Submitted!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "STATUS: PENDING BUSINESS ACCEPTANCE",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "Thank you, ${booking.customerName}! Your booking request for ${booking.serviceType} has been recorded.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "IMPORTANT: As per our policy, your booking is NOT yet confirmed. M Salman will contact you at ${booking.mobileNumber} to confirm team arrival and final details.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Forward this booking directly to M Salman on WhatsApp for instant priority confirmation:",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        IntentUtils.sendBookingToWhatsApp(context, booking)
                        showConfirmationDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Send to WhatsApp Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text("Done")
                }
            }
        )
    }
}
