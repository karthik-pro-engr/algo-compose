package com.karthik.pro.engr.algocompose.ui.viewmodel.app

sealed class UiEffect {
    data class ShowToast(val message: String) : UiEffect()
    object OpenEmailIntent : UiEffect()
}