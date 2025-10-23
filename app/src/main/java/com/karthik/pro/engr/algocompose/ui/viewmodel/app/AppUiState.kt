package com.karthik.pro.engr.algocompose.ui.viewmodel.app

import com.karthik.pro.engr.algocompose.BuildConfig
import com.karthik.pro.engr.algocompose.model.AppScreenModel

data class AppUiState(
    val list: List<AppScreenModel> = listOf(
        AppScreenModel(ScreenId.BALANCED_ENERGY, "Balanced Energy"),
        AppScreenModel(ScreenId.BUDGET_STAY, "Budget Stay"),
        AppScreenModel(ScreenId.VIDEO_PLAY_REQUESTS, "Video Play Request"),
        AppScreenModel(ScreenId.BOX_NESTING, "Box Nesting")
    ),
    var selectedScreenId: ScreenId? = null,
    var isAppDistributingEnabled: Boolean = BuildConfig.ENABLE_APP_DISTRIBUTION
)

enum class ScreenId {
    DEFAULT,
    BALANCED_ENERGY,
    BUDGET_STAY,
    VIDEO_PLAY_REQUESTS,
    BOX_NESTING
}