package com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.app.presentation.ui.root.AppRootScreen
import com.karthik.pro.engr.algocompose.domain.stack.NextGreaterElementCalculator
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeScreenConfig
import com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.NgeScreenWrapper
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModel
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModelFactory


@Composable
fun BoxNestingScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {

    val ngeViewModelFactory =
        NgeViewModelFactory(NextGreaterElementCalculator::computeNextGreaterElement)

    val ngeViewModel: NgeViewModel =
        viewModel(key = "BoxNestingScreen", factory = ngeViewModelFactory)

    val ngeScreenConfig = NgeScreenConfig(
        titleRes = R.string.nge_box_nesting_title,
        bodyRes = R.string.nge_box_nesting_body,
        inputLabelRes = R.string.nge_box_nesting_label_input,
        inputPlaceholderRes = R.string.nge_box_nesting_placeholder_input,
        inputButtonRes = R.string.nge_box_nesting_button_add_size,
        noItemInfosRes = R.string.nge_box_nesting_no_items_info,
        computeButtonRes = R.string.nge_box_nesting_button_compute,
        unitSuffix = "cc",
        formatResultLine = { resultFormat ->
            with(resultFormat) {
                "B$actualIndex — ${
                    actualValue.toString().padStart(maxOfDigits, ' ')
                } $unitSuffix -> Next: ${if (nextGreaterIndex == -1) " None" else "B$nextGreaterIndex ($nextGreaterValue $unitSuffix)"}"
            }
        }
    )

    AppRootScreen(modifier = modifier) { hideKeyboard->
        NgeScreenWrapper(
            ngeViewModel = ngeViewModel,
            ngeScreenConfig = ngeScreenConfig,
            hideKeyboard = hideKeyboard,
            onBack = onBack
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            ngeViewModel.onEvent(NgeEvent.Reset)
        }
    }
}