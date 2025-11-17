package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model

sealed class MonotonicEvent {
    data class AddItem(val input: String) : MonotonicEvent()
    object ComputeMonotonic : MonotonicEvent()

    object Reset: MonotonicEvent()

}