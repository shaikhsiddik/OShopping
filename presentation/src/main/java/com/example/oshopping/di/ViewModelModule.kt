package com.example.oshopping.di

import com.example.oshopping.ui.feature.cart.CartViewModel
import com.example.oshopping.ui.feature.home.HomeViewModel
import com.example.oshopping.ui.feature.product_details.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {

        HomeViewModel(get(),get())

    }

    viewModel {

        ProductDetailsViewModel(get())

    }

    viewModel{

        CartViewModel(get())

    }

}