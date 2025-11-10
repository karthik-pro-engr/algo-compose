package com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.stack.NgeResult
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NgeViewModel<T : Comparable<T>>(
    private val ngeCalculator: (List<T>) -> NgeResult<T>?,
    private val parser: (String) -> T?
) : ViewModel() {

    private var _ngeUiState = MutableStateFlow(NgeUiState<T>())
    val ngeUiState = _ngeUiState.asStateFlow()


    fun onEvent(event: NgeEvent) {
        when (event) {
            is NgeEvent.AddItem -> {
                addItem(event.input)
            }

            NgeEvent.ComputeNge -> {
                viewModelScope.launch {
                    val inputList = _ngeUiState.value.inputList
                    if (inputList.size < 2) {
                        _ngeUiState.update { it.copy(errorMessage = "Need at least two items to compute") }
                        return@launch
                    }
                    val result = withContext(Dispatchers.Default) { ngeCalculator(inputList) }
                    _ngeUiState.update {
                        it.copy(
                            ngeResult = result
                        )
                    }
                }
            }

            NgeEvent.Reset -> {
                viewModelScope.launch {
                    _ngeUiState.value = NgeUiState()
                }
            }
        }

    }

    private fun addItem(input: String) {
        viewModelScope.launch {
            val trimmed = input.trim()
            val value =   parser(trimmed)
            val error = when {
                trimmed.isEmpty() -> "Input cannot be empty"
                value == null -> "Invalid input for the selected type"
                else -> null
            }
            if (error != null) {
                _ngeUiState.update { it.copy(errorMessage = error) }
                return@launch
            }
            _ngeUiState.update { it.copy(inputList = it.inputList + value!!, errorMessage = "") }
        }
    }
}