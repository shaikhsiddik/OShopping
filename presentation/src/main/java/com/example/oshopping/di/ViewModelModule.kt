package com.example.oshopping.di

import com.example.oshopping.ui.feature.home.HomeViewModel
import com.example.oshopping.ui.feature.product_details.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {

        HomeViewModel(get(),get())

    }

    viewModel {

        ProductViewModel()

    }

}