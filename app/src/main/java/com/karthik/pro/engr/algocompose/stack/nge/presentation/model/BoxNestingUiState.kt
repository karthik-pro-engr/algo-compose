package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

import com.karthik.pro.engr.algocompose.domain.stack.BoxNestingOrder

data class BoxNestingUiState(
    val boxSizesList: List<Int> = emptyList(),
    var errorMessage: String = "",
    val boxNestingOrder: BoxNestingOrder? = null
)