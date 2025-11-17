package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.algocompose.domain.stack.MonotonicResult
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicEvent
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MonotonicViewModel<T : Comparable<T>>(
    private val ngeCalculator: (List<T>) -> MonotonicResult<T>?,
    private val parser: (String) -> T?
) : ViewModel() {

    private var _monotonicUiState = MutableStateFlow(MonotonicUiState<T>())
    val monotonicUiState = _monotonicUiState.asStateFlow()


    fun onEvent(event: MonotonicEvent) {
        when (event) {
            is MonotonicEvent.AddItem -> {
                addItem(event.input)
            }

            MonotonicEvent.ComputeMonotonic -> {
                viewModelScope.launch {
                    val inputList = _monotonicUiState.value.inputList
                    if (inputList.size < 2) {
                        _monotonicUiState.update { it.copy(errorMessage = "Need at least two items to compute") }
                        return@launch
                    }
                    val result = withContext(Dispatchers.Default) { ngeCalculator(inputList) }
                    _monotonicUiState.update {
                        it.copy(
                            monotonicResult = result
                        )
                    }
                }
            }

            MonotonicEvent.Reset -> {
                viewModelScope.launch {
                    _monotonicUiState.value = MonotonicUiState()
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
                _monotonicUiState.update { it.copy(errorMessage = error) }
                return@launch
            }
            _monotonicUiState.update { it.copy(inputList = it.inputList + value!!, errorMessage = "") }
        }
    }
}