package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.model

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.karthik.pro.engr.algocompose.domain.energy.StretchResult

data class TwoPointersConfig<T>(
    @param:StringRes val titleRes: Int,
    @param:StringRes val bodyRes: Int,
    @param:StringRes val inputLabelRes: Int,
    @param:StringRes val inputPlaceholderRes: Int,
    @param:StringRes val inputButtonRes: Int,
    @param:StringRes val noItemsInfoRes: Int,
    @param:StringRes val computeButtonRes: Int,
    val keyboardOptionsProvider: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    val inputTypeValidator: (String) -> Boolean = { true },
    val inputValueValidateAndAdd: (String) -> Unit = {},
    val formatResultLine: (StretchResult) -> String = { value -> "" }
)