package com.example.data.di

import com.example.data.repository.CartRepositoryImpl
import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.CartRepository
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<CategoryRepository> { CategoryRepositoryImpl(get()) }

    single<ProductRepository> { ProductRepositoryImpl(get()) }

    single<CartRepository> { CartRepositoryImpl(get()) }

}