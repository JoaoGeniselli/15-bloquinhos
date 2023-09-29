package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.data.TileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MoveTiles(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {


    suspend operator fun invoke(
        tiles: List<TileData>,
        direction: Direction
    ): List<TileData> = withContext(coroutineContext) {
        val blankPosition = tiles.indexOf(TileData.Blank)
        val tileToMove = tiles.firstOrNull { it.availableDirection == direction }

        if (tileToMove != null) {
            val tileToMoveIndex = tiles.indexOf(tileToMove)
            val updated = tiles.toMutableList().apply {
                removeAt(blankPosition)
                add(blankPosition, tileToMove)

                removeAt(tileToMoveIndex)
                add(tileToMoveIndex, TileData.Blank)
            }
            updated
        } else {
            tiles
        }
    }
}
