package com.karthik.pro.engr.algocompose.feedback

import androidx.activity.ComponentActivity

fun provideAppFeedbackController(activity: ComponentActivity): AppFeedbackController =
    NoOpAppFeedbackController()