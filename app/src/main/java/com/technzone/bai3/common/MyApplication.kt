package com.technzone.bai3.common

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@HiltAndroidApp
class MyApplication @Inject constructor() : Application() {

    public var deeplink_id = ""
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    companion object {
        lateinit var instance: MyApplication
    }
}
