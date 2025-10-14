package com.karthik.pro.engr.algocompose.ui.viewmodel.stay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.stay.BudgetStayCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BudgetStayViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(BudgetStayUiState())
    val uiState = _uiState

    fun onEvent(event: BudgetStayEvent) {

        when (event) {
            is BudgetStayEvent.AddBudget -> {
                viewModelScope.launch {
                    _uiState.update {
                        var budget = 0
                        var showBudgetInput = true
                        var showCostInput = false
                        val error = when {
                            event.budget.isEmpty() -> {
                                "Budget cannot be empty"
                            }

                            event.budget.toInt() == 0 -> {
                                "Budget should be above zero"
                            }

                            else -> {
                                budget = event.budget.toInt()
                                showBudgetInput = false
                                showCostInput = true
                                ""
                            }
                        }
                        it.copy(
                            budget = budget, budgetErrorMessage = error,
                            showBudgetInput = showBudgetInput,
                            showCostInput = showCostInput
                        )
                    }
                }
            }

            is BudgetStayEvent.AddHotelPrice -> {
                viewModelScope.launch {
                    _uiState.update {
                        when {
                            event.price.isEmpty() -> {
                                it.copy(errorMessage = "Input cannot be empty")
                            }

                            event.price.toInt() == 0 -> {
                                it.copy(errorMessage = "Input cost should be above zero")
                            }

                            else -> {
                                val list = it.list + event.price.toInt()
                                it.copy(list = list, errorMessage = "")
                            }
                        }
                    }

                }
            }

            BudgetStayEvent.ComputeBudgetStay -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            result = BudgetStayCalculator.computeBudgetStay(
                                _uiState.value.budget,
                                _uiState.value.list
                            )
                        )
                    }
                }
            }

            BudgetStayEvent.Reset -> {
                viewModelScope.launch {
                    _uiState.update {
                        BudgetStayUiState()
                    }
                }

            }

        }

    }

}