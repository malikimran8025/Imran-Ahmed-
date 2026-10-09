package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusinessInfo
import com.example.data.model.ServiceType
import com.example.ui.MainViewModel
import com.example.ui.screens.BookingScreen
import com.example.ui.screens.BookingsHistoryScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PriceListScreen
import com.example.ui.screens.PrivacyPolicyScreen
import com.example.ui.screens.SolarWashScreen
import com.example.ui.screens.SupportScreen
import com.example.ui.screens.TankCleaningScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

enum class AppScreen(val title: String, val icon: ImageVector? = null) {
    HOME("I.S Tank & Solar Wash", Icons.Default.Home),
    TANK_CLEANING("Water Tank Cleaning", Icons.Default.WaterDrop),
    SOLAR_WASH("Solar Plate Washing", Icons.Default.WbSunny),
    PRICE_LIST("Service Price List", Icons.Default.Payments),
    BOOKING("Book a Service", Icons.Default.DateRange),
    BOOKING_HISTORY("My Bookings", Icons.Default.History),
    CONTACT("Contact M Salman", Icons.Default.Phone),
    SUPPORT("Help & FAQs", Icons.Default.SupportAgent),
    PRIVACY_POLICY("Privacy & Play Store", Icons.Default.Policy)
}

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(viewModel: MainViewModel) {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var showMenu by remember { mutableStateOf(false) }

    // Intercept back button when not on Home screen
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        currentScreen = AppScreen.HOME
    }

    val bottomNavItems = listOf(
        AppScreen.HOME,
        AppScreen.TANK_CLEANING,
        AppScreen.SOLAR_WASH,
        AppScreen.PRICE_LIST,
        AppScreen.BOOKING
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = currentScreen.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                },
                navigationIcon = {
                    if (currentScreen != AppScreen.HOME) {
                        IconButton(
                            onClick = { currentScreen = AppScreen.HOME },
                            modifier = Modifier.testTag("top_bar_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { /* No-op, logo display */ },
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_is_logo),
                                contentDescription = "I.S Logo",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { IntentUtils.dialPhoneNumber(context, BusinessInfo.PHONE_NUMBER) },
                        modifier = Modifier.testTag("appbar_call_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call Salman",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { IntentUtils.openWhatsAppChat(context) },
                        modifier = Modifier.testTag("appbar_whatsapp_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "WhatsApp Chat",
                            tint = WhatsAppGreen
                        )
                    }

                    Box {
                        IconButton(
                            onClick = { showMenu = true },
                            modifier = Modifier.testTag("appbar_overflow_menu")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options"
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("My Booking History") },
                                onClick = {
                                    showMenu = false
                                    currentScreen = AppScreen.BOOKING_HISTORY
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.History, contentDescription = null)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Contact M Salman") },
                                onClick = {
                                    showMenu = false
                                    currentScreen = AppScreen.CONTACT
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Phone, contentDescription = null)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Customer Support & FAQs") },
                                onClick = {
                                    showMenu = false
                                    currentScreen = AppScreen.SUPPORT
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.SupportAgent, contentDescription = null)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Privacy Policy & Play Store") },
                                onClick = {
                                    showMenu = false
                                    currentScreen = AppScreen.PRIVACY_POLICY
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Policy, contentDescription = null)
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                bottomNavItems.forEach { screen ->
                    val isSelected = currentScreen == screen
                    val labelText = when (screen) {
                        AppScreen.HOME -> "Home"
                        AppScreen.TANK_CLEANING -> "Tank"
                        AppScreen.SOLAR_WASH -> "Solar"
                        AppScreen.PRICE_LIST -> "Prices"
                        AppScreen.BOOKING -> "Book"
                        else -> screen.name
                    }
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = screen },
                        icon = {
                            screen.icon?.let { icon ->
                                Icon(imageVector = icon, contentDescription = labelText)
                            }
                        },
                        label = {
                            Text(
                                text = labelText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        modifier = Modifier.testTag("nav_item_${labelText.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    AppScreen.HOME -> HomeScreen(
                        onNavigateToTankCleaning = { currentScreen = AppScreen.TANK_CLEANING },
                        onNavigateToSolarWash = { currentScreen = AppScreen.SOLAR_WASH },
                        onNavigateToBooking = { serviceType ->
                            viewModel.setPrefilledBooking(serviceType)
                            currentScreen = AppScreen.BOOKING
                        },
                        onNavigateToPriceList = { currentScreen = AppScreen.PRICE_LIST },
                        onNavigateToContact = { currentScreen = AppScreen.CONTACT }
                    )

                    AppScreen.TANK_CLEANING -> TankCleaningScreen(
                        viewModel = viewModel,
                        onBookService = { serviceType, capacity ->
                            viewModel.setPrefilledBooking(serviceType, capacity)
                            currentScreen = AppScreen.BOOKING
                        },
                        onViewPrices = { currentScreen = AppScreen.PRICE_LIST }
                    )

                    AppScreen.SOLAR_WASH -> SolarWashScreen(
                        viewModel = viewModel,
                        onBookService = { serviceType, panels ->
                            viewModel.setPrefilledBooking(serviceType, panels)
                            currentScreen = AppScreen.BOOKING
                        },
                        onViewPrices = { currentScreen = AppScreen.PRICE_LIST }
                    )

                    AppScreen.PRICE_LIST -> PriceListScreen(
                        viewModel = viewModel,
                        onBookService = { serviceType, details ->
                            viewModel.setPrefilledBooking(serviceType, details)
                            currentScreen = AppScreen.BOOKING
                        }
                    )

                    AppScreen.BOOKING -> BookingScreen(
                        viewModel = viewModel,
                        onNavigateToHistory = { currentScreen = AppScreen.BOOKING_HISTORY }
                    )

                    AppScreen.BOOKING_HISTORY -> BookingsHistoryScreen(
                        viewModel = viewModel,
                        onNavigateToNewBooking = { currentScreen = AppScreen.BOOKING }
                    )

                    AppScreen.CONTACT -> ContactScreen()

                    AppScreen.SUPPORT -> SupportScreen()

                    AppScreen.PRIVACY_POLICY -> PrivacyPolicyScreen()
                }
            }
        }
    }
}
