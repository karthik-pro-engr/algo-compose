package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

import com.karthik.pro.engr.algocompose.domain.stack.NgeResult

data class NgeUiState(
    val inputList: List<Int> = emptyList(),
    var errorMessage: String = "",
    val ngeResult: NgeResult? = null
)