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
object CartSummaryScreen

@Serializable
data class UserAddressRoute(val userAddressWrapper: UserAddressRouteWrapper)

@Serializable
data class ProductDetailScreen(val product: UiProductModel)

