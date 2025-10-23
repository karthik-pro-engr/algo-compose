package com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.stack.NextGreaterElementCalculator
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.BoxNestingEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.BoxNestingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BoxNestingViewModel : ViewModel() {

    private var _boxNestingUiState = MutableStateFlow(BoxNestingUiState())
    val boxNestingUiState = _boxNestingUiState


    fun onEvent(event: BoxNestingEvent) {
        when (event) {
            is BoxNestingEvent.AddBoxSize -> {
                viewModelScope.launch {
                    _boxNestingUiState.update {
                        if (event.size.isEmpty()) {
                            it.copy(errorMessage = "Input size cannot be empty")
                        } else if (event.size.toInt() == 0) {
                            it.copy(errorMessage = "Input size cannot be zero")
                        } else {
                            val list = it.boxSizesList + event.size.toInt()
                            it.copy(errorMessage = "", boxSizesList = list)
                        }
                    }
                }
            }

            BoxNestingEvent.ComputeBoxNesting -> {
                viewModelScope.launch {
                    _boxNestingUiState.update {
                        it.copy(boxNestingOrder = NextGreaterElementCalculator.computeNextGreaterElement(
                            it.boxSizesList
                        )
                        )
                    }
                }
            }

            BoxNestingEvent.Reset -> {
                viewModelScope.launch {
                    _boxNestingUiState.update {
                        BoxNestingUiState()
                    }
                }
            }
        }

    }
}