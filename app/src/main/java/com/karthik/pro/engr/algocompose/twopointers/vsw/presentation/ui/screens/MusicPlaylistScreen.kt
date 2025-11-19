package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.domain.vsw.VswCalculation
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.model.TwoPointersConfig
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens.TwoPointersScreenWrapper
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel.TwoPointersViewModelFactory
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.viewmodel.TwoPointersViewmodel
import com.karthik.pro.engr.algocompose.util.textKeyboardOption

@Composable
fun MusicPlaylistScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val twoPointersViewModelFactory =
        TwoPointersViewModelFactory<String>(VswCalculation::findLongestUniqueSegment)
    val twoPointersViewmodel: TwoPointersViewmodel<String> =
        viewModel(key = "Music Playlist", factory = twoPointersViewModelFactory)

    val twoPointersConfig = TwoPointersConfig<String>(
        titleRes = R.string.vsw_music_title,
        bodyRes = R.string.vsw_music_body,
        inputLabelRes = R.string.vsw_music_label_input,
        inputPlaceholderRes = R.string.vsw_music_placeholder_input,
        inputButtonRes = R.string.vsw_music_button_add,
        noItemsInfoRes = R.string.vsw_music_no_items_info,
        computeButtonRes = R.string.vsw_music_button_compute,
        keyboardOptionsProvider = textKeyboardOption,
        inputTypeValidator = { value -> true }, // No restriction to input accepts text and number and whatever it is
        inputValueValidateAndAdd = { value ->
            if (value.isEmpty()) twoPointersViewmodel.setErrorMessage("Input cannot be empty")
            else {
                twoPointersViewmodel.addInput(value)
                twoPointersViewmodel.setErrorMessage("")
            }
        },
        formatResultLine = { result ->
            "Longest unique playlist segment has length ${result.length}, starts from ${result.startIndex} to ${result.endIndex}: ${
                twoPointersViewmodel.inputList.slice(result.startIndex..result.endIndex)
            }"
        }
    )

    TwoPointersScreenWrapper(
        modifier = modifier,
        screenConfig = twoPointersConfig,
        viewModel = twoPointersViewmodel,
        parser = null,
        onBack = onBack
    )
    DisposableEffect(Unit) {
        onDispose {
            twoPointersViewmodel.reset()
        }
    }
}