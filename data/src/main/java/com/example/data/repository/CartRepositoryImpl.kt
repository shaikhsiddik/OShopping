package com.example.data.repository

import com.example.data.network.NetworkServiceImpl
import com.example.domain.model.CartModel
import com.example.domain.model.request.AddCartRequestModel
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CartRepository

class CartRepositoryImpl(private val networkServiceImpl: NetworkServiceImpl) : CartRepository {

    override suspend fun addProductToCart(request: AddCartRequestModel): ResultWrapper<CartModel> {

        return networkServiceImpl.addProductToCart(request)

    }

    override suspend fun getCart(): ResultWrapper<CartModel> {

        return networkServiceImpl.getCart()

    }
}