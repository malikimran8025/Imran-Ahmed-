package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.DefaultPrices
import com.example.data.model.PriceCategory
import com.example.data.model.PriceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PriceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("is_pricing_prefs", Context.MODE_PRIVATE)

    private val _prices = MutableStateFlow<List<PriceItem>>(loadPrices())
    val prices: StateFlow<List<PriceItem>> = _prices.asStateFlow()

    private fun loadPrices(): List<PriceItem> {
        return DefaultPrices.items.map { defaultItem ->
            if (defaultItem.isCustomQuote) {
                defaultItem
            } else {
                val savedPrice = prefs.getInt("price_${defaultItem.id}", defaultItem.pricePkr)
                val savedDesc = prefs.getString("desc_${defaultItem.id}", defaultItem.description) ?: defaultItem.description
                defaultItem.copy(
                    pricePkr = savedPrice,
                    description = savedDesc
                )
            }
        }
    }

    fun updatePrice(id: String, newPricePkr: Int, newDescription: String? = null) {
        val editor = prefs.edit()
        editor.putInt("price_$id", newPricePkr)
        if (newDescription != null) {
            editor.putString("desc_$id", newDescription)
        }
        editor.apply()
        _prices.value = loadPrices()
    }

    fun resetToDefaults() {
        val editor = prefs.edit()
        DefaultPrices.items.forEach { item ->
            editor.remove("price_${item.id}")
            editor.remove("desc_${item.id}")
        }
        editor.apply()
        _prices.value = loadPrices()
    }

    fun getPricesByCategory(category: PriceCategory): List<PriceItem> {
        return _prices.value.filter { it.category == category }
    }
}
