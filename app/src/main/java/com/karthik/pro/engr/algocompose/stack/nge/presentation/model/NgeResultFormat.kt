package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

data class NgeResultFormat<T>(
    val maxOfDigits: Int,
    val sbCapacityEstimate: Int,
    val actualIndex: Int,
    val computedIndex: Int,
    val actualValue: T,
    val computedValue: T?,
    val unitSuffix:String
)
