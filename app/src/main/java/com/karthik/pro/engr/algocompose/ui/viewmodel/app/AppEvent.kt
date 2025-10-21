package com.karthik.pro.engr.algocompose.ui.viewmodel.app

sealed class AppEvent {
    object OnBack : AppEvent()
    data class SelectedScreen(val id:Int): AppEvent()
}