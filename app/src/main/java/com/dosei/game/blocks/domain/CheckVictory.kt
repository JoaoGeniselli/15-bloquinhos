package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.TileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CheckVictory(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    suspend operator fun invoke(tiles: List<TileData>): Boolean = withContext(coroutineContext) {
        val numbers = tiles.filterIsInstance<TileData.Number>().map { it.value }
        val areNumbersOrdered = numbers.withIndex().all { (index, value) -> index == value.dec() }
        val isLastSlotBlank = tiles.last() == TileData.Blank
        areNumbersOrdered && isLastSlotBlank
    }
}
