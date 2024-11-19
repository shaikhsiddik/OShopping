package com.example.domain.usecase

import com.example.domain.repository.CategoryRepository

class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend fun categoryExecute() = categoryRepository.getCategories()

}