package com.example.domain.usecase

import com.example.domain.repository.ProductRepository

class GetProductUseCase(private val productRepository: ProductRepository) {

    suspend fun getProductExecute() = productRepository.getProducts()

}