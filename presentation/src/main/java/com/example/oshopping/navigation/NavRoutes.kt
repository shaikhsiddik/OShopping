package com.example.oshopping.navigation

import com.example.oshopping.model.UiProductModel
import kotlinx.serialization.Serializable


@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetailScreen(val product: UiProductModel)

