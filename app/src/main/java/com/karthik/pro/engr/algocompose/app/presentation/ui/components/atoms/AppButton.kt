package com.karthik.pro.engr.algocompose.app.presentation.ui.components.atoms

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButton(modifier: Modifier = Modifier, buttonName: String, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(buttonName)
    }
}