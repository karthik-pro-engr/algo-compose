package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model

import com.karthik.pro.engr.algocompose.domain.stack.MonotonicResult

data class MonotonicUiState<T: Comparable<T>>(
    val inputList: List<T> = emptyList(),
    var errorMessage: String = "",
    val monotonicResult: MonotonicResult<T>? = null
)