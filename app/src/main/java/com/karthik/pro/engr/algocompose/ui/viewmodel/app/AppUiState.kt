package com.karthik.pro.engr.algocompose.ui.viewmodel.app

import com.karthik.pro.engr.algocompose.model.AppScreenModel

data class AppUiState(
    val list: List<AppScreenModel> = listOf(
        AppScreenModel(0, "Balanced Energy"),
        AppScreenModel(1, "Budget Stay")
    ),
    var selectedScreenId: Int?=null
)