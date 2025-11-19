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
import com.karthik.pro.engr.algocompose.util.stringValidator
import com.karthik.pro.engr.algocompose.util.textKeyboardOption

@Composable
fun BalancedEnergyScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    val prefixSumViewModelFactory =
        PrefixSumViewModelFactory<String> { houseTypes, parser ->
            PrefixsumCalculator.findLongestStretch(
                houseTypes,
                parser
            )
        }
    val prefixSumScreenViewModel: PrefixSumViewModel<String> =
        viewModel(key = "Balanced Energy", factory = prefixSumViewModelFactory)

    val twoPointersConfig = TwoPointersConfig<String>(
        R.string.tp_energy_title,
        R.string.tp_energy_screen_body,
        R.string.tp_energy_label_input,
        R.string.tp_energy_placeholder_input,
        R.string.tp_energy_button_add,
        R.string.tp_energy_no_items_info,
        R.string.tp_energy_button_compute,
        keyboardOptionsProvider = textKeyboardOption,
        inputTypeValidator = stringValidator,
        inputValueValidateAndAdd = { type ->
            val trimmed = type.trim()
            when {
                trimmed.isEmpty() -> prefixSumScreenViewModel.setErrorMessage("Input cannot be empty")
                trimmed.lowercase() !in listOf(
                    "p",
                    "c"
                ) -> prefixSumScreenViewModel.setErrorMessage(
                    "Input must be either 'p' or 'c'"
                )

                else -> {
                    prefixSumScreenViewModel.addInput(trimmed)
                    prefixSumScreenViewModel.setErrorMessage("")
                }
            }
        },
        formatResultLine = { result ->
            "The Longest Stretch Houses Starts from" +
                    " ${result.startIndex + 1} to ${result.endIndex + 1}"
        }
    )



    TwoPointersScreenWrapper(
        modifier = modifier,
        screenConfig = twoPointersConfig,
        viewModel = prefixSumScreenViewModel,
        parser = { element ->
            when (element.lowercase()) {
                "p" -> 1

                "c" -> -1

                else -> 0
            }
        },
        onBack = onBack
    )

    DisposableEffect(Unit) {
        onDispose {
            prefixSumScreenViewModel.reset()
        }
    }


}