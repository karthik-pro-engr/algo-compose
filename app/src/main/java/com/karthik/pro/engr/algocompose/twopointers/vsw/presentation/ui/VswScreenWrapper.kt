package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VarSlidingWindowEvent
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswStrings
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules.ComputeAndResultSection
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules.InputWithButtonRes
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel.VarSlidingWindowViewModel
import com.karthik.pro.engr.algocompose.ui.components.atoms.ResetButton

@Composable
fun VswScreenWrapper(
    modifier: Modifier = Modifier,
    vm: VarSlidingWindowViewModel,
    vswStrings: VswStrings,
    onBack: () -> Unit,
) {
    BackHandler {
        onBack()
    }
    var capacityInput by rememberSaveable { mutableStateOf("") }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }

    val uiState by vm.uiState.collectAsState()
    val scrollState = rememberSaveable(saver = ScrollState.Saver) { ScrollState(0) }

    with(vswStrings) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            ScreenHeader(
                title = titleRes,
                body = bodyRes
            )

            if (uiState.showRangeInput) {
                InputWithButtonRes(
                    value = capacityInput,
                    labelRes = capacityLabelRes,
                    placeholderRes = capacityPlaceholderRes,
                    buttonRes = capacityButtonRes,
                    enabled = enableAddButton,
                    isError = uiState.rangeErrorMessage.isNotEmpty(),
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            capacityInput = newValue
                        }
                    },
                    onButtonClick = {
                        vm.onEvent(VarSlidingWindowEvent.AddRange(capacityInput))
                        capacityInput = ""
                    }
                )
            } else {
                Text(
                    stringResource(capacityAddedTextRes, uiState.rangeOrMaxCapacity)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.showArrayItemInput) {
                InputWithButtonRes(
                    value = input,
                    labelRes = itemLabelRes,
                    placeholderRes = itemPlaceholderRes,
                    buttonRes = itemButtonRes,
                    enabled = enableAddButton,
                    isError = uiState.rangeErrorMessage.isNotEmpty(),
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            input = newValue
                        }
                    },
                    onButtonClick = {
                        vm.onEvent(VarSlidingWindowEvent.AddInputForArray(input))
                        input = ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            StatusText(
                errorMessage = uiState.errorMessage, inputMessage = when {
                    uiState.list.isNotEmpty() -> {
                        "[ ${uiState.list.joinToString(", ")} ]"
                    }

                    uiState.showArrayItemInput -> stringResource(
                        noItemsInfoRes
                    )

                    else -> ""
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.list.isNotEmpty()) {
                ComputeAndResultSection(
                    buttonRes = computeButtonRes,
                    unitPluralRes = unitPluralRes,
                    resultRes = resultTextRes,
                    enabled = enableAddButton,
                    result = uiState.result,
                    onButtonClicked = {
                        enableAddButton = false
                        vm.onEvent(VarSlidingWindowEvent.ComputeVarSlidingWindow)
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            ResetButton(
                buttonRes = R.string.button_reset,
                onButtonClick = {
                    enableAddButton = true
                    vm.onEvent(VarSlidingWindowEvent.Reset)
                }
            )

        }
    }
}