package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.DEFAULT_SIZE
import com.dosei.game.blocks.data.GameState
import com.dosei.game.blocks.data.TileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CreateGame(
    private val calculateSides: CalculateSides,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {


    suspend operator fun invoke(boardSize: Int = DEFAULT_SIZE) = withContext(coroutineContext) {
        val maxTiles = boardSize * boardSize
        val tileNumbers = (1..maxTiles.dec()).shuffled()
        GameState(
            tiles = tileNumbers
                .map { TileData.Number(it) }
                .let { calculateSides(it + TileData.Blank, boardSize) }
        )
    }
}