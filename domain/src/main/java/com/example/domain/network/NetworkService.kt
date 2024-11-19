package com.example.domain.network

import com.example.domain.model.CategoryListModel
import com.example.domain.model.Product
import com.example.domain.model.ProductListModel

interface NetworkService {

    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>

    suspend fun getCategory(): ResultWrapper<CategoryListModel>

}

sealed class ResultWrapper<out T>{

    data class Success<out T>(val value: T): ResultWrapper<T>()

    data class Failure(val error: Throwable): ResultWrapper<Nothing>()

}