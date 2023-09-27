package com.dosei.game.blocks.domain

import com.dosei.game.blocks.screen.game.TileData.Blank
import com.dosei.game.blocks.screen.game.TileData.Number
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CheckVictoryKtTest {

    @Test
    fun `test victory`() {
        val items = (1..15).map { Number(it) } + Blank
        assertTrue(checkVictory(items))
    }

    @Test
    fun `test non victory`() {
        val items = ((1..15).map { Number(it) } + Blank).shuffled()
        assertFalse(checkVictory(items))
    }
}