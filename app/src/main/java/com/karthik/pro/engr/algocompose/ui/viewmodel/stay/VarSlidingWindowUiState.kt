package com.karthik.pro.engr.algocompose.ui.viewmodel.stay

import com.karthik.pro.engr.algocompose.domain.stay.ConsecutiveSubArrayAndSize

data class VarSlidingWindowUiState(
    val list: List<Int> = emptyList(),
    var rangeOrMaxCapacity: Int = 0,
    var result: ConsecutiveSubArrayAndSize? = null,
    var errorMessage: String = "",
    var rangeErrorMessage: String = "",
    var showRangeInput: Boolean = true,
    var showArrayItemInput: Boolean = false
)