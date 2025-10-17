package com.karthik.pro.engr.algocompose

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val app = FirebaseApp.initializeApp(this)
        Log.d("App", "FirebaseApp.initializeApp returned: ${app?.name ?: "null"}")
        // Also list installed Firebase apps for debugging
        val apps = FirebaseApp.getApps(this)
        Log.d("App", "FirebaseApp.getApps(): ${apps.map { it.name }}")
    }
}
