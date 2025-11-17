package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model

data class MonotonicResultFormat<T>(
    val maxOfDigits: Int,
    val sbCapacityEstimate: Int,
    val actualIndex: Int,
    val computedIndex: Int,
    val actualValue: T,
    val computedValue: T?,
    val unitSuffix:String
)
