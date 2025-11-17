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
import com.karthik.pro.engr.algocompose.util.intValidator
import com.karthik.pro.engr.algocompose.util.numberKeyboardOption


@Composable
fun BoxNestingScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {

    val monotonicViewModelFactory =
        MonotonicViewModelFactory(
            MonotonicStackProcessor::computeNextGreaterElement,
            parser = { s -> s.toIntOrNull() }
        )

    val monotonicViewModel: MonotonicViewModel<Int> =
        viewModel(key = "BoxNestingScreen", factory = monotonicViewModelFactory)

    val monotonicScreenConfig = MonotonicScreenConfig<Int>(
        titleRes = R.string.monotonic_box_nesting_title,
        bodyRes = R.string.monotonic_box_nesting_body,
        inputLabelRes = R.string.monotonic_box_nesting_label_input,
        inputPlaceholderRes = R.string.monotonic_box_nesting_placeholder_input,
        inputButtonRes = R.string.monotonic_box_nesting_button_add_size,
        noItemInfosRes = R.string.monotonic_box_nesting_no_items_info,
        computeButtonRes = R.string.monotonic_box_nesting_button_compute,
        unitSuffix = "cc",
        inputValidator = intValidator,
        keyboardOptionsProvider = numberKeyboardOption,
        formatResultLine = { resultFormat ->
            with(resultFormat) {
                "B$actualIndex — ${
                    actualValue.toString().padStart(maxOfDigits, ' ')
                } $unitSuffix -> Next: ${if (computedIndex == -1) " None" else "B$computedIndex ($computedValue $unitSuffix)"}"
            }
        }
    )

    AppRootScreen(modifier = modifier, contentScrollable = false) { hideKeyboard ->
        MonotonicScreenWrapper(
            monotonicViewModel = monotonicViewModel,
            monotonicScreenConfig = monotonicScreenConfig,
            hideKeyboard = hideKeyboard,
            onBack = onBack
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            monotonicViewModel.onEvent(MonotonicEvent.Reset)
        }
    }
}