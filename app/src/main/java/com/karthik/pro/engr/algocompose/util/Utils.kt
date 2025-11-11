package com.karthik.pro.engr.algocompose.util

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val timeFormat: DateTimeFormatter? = DateTimeFormatter.ofPattern("HH:mm")

fun indexToTime(index: Int, start: LocalTime = LocalTime.of(12, 0)): String {
    return start.plusMinutes(index.toLong()).format(timeFormat)
}

val intValidator: (String) -> Boolean = { s -> s.isEmpty() || Regex("^[-+]?[0-9]*\$").matches(s) }

val doubleValidator: (String) -> Boolean = { s ->
    s.isEmpty() || Regex("^[-+]?(\\d+\\.?\\d*|\\.\\d+)?\$").matches(s)
}

val numberKeyboardOption = {
    KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    )
}

