package com.karthik.pro.engr.algocompose.ui.components.molecules

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.karthik.pro.engr.algocompose.R

@Composable
fun HotelCostStatus(
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    inputMessage:String = ""
) {

    if (inputMessage.isNotEmpty()) {
        Text(
            text = inputMessage)
    } else if(errorMessage.isNotEmpty()){
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }

}