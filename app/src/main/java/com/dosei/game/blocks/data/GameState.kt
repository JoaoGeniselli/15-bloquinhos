package com.dosei.game.blocks.data

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

const val DEFAULT_SIZE = 4
const val INVALID_VALUE = -1

@Parcelize
data class GameState(
    val boardSize: Int = DEFAULT_SIZE,
    val tiles: List<TileData>,
    val isVictory: Boolean = false,
    val time: Int = 0
) : Parcelable

val Dummy = GameState(
    tiles = (1..DEFAULT_SIZE * DEFAULT_SIZE).map { TileData.Blank },
)

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


