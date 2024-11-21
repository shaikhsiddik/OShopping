package com.example.oshopping

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.oshopping.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OShopperApp : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@OShopperApp)

            modules(
                listOf(
                presentationModule,
                dataModule,
                domainModule)
            )

        }

    }

}