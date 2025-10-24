package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

data class NgeResultFormat(
    val maxOfDigits: Int,
    val sbCapacityEstimate: Int,
    val actualIndex: Int,
    val nextGreaterIndex: Int,
    val actualValue: Int,
    val nextGreaterValue: Int,
    val unitSuffix:String
)
