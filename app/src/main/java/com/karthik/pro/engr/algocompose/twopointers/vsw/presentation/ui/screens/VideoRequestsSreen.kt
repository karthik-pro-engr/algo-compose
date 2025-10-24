package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswStrings
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.VswScreenWrapper
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel.VarSlidingWindowViewModel

@Composable
fun VideoRequestsScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    val varSlidingWindowViewModel: VarSlidingWindowViewModel = viewModel()

    val vswStrings = VswStrings(
        titleRes = R.string.vsw_video_title,
        bodyRes = R.string.vsw_video_body,
        capacityLabelRes = R.string.vsw_video_capacity_label_add,
        capacityPlaceholderRes = R.string.vsw_video_capacity_placeholder,
        capacityButtonRes = R.string.vsw_video_capacity_button_add,
        itemLabelRes = R.string.vsw_video_label_input,
        itemPlaceholderRes = R.string.vsw_video_placeholder_input,
        itemButtonRes = R.string.vsw_video_button_add_input,
        noItemsInfoRes = R.string.vsw_video_no_input_infos,
        computeButtonRes = R.string.vsw_video_button_compute,
        unitPluralRes = R.plurals.vsw_video_seconds,
        capacityAddedTextRes = R.string.vsw_video_capacity_added,
        resultTextRes = R.string.vsw_video_result,
    )

    VswScreenWrapper(
        modifier = modifier,
        vm = varSlidingWindowViewModel,
        vswStrings = vswStrings,
        onBack = onBack
    )
}