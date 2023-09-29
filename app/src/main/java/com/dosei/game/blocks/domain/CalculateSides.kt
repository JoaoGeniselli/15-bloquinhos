package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.DEFAULT_SIZE
import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.data.TileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

private const val INDEX_NONE = -1

class CalculateSides(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    suspend operator fun invoke(
        tiles: List<TileData>,
        boardSize: Int = DEFAULT_SIZE
    ): List<TileData> = withContext(coroutineContext) {
        val validTilesRange = 0 until (boardSize * boardSize)
        val blank = tiles.indexOf(TileData.Blank)

        val top = (blank - boardSize).takeIf { it in validTilesRange } ?: INDEX_NONE
        val down = (blank + boardSize).takeIf { it in validTilesRange } ?: INDEX_NONE
        val left = blank.dec()
            .takeIf { it in validTilesRange && sameLine(boardSize, blank, it) } ?: INDEX_NONE
        val right = blank.inc()
            .takeIf { it in validTilesRange && sameLine(boardSize, blank, it) } ?: INDEX_NONE

        tiles.mapIndexed { index, tile ->
            val availableDirection = when (index) {
                top -> Direction.DOWN
                down -> Direction.UP
                left -> Direction.RIGHT
                right -> Direction.LEFT
                else -> null
            }
            when (tile) {
                is TileData.Number -> tile.copy(availableDirection = availableDirection)
                is TileData.Blank -> tile
            }
        }
    }

    private fun sameLine(boardSize: Int, a: Int, b: Int) = (a / boardSize) == (b / boardSize)
}
