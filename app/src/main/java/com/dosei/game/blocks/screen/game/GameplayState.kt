package com.dosei.game.blocks.screen.game

import android.os.Parcelable
import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.domain.calculateSides
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

const val DEFAULT_SIZE = 4
const val INVALID_VALUE = -1

@Parcelize
data class GameplayState(
    val boardSize: Int = DEFAULT_SIZE,
    val tiles: List<TileData> = (1..15)//.shuffled()
        .map { TileData.Number(it) }
        .let { calculateSides(it + TileData.Blank, DEFAULT_SIZE) },
    val isVictory: Boolean = false
) : Parcelable

@Parcelize
sealed class TileData : Parcelable {
    abstract val value: Int
    abstract val availableDirection: Direction?

    object Blank : TileData() {
        @IgnoredOnParcel
        override val value: Int = INVALID_VALUE

        @IgnoredOnParcel
        override val availableDirection: Direction? = null
    }

    data class Number(
        override val value: Int,
        override val availableDirection: Direction? = null
    ) : TileData()
}


