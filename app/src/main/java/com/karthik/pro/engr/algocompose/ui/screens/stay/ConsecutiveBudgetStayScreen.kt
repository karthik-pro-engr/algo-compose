package com.karthik.pro.engr.algocompose.ui.screens.stay

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.ui.components.molecules.HotelCostStatus
import com.karthik.pro.engr.algocompose.ui.screens.energy.InputTextField
import com.karthik.pro.engr.algocompose.ui.viewmodel.stay.BudgetStayEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.stay.BudgetStayViewModel

@Composable
fun BudgetStayScreen(
    modifier: Modifier = Modifier,
    vm: BudgetStayViewModel,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }
    var budget by rememberSaveable { mutableStateOf("") }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }

    val uiState by vm.uiState.collectAsState()
    println("UiState-> $uiState")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState.showBudgetInput) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                OutlinedTextField(
                    value = budget,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            budget = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.label_budget)) },
                    placeholder = { Text(stringResource(R.string.placeholder_stay_input)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.budgetErrorMessage.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        vm.onEvent(BudgetStayEvent.AddBudget(budget))
                        budget = ""
                    },
                    enabled = enableAddButton
                ) {
                    Text(stringResource(R.string.button_add_budget))
                }
            }
        } else {
            Text(
                "The Budget is ${uiState.budget}"
            )

        }
        if (uiState.showCostInput) {
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
                    label = { Text(stringResource(R.string.label_stay)) },
                    placeholder = { Text(stringResource(R.string.placeholder_stay_input)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.errorMessage.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        vm.onEvent(BudgetStayEvent.AddHotelPrice(input))
                        input = ""
                    },
                    enabled = enableAddButton
                ) {
                    Text(stringResource(R.string.button_add_cost))
                }
            }
        }

        HotelCostStatus(
            errorMessage = uiState.errorMessage, inputMessage = when {
                uiState.list.isNotEmpty() -> {
                    "[ ${uiState.list.joinToString(", ")} ]"
                }
                uiState.showCostInput -> stringResource(
                    R.string.text_no_hotel_cost_added
                )
                else -> ""
            }
        )

        if (uiState.list.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                enableAddButton = false
                vm.onEvent(BudgetStayEvent.ComputeBudgetStay)
            }, enabled = enableAddButton) {
                Text(text = stringResource(R.string.button_find_longest_stretch))
            }
            uiState.result?.let {
                Text(
                    "The Longest Stretch Hotel Starts from" +
                            " ${it.startIndex + 1} to ${it.endIndex + 1} and total nights are ${it.maxNights}"
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = {
            enableAddButton = true
            vm.onEvent(BudgetStayEvent.Reset)
        }) {
            Text(stringResource(R.string.button_reset))
        }

    }
}