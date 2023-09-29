package com.dosei.game.blocks.domain

import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.data.Dummy
import com.dosei.game.blocks.toolbox.tickerFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch

class GameManager(
    private val calculateSides: CalculateSides,
    private val checkVictory: CheckVictory,
    private val createGame: CreateGame,
    private val moveTiles: MoveTiles,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    private var _state = MutableStateFlow(Dummy)
    private var _tickerJob: Job? = null

    val state = _state.asStateFlow()

    init {
        reset()
    }

    private fun restartTicker() {
        _tickerJob?.cancel()
        _tickerJob = scope.launch {
            tickerFlow().cancellable().collect {
                with(_state.value) {
                    _state.emit(copy(time = time.inc()))
                }
            }
        }
    }

    fun move(direction: Direction) {
        scope.launch {
            val state = _state.value
            val updatedTiles = calculateSides(
                moveTiles(state.tiles, direction),
                state.boardSize
            )
            val isVictory = checkVictory(updatedTiles)

            _state.emit(
                state.copy(tiles = updatedTiles, isVictory = isVictory)
            )
        }
    }

    fun reset() {
        scope.launch {
            _state.emit(createGame())
        }
        restartTicker()
    }
}