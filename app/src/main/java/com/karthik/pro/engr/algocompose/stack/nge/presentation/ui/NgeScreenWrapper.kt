package com.karthik.pro.engr.algocompose.stack.nge.presentation.ui


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeEvent
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeResultFormat
import com.karthik.pro.engr.algocompose.stack.nge.presentation.model.NgeScreenConfig
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.NgeViewModel
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules.InputWithButtonRes
import com.karthik.pro.engr.algocompose.ui.components.atoms.ResetButton
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader
import com.karthik.pro.engr.devtools.AllVariantsPreview

@Composable
fun <T:Comparable<T>> NgeScreenWrapper(
    modifier: Modifier = Modifier,
    ngeViewModel: NgeViewModel<T>,
    ngeScreenConfig: NgeScreenConfig<T>,
    hideKeyboard: () -> Unit,
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
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            item {
                ScreenHeader(
                    title = titleRes,
                    body = bodyRes
                )
            }

            item {
                InputWithButtonRes(
                    value = input,
                    labelRes = inputLabelRes,
                    placeholderRes = inputPlaceholderRes,
                    buttonRes = inputButtonRes,
                    enabled = enableAddButton,
                    isError = ngeUiState.errorMessage.isNotEmpty(),
                    keyboardActions = KeyboardActions(onDone = {
                        Log.d("Click", "NgeScreenWrapper: keyboard action done")
                        ngeViewModel.onEvent(NgeEvent.AddItem(input))
                        input = ""
                    }),
                    onValueChange = { value -> if (value.all { it.isDigit() }) input = value },
                    onButtonClick = {
                        ngeViewModel.onEvent(NgeEvent.AddItem(input))
                        input = ""
                    },
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {

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
            }

            if (boxSizesList.size > 1) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        enableAddButton = false
                        hideKeyboard()
                        ngeViewModel.onEvent(NgeEvent.ComputeNge)
                    }, enabled = enableAddButton) {
                        Text(text = stringResource(computeButtonRes))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                ngeUiState.ngeResult?.let { result ->
                    itemsIndexed(boxSizesList) { idx, value ->
                        val nextIdx = result.resultList[idx]
                        val line = ngeScreenConfig.formatResultLine(
                            NgeResultFormat(
                                maxOfDigits = boxSizesList.maxOfOrNull { it.toString().length }
                                    ?: 0,
                                sbCapacityEstimate = 0,
                                actualIndex = idx,
                                nextGreaterIndex = nextIdx,
                                actualValue = value,
                                nextGreaterValue = boxSizesList.getOrNull(nextIdx),
                                unitSuffix = ngeScreenConfig.unitSuffix
                            )
                        )
                        Text(line, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))

                ResetButton {
                    enableAddButton = true
                    hideKeyboard()
                    ngeViewModel.onEvent(NgeEvent.Reset)
                }
            }
        }
    }
}


/*
@AllVariantsPreview
@Composable
private fun BalancedEnergyScreenPreview() {
    NgeScreenWrapper(
        ngeViewModel = viewModel<NgeViewModel>(),
        ngeScreenConfig = NgeScreenConfig(),
        hideKeyboard = {},
    ) {}
}*/
