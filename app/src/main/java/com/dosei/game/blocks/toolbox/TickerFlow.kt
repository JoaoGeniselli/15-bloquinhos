package com.dosei.game.blocks.toolbox

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

const val OneSecond = 1000L

fun tickerFlow(delay: Long = OneSecond, initialTime: Long = 0L) = flow {
    delay(initialTime)
    while (true) {
        emit(Unit)
        delay(delay)
    }
}