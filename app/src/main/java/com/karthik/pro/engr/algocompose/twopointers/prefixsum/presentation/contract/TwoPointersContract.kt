package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.contract

import com.karthik.pro.engr.algocompose.domain.energy.StretchResult

interface TwoPointersContract<T> {

    val inputList: List<T>
    val stretchResult: StretchResult?
    val errorMessage: String

    fun addInput(value: T)

    fun setErrorMessage(error: String)

    fun compute(parser: ((T) -> Int)? = null)

    fun reset()
}