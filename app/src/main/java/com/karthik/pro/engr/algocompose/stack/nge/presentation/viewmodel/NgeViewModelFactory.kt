package com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.algocompose.domain.stack.NgeResult

class NgeViewModelFactory<T : Comparable<T>>(
    private val ngeCalculator: (List<T>) -> NgeResult<T>?,
    private val parser: (String) -> T?
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NgeViewModel::class.java)) {
            return NgeViewModel(ngeCalculator, parser) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}