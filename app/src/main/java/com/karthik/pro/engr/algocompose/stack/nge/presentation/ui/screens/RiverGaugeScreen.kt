package com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.app.presentation.ui.root.AppRootScreen
import com.karthik.pro.engr.algocompose.domain.stack.MonotonicStackProcessor
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeScreenConfig
import com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.NgeScreenWrapper
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModel
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModelFactory
import com.karthik.pro.engr.algocompose.util.doubleValidator
import com.karthik.pro.engr.algocompose.util.indexToTime
import com.karthik.pro.engr.algocompose.util.numberKeyboardOption

@Composable
fun RiverGaugeScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {

    val ngeViewModelFactory =
        NgeViewModelFactory(
            MonotonicStackProcessor::computePreviousGreaterElement,
            parser = { s -> s.toDoubleOrNull() })

    val ngeViewModel: NgeViewModel<Double> =
        viewModel(key = "RiverGaugeScreen", factory = ngeViewModelFactory)

    val ngeScreenConfig = NgeScreenConfig<Double>(
        titleRes = R.string.nge_river_gauge_title,
        bodyRes = R.string.nge_river_gauge_body,
        inputLabelRes = R.string.nge_river_gauge_label_input,
        inputPlaceholderRes = R.string.nge_river_gauge_placeholder_input,
        inputButtonRes = R.string.nge_river_gauge_button_add_water_level,
        noItemInfosRes = R.string.nge_river_gauge_no_items_info,
        computeButtonRes = R.string.nge_river_gauge_button_compute,
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
        NgeScreenWrapper(
            ngeViewModel = ngeViewModel,
            ngeScreenConfig = ngeScreenConfig,
            hideKeyboard = hideKeyboard,
            onBack = onBack,
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            ngeViewModel.onEvent(NgeEvent.Reset)
        }
    }
}