package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

import com.karthik.pro.engr.algocompose.domain.stack.NgeResult

data class NgeUiState<T: Comparable<T>>(
    val inputList: List<T> = emptyList(),
    var errorMessage: String = "",
    val ngeResult: NgeResult<T>? = null
)