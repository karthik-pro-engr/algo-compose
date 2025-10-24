package com.karthik.pro.engr.algocompose.stack.nge.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.domain.stack.NextGreaterElementCalculator
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.screens.NgeScreenWrapper
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModel
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModelFactory


@Composable
fun BoxNestingScreen(modifier: Modifier, onBack: () -> Unit) {

    val ngeViewModelFactory =
        NgeViewModelFactory(NextGreaterElementCalculator::computeNextGreaterElement)
    val ngeViewModel: NgeViewModel = viewModel(key = "BoxNestingScreen", factory = ngeViewModelFactory)

    NgeScreenWrapper(
        modifier = modifier,
        ngeViewModel = ngeViewModel,
        onBack = onBack
    )

    DisposableEffect(Unit) {
        onDispose {
            ngeViewModel.onEvent(NgeEvent.Reset)
        }
    }
}