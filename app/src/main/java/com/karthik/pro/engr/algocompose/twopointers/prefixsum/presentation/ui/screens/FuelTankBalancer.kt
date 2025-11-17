package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.domain.PrefixSumWithMonotonic
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.model.TwoPointersConfig
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel.TwoPointersViewModelFactory
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel.TwoPointersViewmodel
import com.karthik.pro.engr.algocompose.util.longValidator
import com.karthik.pro.engr.algocompose.util.textKeyboardOption

@Composable
fun FuelTankBalancerScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    val twoPointersViewModelFactory =
        TwoPointersViewModelFactory(PrefixSumWithMonotonic::findLongestFuelStretch)
    val twoPointersViewModel: TwoPointersViewmodel<Long> =
        viewModel(key = "Fuel Tank Balancer", factory = twoPointersViewModelFactory)

    val twoPointersConfig = TwoPointersConfig<Long>(
        R.string.tp_fuel_title,
        R.string.tp_fuel_body,
        R.string.tp_fuel_label_input,
        R.string.tp_fuel_placeholder_input,
        R.string.tp_fuel_button_add,
        R.string.tp_fuel_no_items_info,
        R.string.tp_fuel_button_compute,
        keyboardOptionsProvider = textKeyboardOption,
        inputTypeValidator = longValidator,
        inputValueValidateAndAdd = { type ->
            val trimmed = type.trim()
            when {
                trimmed.isEmpty() -> twoPointersViewModel.setErrorMessage("Input cannot be empty")
                else -> {
                    twoPointersViewModel.addInput(trimmed.toLong())
                    twoPointersViewModel.setErrorMessage("")
                }
            }
        },
        formatResultLine = { result ->
            "The Longest Stretch Fuel Running Balance Starts from" +
                    " ${result.startIndex} to ${result.endIndex}, so length is ${result.length}"
        }
    )



    TwoPointersScreenWrapper(
        modifier = modifier,
        screenConfig = twoPointersConfig,
        viewModel = twoPointersViewModel,
        onBack = onBack
    )

    DisposableEffect(Unit) {
        onDispose {
            twoPointersViewModel.reset()
        }
    }


}
