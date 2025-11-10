package com.karthik.pro.engr.algocompose.stack.nge.presentation.model

import androidx.annotation.StringRes

data class NgeScreenConfig<T>(
    @param:StringRes val titleRes: Int = 0,
    @param:StringRes val bodyRes: Int = 0,
    @param:StringRes val inputLabelRes: Int = 0,
    @param:StringRes val inputPlaceholderRes: Int = 0,
    @param:StringRes val inputButtonRes: Int = 0,
    @param:StringRes val noItemInfosRes: Int = 0,
    @param:StringRes val computeButtonRes: Int = 0,
    val unitSuffix: String = "",
    var formatResultLine: (resultFormat: NgeResultFormat<T>) -> String = { value -> "" }
)
