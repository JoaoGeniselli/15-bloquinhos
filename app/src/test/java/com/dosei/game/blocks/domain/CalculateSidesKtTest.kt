package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.screen.game.TileData.Blank
import com.dosei.game.blocks.screen.game.TileData.Number
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateSidesKtTest {

    @Test
    fun `test calculation with all sides`() {
        val tiles = matrix(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, BL, 11,
            12, 13, 14, 15
        )

        val result = calculateSides(tiles, 4)

        val expected = listOf(
            Number(1),
            Number(2),
            Number(3),
            Number(4),
            Number(5),
            Number(6),
            Number(7, Direction.DOWN),
            Number(8),
            Number(9),
            Number(10, Direction.RIGHT),
            Blank,
            Number(11, Direction.LEFT),
            Number(12),
            Number(13),
            Number(14, Direction.UP),
            Number(15),
        )

        assertEquals(
            expected,
            result
        )
    }

    @Test
    fun `test calculation with negative sides`() {
        val tiles = matrix(
            BL, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 1, 11,
            12, 13, 14, 15
        )

        val result = calculateSides(tiles, 4)

        val expected = listOf(
            Blank,
            Number(2, Direction.LEFT),
            Number(3),
            Number(4),
            Number(5, Direction.UP),
            Number(6),
            Number(7),
            Number(8),
            Number(9),
            Number(10),
            Number(1),
            Number(11),
            Number(12),
            Number(13),
            Number(14),
            Number(15),
        )

        assertEquals(
            expected,
            result
        )
    }

    @Test
    fun `test calculation with out of range sides`() {
        val tiles = matrix(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 15, 11,
            12, 13, 14, BL
        )

        val result = calculateSides(tiles, 4)

        val expected = listOf(
            Number(1),
            Number(2),
            Number(3),
            Number(4),
            Number(5),
            Number(6),
            Number(7),
            Number(8),
            Number(9),
            Number(10),
            Number(15),
            Number(11, Direction.DOWN),
            Number(12),
            Number(13),
            Number(14, Direction.RIGHT),
            Blank,
        )

        assertEquals(
            expected,
            result
        )
    }

    @Test
    fun `test calculation with result on different lines`() {
        val tiles = matrix(
            1, 2, 3, 4,
            5, 6, 7, BL,
            9, 10, 15, 11,
            12, 13, 14, 8
        )

        val result = calculateSides(tiles, 4)

        val expected = listOf(
            Number(1),
            Number(2),
            Number(3),
            Number(4, Direction.DOWN),
            Number(5),
            Number(6),
            Number(7, Direction.RIGHT),
            Blank,
            Number(9),
            Number(10),
            Number(15),
            Number(11, Direction.UP),
            Number(12),
            Number(13),
            Number(14),
            Number(8),
        )

        assertEquals(
            expected,
            result
        )
    }

    private fun matrix(vararg values: Int) =
        values.map { if (it == BL) Blank else Number(it) }

    companion object {
        const val BL = -1
    }
}