package com.dosei.game.blocks

import android.app.Application
import com.dosei.game.blocks.domain.CalculateSides
import com.dosei.game.blocks.domain.CheckVictory
import com.dosei.game.blocks.domain.CreateGame
import com.dosei.game.blocks.domain.GameManager
import com.dosei.game.blocks.domain.MoveTiles
import com.dosei.game.blocks.screen.game.GameplayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BlocksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BlocksApplication)
            modules(diModule)
        }
    }
}

val diModule = module {
    single {
        GameManager(
            calculateSides = get(),
            checkVictory = get(),
            createGame = get(),
            moveTiles = get()
        )
    }

    factory { CalculateSides() }
    factory { CheckVictory() }
    factory { CreateGame(get()) }
    factory { MoveTiles() }

    viewModel { GameplayViewModel(get()) }
}