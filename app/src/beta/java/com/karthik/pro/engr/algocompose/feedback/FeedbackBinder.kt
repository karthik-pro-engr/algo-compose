package com.karthik.pro.engr.algocompose.feedback

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.feedback.impl.FeedbackViewModelFactory
import com.karthik.pro.engr.feedback.impl.FirebaseFeedbackSender
import com.karthik.pro.engr.feedback.impl.ui.viewmodel.FeedbackViewModel

fun provideAppFeedbackController(activity: ComponentActivity): AppFeedbackController {
    val firebaseFeedbackSender = FirebaseFeedbackSender()
    val feedbackViewModelFactory = FeedbackViewModelFactory(firebaseFeedbackSender)
    val vm =
        ViewModelProvider(activity, feedbackViewModelFactory).get(FeedbackViewModel::class.java)
    return BetaAppFeedbackController(vm)
}