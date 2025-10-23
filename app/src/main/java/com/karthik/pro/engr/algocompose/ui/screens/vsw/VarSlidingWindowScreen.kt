package com.karthik.pro.engr.algocompose.ui.screens.vsw

import androidx.activity.compose.BackHandler
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader
import com.karthik.pro.engr.algocompose.ui.viewmodel.vsw.VarSlidingWindowEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.vsw.VarSlidingWindowViewModel

@Composable
fun VariableSlidingWindowScreen(
    modifier: Modifier = Modifier,
    vm: VarSlidingWindowViewModel,
    @StringRes title:Int,
    @StringRes body:Int,
    @StringRes lblRangeOrMaxCapacity: Int,
    @StringRes phRangeOrMaxCapacity: Int,
    @StringRes btnRangeOrMaxCapacity: Int,
    @StringRes lblInputForArr: Int,
    @StringRes phInputForArr: Int,
    @StringRes btnInputForArr: Int,
    @StringRes txtInputArrInfo: Int,
    @StringRes btnComputeResult: Int,
    @PluralsRes strPlural: Int,
    @StringRes txtResult: Int,
    @StringRes txtMaxCapacity: Int,
    onBack: () -> Unit,
) {
    BackHandler {
        onBack()
    }
    var rangeOrMaxCapacityInput by rememberSaveable { mutableStateOf("") }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }

    val uiState by vm.uiState.collectAsState()
    val scrollState = rememberSaveable(saver = ScrollState.Saver) { ScrollState(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        ScreenHeader(
            title = title,
            body = body
        )
        if (uiState.showRangeInput) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                OutlinedTextField(
                    value = rangeOrMaxCapacityInput,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            rangeOrMaxCapacityInput = newValue
                        }
                    },
                    label = { Text(stringResource(lblRangeOrMaxCapacity)) },
                    placeholder = { Text(stringResource(phRangeOrMaxCapacity)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.rangeErrorMessage.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        vm.onEvent(VarSlidingWindowEvent.AddRange(rangeOrMaxCapacityInput))
                        rangeOrMaxCapacityInput = ""
                    },
                    enabled = enableAddButton
                ) {
                    Text(stringResource(btnRangeOrMaxCapacity))
                }
            }
        } else {
            Text(
                stringResource(txtMaxCapacity, uiState.rangeOrMaxCapacity)
            )

        }
        if (uiState.showArrayItemInput) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            input = newValue
                        }
                    },
                    label = { Text(stringResource(lblInputForArr)) },
                    placeholder = { Text(stringResource(phInputForArr)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.errorMessage.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        vm.onEvent(VarSlidingWindowEvent.AddInputForArray(input))
                        input = ""
                    },
                    enabled = enableAddButton
                ) {
                    Text(stringResource(btnInputForArr))
                }
            }
        }

        StatusText(
            errorMessage = uiState.errorMessage, inputMessage = when {
                uiState.list.isNotEmpty() -> {
                    "[ ${uiState.list.joinToString(", ")} ]"
                }

                uiState.showArrayItemInput -> stringResource(
                    txtInputArrInfo
                )

                else -> ""
            }
        )

        if (uiState.list.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                enableAddButton = false
                vm.onEvent(VarSlidingWindowEvent.ComputeVarSlidingWindow)
            }, enabled = enableAddButton) {
                Text(text = stringResource(btnComputeResult))
            }
            uiState.result?.let {
                val longestStretch = pluralStringResource(strPlural, it.maxStretch, it.maxStretch)
                Text(
                    stringResource(txtResult, it.startIndex + 1, it.endIndex + 1, longestStretch)
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = {
            enableAddButton = true
            vm.onEvent(VarSlidingWindowEvent.Reset)
        }) {
            Text(stringResource(R.string.button_reset))
        }

    }
}