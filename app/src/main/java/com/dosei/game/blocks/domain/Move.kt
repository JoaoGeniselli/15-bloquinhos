package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.screen.game.TileData

fun move(tiles: List<TileData>, direction: Direction): List<TileData> {
    val blankPosition = tiles.indexOf(TileData.Blank)
    val tileToMove = tiles.firstOrNull { it.availableDirection == direction }

    return if (tileToMove != null) {
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