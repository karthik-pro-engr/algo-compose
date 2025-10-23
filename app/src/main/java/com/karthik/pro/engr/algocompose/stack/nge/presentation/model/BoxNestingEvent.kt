package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

sealed class BoxNestingEvent {
    data class AddBoxSize(val size: String) : BoxNestingEvent()
    object ComputeBoxNesting : BoxNestingEvent()

    object Reset: BoxNestingEvent()

}