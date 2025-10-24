package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

sealed class NgeEvent {
    data class AddItem(val input: String) : NgeEvent()
    object ComputeNge : NgeEvent()

    object Reset: NgeEvent()

}