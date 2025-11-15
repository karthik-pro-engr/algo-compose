package com.karthik.pro.engr.algocompose.app.presentation.model

import com.karthik.pro.engr.algocompose.BuildConfig

data class AppUiState(
    val list: List<AppScreenModel> = listOf(
        AppScreenModel(ScreenId.BALANCED_ENERGY, "Balanced Energy"),
        AppScreenModel(ScreenId.BUDGET_STAY, "Budget Stay"),
        AppScreenModel(ScreenId.VIDEO_PLAY_REQUESTS, "Video Play Request"),
        AppScreenModel(ScreenId.BOX_NESTING, "Box Nesting"),
        AppScreenModel(ScreenId.WIND_GUSTS, "Wind Gusts"),
        AppScreenModel(ScreenId.RIVER_GAUGE, "River Gauge"),
        AppScreenModel(ScreenId.FUEL_TANK_BALANCER, "Fuel Tank Balancer"),

    ),
    var selectedScreenId: ScreenId? = null,
    var isAppDistributingEnabled: Boolean = BuildConfig.ENABLE_APP_DISTRIBUTION
)

enum class ScreenId {
    DEFAULT,
    BALANCED_ENERGY,
    BUDGET_STAY,
    VIDEO_PLAY_REQUESTS,
    BOX_NESTING,
    WIND_GUSTS,

    RIVER_GAUGE,
    FUEL_TANK_BALANCER
}