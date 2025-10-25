package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model

import com.karthik.pro.engr.algocompose.domain.vsw.ConsecutiveSubArrayAndSize

data class VswUiState(
    val list: List<Int> = emptyList(),
    var capacity: Int = 0,
    var result: ConsecutiveSubArrayAndSize? = null,
    var errorMessage: String = "",
    var capacityErrorMessage: String = "",
    var showRangeInput: Boolean = true,
    var showArrayItemInput: Boolean = false
)