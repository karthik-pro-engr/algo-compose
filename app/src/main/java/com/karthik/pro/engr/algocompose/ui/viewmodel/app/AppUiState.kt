package com.karthik.pro.engr.algocompose.ui.viewmodel.app

import com.karthik.pro.engr.algocompose.BuildConfig
import com.karthik.pro.engr.algocompose.model.AppScreenModel

data class AppUiState(
    val list: List<AppScreenModel> = listOf(
        AppScreenModel(0, "Balanced Energy"),
        AppScreenModel(1, "Budget Stay")
    ),
    var selectedScreenId: Int? = null,
    var isAppDistributingEnabled: Boolean = BuildConfig.ENABLE_APP_DISTRIBUTION,
    var isSubmittingFeedback: Boolean = false,
    var feedBackState: String = "Hello testers — tap FAB to send feedback"
)