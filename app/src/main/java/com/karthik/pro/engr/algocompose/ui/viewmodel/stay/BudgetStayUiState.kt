package com.karthik.pro.engr.algocompose.ui.viewmodel.stay

import com.karthik.pro.engr.algocompose.domain.stay.ConsecutiveBudgetStay

data class BudgetStayUiState(
    val list: List<Int> = emptyList(),
    var budget: Int = 0,
    var result: ConsecutiveBudgetStay? = null,
    var errorMessage: String = "",
    var budgetErrorMessage: String = "",
    var showBudgetInput: Boolean = true,
    var showCostInput: Boolean = false
)