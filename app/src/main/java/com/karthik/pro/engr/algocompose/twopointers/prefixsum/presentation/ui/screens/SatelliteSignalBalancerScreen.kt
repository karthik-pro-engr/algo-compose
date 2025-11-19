package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.domain.energy.PrefixsumCalculator
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.model.TwoPointersConfig
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel.PrefixSumViewModel
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel.PrefixSumViewModelFactory
import com.karthik.pro.engr.algocompose.util.intValidator
import com.karthik.pro.engr.algocompose.util.numberKeyboardOption

@Composable
fun SatelliteSignalBalancerScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    val prefixSumViewModelFactory = PrefixSumViewModelFactory<Int> { inputList, parser ->
        PrefixsumCalculator.findLongestStretch(inputList, parser)
    }

    val prefixSumViewModel: PrefixSumViewModel<Int> =
        viewModel(key = "SatelliteSignalBalancer", factory = prefixSumViewModelFactory)

    val twoPointersConfig = TwoPointersConfig<Int>(
        titleRes = R.string.tp_satellite_title,
        bodyRes = R.string.tp_satellite_body,
        inputLabelRes = R.string.tp_satellite_label_input,
        inputPlaceholderRes = R.string.tp_satellite_placeholder_input,
        inputButtonRes = R.string.tp_satellite_button_add,
        noItemsInfoRes = R.string.tp_satellite_no_items_info,
        computeButtonRes = R.string.tp_satellite_button_compute,
        keyboardOptionsProvider = numberKeyboardOption,
        inputTypeValidator = intValidator,
        inputValueValidateAndAdd = { value ->
            val trimmed = value.trim()
            when {
                trimmed.isEmpty() -> prefixSumViewModel.setErrorMessage("Input cannot be empty")
                else -> {
                    prefixSumViewModel.addInput(trimmed.toInt())
                    prefixSumViewModel.setErrorMessage("")
                }
            }
        },
        formatResultLine = { result ->
            "The Longest contiguous Balanced Signals Starts from" +
                    " ${result.startIndex + 1} to ${result.endIndex + 1}, so length is ${result.length}"
        }
    )

    TwoPointersScreenWrapper(
        modifier = modifier,
        screenConfig = twoPointersConfig,
        viewModel = prefixSumViewModel,
        parser = { value -> value },
        onBack = onBack
    )


    DisposableEffect(Unit) {
        onDispose {
            prefixSumViewModel.reset()
        }
    }
}
