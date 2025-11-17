package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.algocompose.domain.stack.MonotonicResult

class MonotonicViewModelFactory<T : Comparable<T>>(
    private val ngeCalculator: (List<T>) -> MonotonicResult<T>?,
    private val parser: (String) -> T?
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonotonicViewModel::class.java)) {
            return MonotonicViewModel(ngeCalculator, parser) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}