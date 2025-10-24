package com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.stack.NgeResult
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NgeViewModel(private val ngeCalculator: (List<Int>) -> NgeResult?) : ViewModel() {

    private var _ngeUiState = MutableStateFlow(NgeUiState())
    val ngeUiState = _ngeUiState


    fun onEvent(event: NgeEvent) {
        when (event) {
            is NgeEvent.AddItem -> {
                viewModelScope.launch {
                    _ngeUiState.update {
                        if (event.input.isEmpty()) {
                            it.copy(errorMessage = "Input size cannot be empty")
                        } else if (event.input.toInt() == 0) {
                            it.copy(errorMessage = "Input size cannot be zero")
                        } else {
                            val list = it.inputList + event.input.toInt()
                            it.copy(errorMessage = "", inputList = list)
                        }
                    }
                }
            }

            NgeEvent.ComputeNge -> {
                viewModelScope.launch {
                    val ngeResult = ngeCalculator(_ngeUiState.value.inputList)
                    _ngeUiState.update {
                        it.copy(
                            ngeResult = ngeResult
                        )
                    }
                }
            }

            NgeEvent.Reset -> {
                viewModelScope.launch {
                    _ngeUiState.update {
                        NgeUiState()
                    }
                }
            }
        }

    }
}