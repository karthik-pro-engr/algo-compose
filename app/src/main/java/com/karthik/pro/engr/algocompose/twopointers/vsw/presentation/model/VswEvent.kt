package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model

sealed class VswEvent {
    data class AddRange(val rangeOrMaxCapacity: String) : VswEvent()
    data class AddInputForArray(val price: String) : VswEvent()
    object ComputeVsw : VswEvent()
    object Reset : VswEvent()
}