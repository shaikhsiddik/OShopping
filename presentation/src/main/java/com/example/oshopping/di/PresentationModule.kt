package com.example.oshopping.di

import org.koin.dsl.module

val presentationModule = module {

    includes(viewModelModule)

}