package com.dosei.game.blocks.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.NumberFormat
import java.util.Locale

const val SECONDS_PER_MINUTE = 60

@Composable
fun formatTime(
    timeInSeconds: Long,
): String {
    val formatter = remember {
        NumberFormat
            .getNumberInstance(Locale.getDefault())
            .apply { minimumIntegerDigits = 2 }
    }

    val minutes = timeInSeconds / SECONDS_PER_MINUTE
    val seconds = timeInSeconds - (minutes * SECONDS_PER_MINUTE)
    return "${formatter.format(minutes)}:${formatter.format(seconds)}"
}