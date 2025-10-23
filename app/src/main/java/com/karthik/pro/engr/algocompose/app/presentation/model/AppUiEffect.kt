package com.karthik.pro.engr.algocompose.app.presentation.model

sealed class AppUiEffect {
    data class ShowToast(val message: String) : AppUiEffect()
    object OpenEmailIntent : AppUiEffect()
}