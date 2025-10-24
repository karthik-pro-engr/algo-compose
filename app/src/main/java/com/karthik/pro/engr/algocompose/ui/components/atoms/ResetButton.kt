package com.karthik.pro.engr.algocompose.ui.components.atoms

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    @StringRes buttonRes: Int,
    onButtonClick: () -> Unit,
) {
    Button(onClick = onButtonClick) {
        Text(stringResource(buttonRes))
    }
}