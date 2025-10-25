package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswEvent
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswStrings
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules.ComputeAndResultSection
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules.InputWithButtonRes
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel.VswViewModel
import com.karthik.pro.engr.algocompose.ui.components.atoms.ResetButton
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader

@Composable
fun VswScreenWrapper(
    modifier: Modifier = Modifier,
    vm: VswViewModel,
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

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            if (uiState.showRangeInput) {
                InputWithButtonRes(
                    value = capacityInput,
                    labelRes = capacityLabelRes,
                    placeholderRes = capacityPlaceholderRes,
                    buttonRes = capacityButtonRes,
                    enabled = enableAddButton,
                    isError = uiState.capacityErrorMessage.isNotEmpty(),
                    keyboardActions = KeyboardActions(onDone = {
                        vm.onEvent(VswEvent.AddRange(capacityInput))
                        capacityInput = ""
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }),
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            capacityInput = newValue
                        }
                    },
                    onButtonClick = {
                        vm.onEvent(VswEvent.AddRange(capacityInput))
                        capacityInput = ""
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    },
                )
            } else {
                Text(
                    stringResource(capacityAddedTextRes, uiState.capacity)
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
                    isError = uiState.errorMessage.isNotEmpty(),
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            input = newValue
                        }
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        vm.onEvent(VswEvent.AddInputForArray(input))
                        input = ""
                    }),
                    onButtonClick = {
                        vm.onEvent(VswEvent.AddInputForArray(input))
                        input = ""
                    },
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
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        vm.onEvent(VswEvent.ComputeVsw)
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            ResetButton(
                buttonRes = R.string.button_reset,
                onButtonClick = {
                    enableAddButton = true
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    vm.onEvent(VswEvent.Reset)
                }
            )

        }
    }
}