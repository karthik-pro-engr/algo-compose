package com.karthik.pro.engr.algocompose.ui.components.molecules

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes body: Int
) {
    Text(
        text = stringResource(title),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(body),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(18.dp))
}