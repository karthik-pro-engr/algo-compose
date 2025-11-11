package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

data class NgeResultFormat<T>(
    val maxOfDigits: Int,
    val sbCapacityEstimate: Int,
    val actualIndex: Int,
    val nextGreaterIndex: Int,
    val actualValue: T,
    val nextGreaterValue: T?,
    val unitSuffix:String
)
