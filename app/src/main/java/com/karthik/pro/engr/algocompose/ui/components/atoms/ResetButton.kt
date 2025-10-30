package com.karthik.pro.engr.algocompose.ui.components.atoms

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.karthik.pro.engr.algocompose.R

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    @StringRes buttonRes: Int = R.string.button_reset,
    onButtonClick: () -> Unit,
) {
    Button(onClick = onButtonClick, modifier = modifier) {
        Text(stringResource(buttonRes))
    }
}