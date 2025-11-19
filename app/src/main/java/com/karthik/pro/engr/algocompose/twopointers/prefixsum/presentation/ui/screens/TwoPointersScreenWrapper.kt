package com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens

/***
 * In a town, each house either produces electricity (producer house) or consumes electricity (consumer house).
 * You want to find the longest continuous stretch of houses where the total electricity balances out (no surplus, no deficit).
 */
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.app.presentation.ui.root.AppRootScreen
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.contract.TwoPointersContract
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.model.TwoPointersConfig
import com.karthik.pro.engr.algocompose.ui.components.atoms.StatusText
import com.karthik.pro.engr.algocompose.ui.components.molecules.ScreenHeader
import com.karthik.pro.engr.devtools.AllVariantsPreview

@Composable
fun <T, VM> TwoPointersScreenWrapper(
    modifier: Modifier = Modifier,
    screenConfig: TwoPointersConfig<T>,
    viewModel: VM,
    parser: ((T) -> Int)? = null,
    onBack: () -> Unit
) where VM : ViewModel, VM : TwoPointersContract<T> {
    BackHandler {
        onBack()
    }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }
    val inputList = viewModel.inputList
    with(screenConfig) {
        AppRootScreen(modifier = modifier) { hideAndClear ->
            Column(
                modifier = Modifier
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
                    InputTextField(
                        Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        input,
                        onValueChange = { value -> if (inputTypeValidator(value)) input = value },
                        screenConfig = this@with,
                        twoPointersViewmodel = viewModel,
                        keyboardActionsProvider = KeyboardActions(onDone = {
                            inputValueValidateAndAdd(input)
                            input = ""
                        }),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            inputValueValidateAndAdd(input)
                            input = ""
                        },
                        enabled = enableAddButton
                    ) {
                        Text(stringResource(inputButtonRes))
                    }
                }

                StatusText(
                    errorMessage = viewModel.errorMessage,
                    inputMessage = when {
                        inputList.isNotEmpty() -> {
                            "[ ${inputList.joinToString(", ")} ]"
                        }

                        else -> stringResource(
                            noItemsInfoRes
                        )
                    }
                )

                if (inputList.size > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        enableAddButton = false
                        hideAndClear()
                        viewModel.compute(parser)
                    }, enabled = enableAddButton) {
                        Text(text = stringResource(computeButtonRes))
                    }
                    viewModel.stretchResult?.let { result ->
                        Text(
                            formatResultLine(result)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
                Button(onClick = {
                    enableAddButton = true
                    hideAndClear()
                    viewModel.reset()
                }) {
                    Text(stringResource(R.string.button_reset))
                }
            }
        }
    }
}


@Composable
fun <T, VM> InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    screenConfig: TwoPointersConfig<T>,
    twoPointersViewmodel: VM,
    keyboardActionsProvider: KeyboardActions
) where VM : ViewModel, VM : TwoPointersContract<T> {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(screenConfig.inputLabelRes)) },
        placeholder = { Text(stringResource(screenConfig.inputPlaceholderRes)) },
        keyboardActions = keyboardActionsProvider,
        keyboardOptions = screenConfig.keyboardOptionsProvider,
        isError = twoPointersViewmodel.errorMessage.isNotEmpty(),
        modifier = modifier
    )
}

@AllVariantsPreview
@Composable
private fun BalancedEnergyScreenPreview() {
//    TwoPointersScreenWrapper(onBack = {})
}