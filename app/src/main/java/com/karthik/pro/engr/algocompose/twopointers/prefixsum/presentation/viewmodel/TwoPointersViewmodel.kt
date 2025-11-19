package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.algocompose.domain.energy.StretchResult
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.contract.TwoPointersContract

class TwoPointersViewmodel<T>(val calculator: (List<T>) -> StretchResult) : ViewModel(), TwoPointersContract<T> {
    private val _inputList = mutableStateListOf<T>()
    override val inputList: List<T> get() = _inputList

    override var stretchResult by mutableStateOf<StretchResult?>(null)
        private set


    private var _errorMessage by mutableStateOf("")

    override val errorMessage: String get() = _errorMessage


    override fun addInput(value: T) {
        _inputList.add(value)
    }

    override fun setErrorMessage(error: String) {
        _errorMessage = error
    }

    override fun compute(parser: ((T) -> Int)?) {
        stretchResult = calculator(_inputList)
    }

    fun calculateBalancedEnergy() {
        stretchResult = calculator(_inputList)
    }

    override fun reset() {
        _inputList.clear()
        _errorMessage = ""
    }
}