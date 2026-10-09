package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusinessInfo
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CleanGreenTertiary
import com.example.ui.theme.OceanBluePrimary
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

data class FaqItem(
    val question: String,
    val answer: String
)

@Composable
fun SupportScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var enquiryText by remember { mutableStateOf("") }
    var enquiryCustomerName by remember { mutableStateOf("") }

    val faqs = remember {
        listOf(
            FaqItem(
                question = "How often should underground and overhead water tanks be cleaned in Lahore?",
                answer = "In Lahore, underground and overhead water tanks should be cleaned every 4 to 6 months. Due to municipal water supply silt, dust, and rust in pipeline networks, sludge and fungal bacteria settle rapidly at the bottom, posing severe risks for typhoid, amoeba, stomach infections, and skin allergies."
            ),
            FaqItem(
                question = "How much does dirty solar panels reduce power generation?",
                answer = "Rooftop solar panels in Lahore suffer from heavy airborne dust, vehicle soot, and bird droppings, causing a 25% to 35% reduction in electricity generation! Washing them every 2 to 3 weeks restores optimal solar kilowatt (kW) output, maximizing your net-metering savings."
            ),
            FaqItem(
                question = "Do your cleaners bring their own pressure equipment and ladder?",
                answer = "Yes! I.S Water Tank Cleaner & Solar Plate Wash brings our own high-pressure washer machines, de-watering submersible suction pumps, specialized soft microfiber telescopic brushes, and safety equipment. The customer only needs to provide access to a water point and electrical outlet."
            ),
            FaqItem(
                question = "Do you use chemical bleach or safe sanitizers for water tanks?",
                answer = "We strictly use non-toxic, food-grade antibacterial and chlorine sanitizers. We thoroughly vacuum and rinse the entire tank so that the stored drinking water remains odorless, crystal clean, and 100% safe for consumption."
            ),
            FaqItem(
                question = "How long does a water tank cleaning service take?",
                answer = "An overhead plastic or fiberglass tank typically takes 1 to 1.5 hours. A large underground concrete tank takes approximately 2 to 3 hours depending on mud sediment depth."
            ),
            FaqItem(
                question = "Will washing solar panels cause scratches on the glass?",
                answer = "Never! We use specialized ultra-soft telescopic wash brushes and filtered water specifically engineered for photovoltaic glass. We never use rough brooms or harsh abrasive detergents."
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("support_screen"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Customer Support Hero
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Friendly Customer Support",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "We are here to assist with any questions, price guidance, or booking requests",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { IntentUtils.dialPhoneNumber(context, BusinessInfo.PHONE_NUMBER) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OceanBluePrimary)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call M Salman", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                IntentUtils.openWhatsAppChat(context, "Assalam-o-Alaikum M Salman, I have a customer support enquiry.")
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("WhatsApp Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // FAQs Section
        item {
            SectionHeader(
                title = "Frequently Asked Questions",
                subtitle = "Common questions about tank hygiene & solar wash",
                icon = Icons.Default.QuestionAnswer
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    faqs.forEachIndexed { index, faq ->
                        ExpandableFaqItem(faq = faq)
                        if (index < faqs.size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        // Quick Enquiry & Complaints Form
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        title = "Send Quick Enquiry or Complaint",
                        subtitle = "Directly sent to M Salman for immediate resolution",
                        icon = Icons.Default.ReportProblem
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = enquiryCustomerName,
                        onValueChange = { enquiryCustomerName = it },
                        label = { Text("Your Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = enquiryText,
                        onValueChange = { enquiryText = it },
                        label = { Text("Describe your question, quote request, or feedback") },
                        maxLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (enquiryText.isBlank()) {
                                Toast.makeText(context, "Please enter your message", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val msg = "Assalam-o-Alaikum M Salman,\nSupport Enquiry from ${if (enquiryCustomerName.isNotBlank()) enquiryCustomerName else "Customer"}:\n$enquiryText"
                            IntentUtils.openWhatsAppChat(context, msg)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send via WhatsApp Support")
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableFaqItem(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = faq.answer,
                            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}
