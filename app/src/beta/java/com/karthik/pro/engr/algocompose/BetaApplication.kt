package com.karthik.pro.engr.algocompose

import android.app.Application
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.karthik.pro.engr.algocompose.feedback.BetaAppFeedbackController
import com.karthik.pro.engr.algocompose.feedback.FeedbackBinder
import com.karthik.pro.engr.feedback.impl.FeedbackViewModelFactory
import com.karthik.pro.engr.feedback.impl.FirebaseFeedbackSender
import com.karthik.pro.engr.feedback.impl.ui.viewmodel.FeedbackViewModel

class BetaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val app = FirebaseApp.initializeApp(this)
        Log.d("BetaApplication", "FirebaseApp.initializeApp returned: ${app?.name ?: "null"}")
        // Also list installed Firebase apps for debugging
        val apps = FirebaseApp.getApps(this)
        Log.d("BetaApplication", "FirebaseApp.getApps(): ${apps.map { it.name }}")
        FeedbackBinder.provideAppFeedbackController = { activity: ComponentActivity ->
            val firebaseFeedbackSender = FirebaseFeedbackSender()
            val feedbackViewModelFactory = FeedbackViewModelFactory(firebaseFeedbackSender)
            val vm =
                ViewModelProvider(
                    activity,
                    feedbackViewModelFactory
                ).get(FeedbackViewModel::class.java)
            BetaAppFeedbackController(vm)
        }
    }
}