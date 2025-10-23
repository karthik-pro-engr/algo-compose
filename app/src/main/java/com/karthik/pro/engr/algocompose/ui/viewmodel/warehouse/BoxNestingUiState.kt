package com.karthik.pro.engr.algocompose.ui.viewmodel.warehouse

import com.karthik.pro.engr.algocompose.domain.stack.BoxNestingOrder

data class BoxNestingUiState(
    val boxSizesList: List<Int> = emptyList(),
    var errorMessage: String = "",
    val boxNestingOrder: BoxNestingOrder? = null
)