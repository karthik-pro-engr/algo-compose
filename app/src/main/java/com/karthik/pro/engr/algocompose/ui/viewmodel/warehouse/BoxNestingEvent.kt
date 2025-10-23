package com.karthik.pro.engr.algocompose.ui.viewmodel.warehouse

sealed class BoxNestingEvent {
    data class AddBoxSize(val size: String) : BoxNestingEvent()
    object ComputeBoxNesting : BoxNestingEvent()

    object Reset: BoxNestingEvent()

}