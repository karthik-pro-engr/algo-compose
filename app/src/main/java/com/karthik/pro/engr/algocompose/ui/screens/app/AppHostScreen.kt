package com.karthik.pro.engr.algocompose.ui.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.ui.screens.energy.BalancedEnergyScreen
import com.karthik.pro.engr.algocompose.ui.screens.stay.BudgetStayScreen
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppViewmodel
import com.karthik.pro.engr.algocompose.ui.viewmodel.stay.BudgetStayViewModel

@Composable
fun AppHostScreen(
    modifier: Modifier = Modifier,
    vm: AppViewmodel = viewModel()
) {
    val collectAsState by vm.appUiState.collectAsState()

    if (collectAsState.selectedScreenId == null) {
        Column(modifier = modifier) {
            LazyColumn(
            ) {
                items(items = vm.appUiState.value.list) {
                    AppButton(
                        buttonName = it.buttonName,
                        onClick = { vm.onEvent(AppEvent.SelectedScreen(it.id)) })
                }
            }
        }
    } else {
        when (collectAsState.selectedScreenId) {
            0 -> BalancedEnergyScreen(
                modifier = modifier,
                onBack = { vm.onEvent(AppEvent.OnBack) })

            1 -> {
                val budgetStayViewModel: BudgetStayViewModel = viewModel()
                BudgetStayScreen(
                    modifier = modifier,
                    vm = budgetStayViewModel,
                    onBack = { vm.onEvent(AppEvent.OnBack) })
            }

            else -> vm.onEvent(AppEvent.OnBack)


        }
    }

}