package com.dosei.game.blocks.screen.game

import androidx.lifecycle.ViewModel
import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.domain.GameManager
import javax.inject.Inject

class GameplayViewModel(
    private val gameManager: GameManager,
) : ViewModel() {

    val state = gameManager.state

    fun onMove(direction: Direction) {
        gameManager.move(direction)
    }

    fun onReset() = gameManager.reset()

    fun onStart() = gameManager.resume()

    fun onStop() = gameManager.pause()
}