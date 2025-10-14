package com.karthik.pro.engr.algocompose.ui.viewmodel.app

import com.karthik.pro.engr.algocompose.model.AppScreenModel

data class AppUiState(
    val list: List<AppScreenModel> = listOf(
        AppScreenModel(0, "Balanced Energy")
    ),
    var selectedScreenId: Int?=null
)