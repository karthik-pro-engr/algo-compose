package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.algocompose.domain.energy.StretchResult

class TwoPointersViewModelFactory<T>(private val calculator: (List<String>) -> StretchResult) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BalancedEnergyViewmodel::class.java)) {
            return BalancedEnergyViewmodel(calculator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}