package com.karthik.pro.engr.algocompose.feedback

import androidx.activity.ComponentActivity


object FeedbackBinder {
    var provideAppFeedbackController: (ComponentActivity) -> AppFeedbackController =
        { _ ->
            NoOpAppFeedbackController()
        }
}