package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.vsw.ConsecutiveSubArrayAndSize
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswEvent
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VswViewModel(
    private val vswCalculator: (Int, List<Int>) -> ConsecutiveSubArrayAndSize
) : ViewModel() {
    private var _uiState = MutableStateFlow(VswUiState())
    val uiState = _uiState

    fun onEvent(event: VswEvent) {

        when (event) {
            is VswEvent.AddRange -> {
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
                            capacity = rangeOrMaxCapacity, rangeErrorMessage = error,
                            showRangeInput = showRangeInput,
                            showArrayItemInput = showArrayItemInput
                        )
                    }
                }
            }

            is VswEvent.AddInputForArray -> {
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

            VswEvent.ComputeVsw -> {
                viewModelScope.launch {
                    val results =
                        vswCalculator(
                            _uiState.value.capacity,
                            _uiState.value.list
                        )
                    _uiState.update {
                        it.copy(
                            result = results
                        )
                    }
                }
            }

            VswEvent.Reset -> {
                viewModelScope.launch {
                    _uiState.update {
                        VswUiState()
                    }
                }

            }

        }

    }

}