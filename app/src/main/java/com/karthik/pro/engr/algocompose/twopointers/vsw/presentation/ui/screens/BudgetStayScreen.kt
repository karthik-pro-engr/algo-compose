package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.domain.vsw.ConsecutiveStretchCalculator
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswEvent
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswStrings
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.VswScreenWrapper
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel.VswViewModel
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel.VswViewModelFactory

@Composable
fun BudgetStayScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val vswViewModelFactory = VswViewModelFactory(ConsecutiveStretchCalculator::computeResult)
    val vswViewModel: VswViewModel =
        viewModel(key = "BudgetStayVswViewModel", factory = vswViewModelFactory)

    val vswStrings = VswStrings(
        titleRes = R.string.vsw_budget_title,
        bodyRes = R.string.vsw_budget_body,
        capacityLabelRes = R.string.vsw_budget_capacity_label,
        capacityPlaceholderRes = R.string.vsw_budget_capacity_placeholder,
        capacityButtonRes = R.string.vsw_budget_button_add,
        itemLabelRes = R.string.vsw_budget_label_input,
        itemPlaceholderRes = R.string.vsw_budget_placeholder_input,
        itemButtonRes = R.string.vsw_budget_button_add_hotel_cost,
        noItemsInfoRes = R.string.vsw_budget_no_items_info,
        computeButtonRes = R.string.vsw_budget_button_compute,
        unitPluralRes = R.plurals.vsw_budget_nights,
        capacityAddedTextRes = R.string.vsw_budget_capacity_added,
        resultTextRes = R.string.vsw_budget_result,
    )

    VswScreenWrapper(
        modifier = modifier,
        vm = vswViewModel,
        vswStrings = vswStrings,
        onBack = {
            onBack()
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            vswViewModel.onEvent(VswEvent.Reset)
        }
    }
}