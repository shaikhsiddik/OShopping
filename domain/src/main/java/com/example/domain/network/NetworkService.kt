package com.example.domain.network

import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.model.CartSummary
import com.example.domain.model.CategoryListModel
import com.example.domain.model.ProductListModel
import com.example.domain.model.request.AddCartRequestModel

interface NetworkService {

    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>

    suspend fun getCategory(): ResultWrapper<CategoryListModel>

    suspend fun addProductToCart(addCartRequestModel: AddCartRequestModel): ResultWrapper<CartModel>

    suspend fun getCart(): ResultWrapper<CartModel>

    suspend fun updateQuantity(cartItemModel: CartItemModel): ResultWrapper<CartModel>

    suspend fun deleteItem(cartItemId: Int, userId: Int): ResultWrapper<CartModel>

    suspend fun getCartSummary(userId: Int): ResultWrapper<CartSummary>

}

sealed class ResultWrapper<out T>{

    data class Success<out T>(val value: T): ResultWrapper<T>()

    data class Failure(val error: Throwable): ResultWrapper<Nothing>()

}