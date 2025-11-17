package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui


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
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicEvent
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicResultFormat
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicScreenConfig
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel.MonotonicViewModel
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules.InputWithButtonRes
import com.karthik.pro.engr.algocompose.ui.components.atoms.ResetButton
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader

@Composable
fun <T : Comparable<T>> MonotonicScreenWrapper(
    modifier: Modifier = Modifier,
    monotonicViewModel: MonotonicViewModel<T>,
    monotonicScreenConfig: MonotonicScreenConfig<T>,
    hideKeyboard: () -> Unit,
    onBack: () -> Unit,
) {
    BackHandler {
        onBack()
    }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }
    val monotonicUiState by monotonicViewModel.monotonicUiState.collectAsState()
    val boxSizesList = monotonicUiState.inputList


    with(monotonicScreenConfig) {
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
                    isError = monotonicUiState.errorMessage.isNotEmpty(),
                    keyboardActions = KeyboardActions(onDone = {
                        Log.d("Click", "MonotonicScreenWrapper: keyboard action done")
                        monotonicViewModel.onEvent(MonotonicEvent.AddItem(input))
                        input = ""
                    }),
                    keyboardOptions = keyboardOptionsProvider,
                    onValueChange = { value -> if (inputValidator(value)) input = value },
                    onButtonClick = {
                        monotonicViewModel.onEvent(MonotonicEvent.AddItem(input))
                        input = ""
                    },
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {

                StatusText(
                    errorMessage = monotonicUiState.errorMessage,
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
                        monotonicViewModel.onEvent(MonotonicEvent.ComputeMonotonic)
                    }, enabled = enableAddButton) {
                        Text(text = stringResource(computeButtonRes))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                monotonicUiState.monotonicResult?.let { result ->
                    itemsIndexed(boxSizesList) { idx, value ->
                        val nextIdx = result.resultList[idx]
                        val line = monotonicScreenConfig.formatResultLine(
                            MonotonicResultFormat(
                                maxOfDigits = boxSizesList.maxOfOrNull { it.toString().length }
                                    ?: 0,
                                sbCapacityEstimate = 0,
                                actualIndex = idx,
                                computedIndex = nextIdx,
                                actualValue = value,
                                computedValue = boxSizesList.getOrNull(nextIdx),
                                unitSuffix = monotonicScreenConfig.unitSuffix
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
                    monotonicViewModel.onEvent(MonotonicEvent.Reset)
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
