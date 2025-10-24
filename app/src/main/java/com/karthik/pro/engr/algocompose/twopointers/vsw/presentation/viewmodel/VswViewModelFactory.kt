package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.algocompose.domain.vsw.ConsecutiveSubArrayAndSize

class VswViewModelFactory(
    private val vswCalculator: (Int, List<Int>) -> ConsecutiveSubArrayAndSize
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VswViewModel::class.java)) {
            return VswViewModel(vswCalculator) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}