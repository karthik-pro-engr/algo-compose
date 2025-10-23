package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.vsw.ConsecutiveStretchCalculator
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VarSlidingWindowEvent
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VarSlidingWindowUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VarSlidingWindowViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(VarSlidingWindowUiState())
    val uiState = _uiState

    fun onEvent(event: VarSlidingWindowEvent) {

        when (event) {
            is VarSlidingWindowEvent.AddRange -> {
                viewModelScope.launch {
                    _uiState.update {
                        var rangeOrMaxCapacity = 0
                        var showRangeInput = true
                        var showArrayItemInput = false
                        val error = when {
                            event.rangeOrMaxCapacity.isEmpty() -> {
                                "Range or MaxCapacity cannot be empty"
                            }

                            event.rangeOrMaxCapacity.toInt() == 0 -> {
                                "Range or MaxCapacity  should be above zero"
                            }

                            else -> {
                                rangeOrMaxCapacity = event.rangeOrMaxCapacity.toInt()
                                showRangeInput = false
                                showArrayItemInput = true
                                ""
                            }
                        }
                        it.copy(
                            rangeOrMaxCapacity = rangeOrMaxCapacity, rangeErrorMessage = error,
                            showRangeInput = showRangeInput,
                            showArrayItemInput = showArrayItemInput
                        )
                    }
                }
            }

            is VarSlidingWindowEvent.AddInputForArray -> {
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

            VarSlidingWindowEvent.ComputeVarSlidingWindow -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            result = ConsecutiveStretchCalculator.computeResult(
                                _uiState.value.rangeOrMaxCapacity,
                                _uiState.value.list
                            )
                        )
                    }
                }
            }

            VarSlidingWindowEvent.Reset -> {
                viewModelScope.launch {
                    _uiState.update {
                        VarSlidingWindowUiState()
                    }
                }

            }

        }

    }

}