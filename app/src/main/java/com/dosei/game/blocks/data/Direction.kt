package com.dosei.game.blocks.data

import androidx.compose.foundation.gestures.Orientation

enum class Direction(val orientation: Orientation) {
    UP(Orientation.Vertical),
    LEFT(Orientation.Horizontal),
    RIGHT(Orientation.Horizontal),
    DOWN(Orientation.Vertical),
    NONE(Orientation.Horizontal)
}