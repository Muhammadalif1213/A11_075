package com.example.uas_pam

import android.app.Application
import com.example.uas_pam.dependenciesinjection.AppContainer
import com.example.uas_pam.dependenciesinjection.PerpusContainer

class PerpusApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PerpusContainer()
    }
}
