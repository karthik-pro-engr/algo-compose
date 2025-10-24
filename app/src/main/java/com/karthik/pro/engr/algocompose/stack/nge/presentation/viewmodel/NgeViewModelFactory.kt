package com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.algocompose.domain.stack.NgeResult

class NgeViewModelFactory(private val ngeCalculator: (List<Int>) -> NgeResult?) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NgeViewModel::class.java)) {
            return NgeViewModel(ngeCalculator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}