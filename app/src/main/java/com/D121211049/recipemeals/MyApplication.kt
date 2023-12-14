package com.D121211049.recipemeals

import android.app.Application
import com.D121211049.recipemeals.data.AppContainer
import com.D121211049.recipemeals.data.DefaultAppContainer

class MyApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}