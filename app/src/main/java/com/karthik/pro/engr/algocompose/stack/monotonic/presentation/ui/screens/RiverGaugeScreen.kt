package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.app.presentation.ui.root.AppRootScreen
import com.karthik.pro.engr.algocompose.domain.stack.MonotonicStackProcessor
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicEvent
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicScreenConfig
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.MonotonicScreenWrapper
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel.MonotonicViewModel
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel.MonotonicViewModelFactory
import com.karthik.pro.engr.algocompose.util.doubleValidator
import com.karthik.pro.engr.algocompose.util.indexToTime
import com.karthik.pro.engr.algocompose.util.numberKeyboardOption

@Composable
fun RiverGaugeScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {

    val monotonicViewModelFactory =
        MonotonicViewModelFactory(
            MonotonicStackProcessor::computePreviousGreaterElement,
            parser = { s -> s.toDoubleOrNull() })

    val monotonicViewModel: MonotonicViewModel<Double> =
        viewModel(key = "RiverGaugeScreen", factory = monotonicViewModelFactory)

    val monotonicScreenConfig = MonotonicScreenConfig<Double>(
        titleRes = R.string.monotonic_river_gauge_title,
        bodyRes = R.string.monotonic_river_gauge_body,
        inputLabelRes = R.string.monotonic_river_gauge_label_input,
        inputPlaceholderRes = R.string.monotonic_river_gauge_placeholder_input,
        inputButtonRes = R.string.monotonic_river_gauge_button_add_water_level,
        noItemInfosRes = R.string.monotonic_river_gauge_no_items_info,
        computeButtonRes = R.string.monotonic_river_gauge_button_compute,
        unitSuffix = "m",
        inputValidator = doubleValidator,
        keyboardOptionsProvider = numberKeyboardOption,
        formatResultLine = { resultFormat ->
            with(resultFormat) {
                "${indexToTime(actualIndex)} —> current level: $actualValue $unitSuffix - previous peak: ${if (computedIndex == -1) "None " else "$computedValue $unitSuffix"}"
            }
        }
    )

    AppRootScreen(modifier = modifier, contentScrollable = false) { hideKeyboard ->
        MonotonicScreenWrapper(
            monotonicViewModel = monotonicViewModel,
            monotonicScreenConfig = monotonicScreenConfig,
            hideKeyboard = hideKeyboard,
            onBack = onBack,
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            monotonicViewModel.onEvent(MonotonicEvent.Reset)
        }
    }
}