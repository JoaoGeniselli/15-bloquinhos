package com.dosei.game.blocks.domain

import com.dosei.game.blocks.screen.game.TileData

fun checkVictory(tiles: List<TileData>): Boolean {
    val numbers = tiles.filterIsInstance<TileData.Number>().map { it.value }
    return numbers.withIndex().all { (index, value) -> index == value.dec() }
}