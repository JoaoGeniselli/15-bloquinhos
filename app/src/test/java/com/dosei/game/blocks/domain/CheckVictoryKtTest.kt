package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.TileData.Blank
import com.dosei.game.blocks.data.TileData.Number
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CheckVictoryKtTest {

    private lateinit var checkVictory: CheckVictory

    @Before
    fun before() {
        checkVictory = CheckVictory()
    }

    @Test
    fun `test victory`() = runBlocking {
        val items = (1..15).map { Number(it) } + Blank
        assertTrue(checkVictory(items))
    }

    @Test
    fun `test non victory`() = runBlocking {
        val items = ((1..15).map { Number(it) } + Blank).shuffled()
        assertFalse(checkVictory(items))
    }
}