package com.karthik.pro.engr.algocompose.stack.nge.presentation.ui


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeResultFormat
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeScreenConfig
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeUiState
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModel
import com.karthik.pro.engr.devtools.AllVariantsPreview

@Composable
fun NgeScreenWrapper(
    modifier: Modifier = Modifier,
    ngeViewModel: NgeViewModel,
    ngeScreenConfig: NgeScreenConfig,
    onBack: () -> Unit,
) {
    BackHandler {
        onBack()
    }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }
    val ngeUiState by ngeViewModel.ngeUiState.collectAsState()
    val boxSizesList = ngeUiState.inputList


    with(ngeScreenConfig) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenHeader(
                title = titleRes,
                body = bodyRes
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    value = input,
                    onValueChange =  { value -> if (value.all { it.isDigit() }) input = value },
                    label = { Text(stringResource(inputLabelRes)) },
                    placeholder = { Text(stringResource(inputPlaceholderRes)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        ngeViewModel.onEvent(NgeEvent.AddItem(input))
                        input=""
                        focusManager.clearFocus()
                    }),
                    isError = ngeUiState.errorMessage.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        ngeViewModel.onEvent(NgeEvent.AddItem(input))
                        input = ""
                    },
                    enabled = enableAddButton
                ) {
                    Text(stringResource(inputButtonRes))
                }
            }

            StatusText(
                errorMessage = ngeUiState.errorMessage,
                inputMessage = when {
                    boxSizesList.isNotEmpty() -> {
                        "[ ${boxSizesList.joinToString(", ") { "$it $unitSuffix" }} ]"
                    }

                    else -> stringResource(
                        noItemInfosRes
                    )
                }
            )

            if (boxSizesList.size > 1) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    enableAddButton = false
                    ngeViewModel.onEvent(NgeEvent.ComputeNge)
                }, enabled = enableAddButton) {
                    Text(text = stringResource(computeButtonRes))
                }

                ngeUiState.ngeResult?.let { result ->
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(modifier= Modifier.weight(1f)) {
                        itemsIndexed(boxSizesList) { idx, value ->
                            val nextIdx = result.resultList[idx]
                            val line = ngeScreenConfig.formatResultLine(
                                NgeResultFormat(
                                    maxOfDigits = boxSizesList.maxOfOrNull { it.toString().length } ?: 0,
                                    sbCapacityEstimate = 0,
                                    actualIndex = idx,
                                    nextGreaterIndex = nextIdx,
                                    actualValue = value,
                                    nextGreaterValue = boxSizesList.getOrNull(nextIdx) ?: 0,
                                    unitSuffix = ngeScreenConfig.unitSuffix
                                )
                            )
                            Text(line, modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                enableAddButton = true
                ngeViewModel.onEvent(NgeEvent.Reset)
            }) {
                Text(stringResource(R.string.button_reset))
            }
        }
    }
}


@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    ngeUiState: NgeUiState,
    ngeViewModel: NgeViewModel
) {

}

@AllVariantsPreview
@Composable
private fun BalancedEnergyScreenPreview() {
    NgeScreenWrapper(
        ngeViewModel = viewModel<NgeViewModel>(),
        ngeScreenConfig = NgeScreenConfig(),
        onBack = {},
    )
}