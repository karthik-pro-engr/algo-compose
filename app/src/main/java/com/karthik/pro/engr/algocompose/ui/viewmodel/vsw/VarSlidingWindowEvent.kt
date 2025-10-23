package com.karthik.pro.engr.algocompose.ui.viewmodel.vsw

sealed class VarSlidingWindowEvent {
    data class AddRange(val rangeOrMaxCapacity: String) : VarSlidingWindowEvent()
    data class AddInputForArray(val price: String) : VarSlidingWindowEvent()
    object ComputeVarSlidingWindow : VarSlidingWindowEvent()
    object Reset : VarSlidingWindowEvent()
}