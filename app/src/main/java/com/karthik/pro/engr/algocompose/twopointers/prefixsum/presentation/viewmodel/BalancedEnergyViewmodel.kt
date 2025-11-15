package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.algocompose.domain.energy.StretchResult

class BalancedEnergyViewmodel<T>(val calculator: (List<T>) -> StretchResult) : ViewModel() {
    private val _inputList = mutableStateListOf<T>()
    val inputList: List<T> get() = _inputList

    var stretchResult by mutableStateOf<StretchResult?>(null)
        private set


    private var _errorMessage by mutableStateOf("")

    val errorMessage:String get()  = _errorMessage



    fun addInput(type: T) {
        _inputList.add(type)
    }

    fun setErrorMessage(error: String) {
        _errorMessage = error
    }

    fun calculateBalancedEnergy() {
        stretchResult = calculator(_inputList)
    }

    fun reset() {
        _inputList.clear()
        _errorMessage = ""
    }
}