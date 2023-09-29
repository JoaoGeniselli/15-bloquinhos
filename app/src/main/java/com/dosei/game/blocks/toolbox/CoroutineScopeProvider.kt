package com.dosei.game.blocks.toolbox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineScopeProvider @Inject constructor() {

    val io get() = Dispatchers.IO
    val ioScope get() = CoroutineScope(Dispatchers.IO)
}