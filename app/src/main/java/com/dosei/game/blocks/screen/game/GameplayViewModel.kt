package com.dosei.game.blocks.screen.game

import androidx.lifecycle.ViewModel
import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.domain.GameManager
import javax.inject.Inject

//@HiltViewModel
class GameplayViewModel @Inject constructor(
    private val gameManager: GameManager,
) : ViewModel() {

    val state = gameManager.state

    fun onMove(direction: Direction) {
        gameManager.move(direction)
    }

    fun onReset() {
        gameManager.reset()
    }

    override fun onCleared() {

    }
}