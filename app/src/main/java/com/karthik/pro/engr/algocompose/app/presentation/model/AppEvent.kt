package com.karthik.pro.engr.algocompose.app.presentation.model

sealed class AppEvent {
    object OnBack : AppEvent()
    data class SelectedScreen(val id: ScreenId) : AppEvent()
}