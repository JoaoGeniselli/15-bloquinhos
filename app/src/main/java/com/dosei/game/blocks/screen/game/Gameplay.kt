package com.dosei.game.blocks.screen.game

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.game.blocks.R
import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.data.GameState
import com.dosei.game.blocks.data.TileData
import com.dosei.game.blocks.data.TileData.Number
import com.dosei.game.blocks.toolbox.detectDragDirection
import com.dosei.game.blocks.ui.component.AdvertView
import com.dosei.game.blocks.ui.component.Board
import com.dosei.game.blocks.ui.component.NumberTile
import com.dosei.game.blocks.ui.component.ResetIcon
import com.dosei.game.blocks.ui.component.TopBar
import com.dosei.game.blocks.ui.theme.SlidingBlocksTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameplayScreen(
    modifier: Modifier = Modifier,
    viewModel: GameplayViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    GameplayContent(
        modifier = modifier,
        state = state,
        onReset = viewModel::onReset,
        onMove = viewModel::onMove,
    )
}

@Composable
fun GameplayContent(
    modifier: Modifier = Modifier,
    state: GameState?,
    onReset: () -> Unit = {},
    onMove: (Direction) -> Unit
) {
    Scaffold(
        modifier,
        topBar = { TopBar(onReset) },
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp)
    ) { padding ->
        if (state != null) {
            Column(
                modifier = Modifier.padding(padding),
                verticalArrangement = spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Time(state)

                GameBoard(state, onMove)

                if (state.isVictory) {
                    VictoryMessage(onReset)
                }

                Spacer(modifier = Modifier.weight(1f))
                AdvertView(unitId = R.string.admob_gameplay_banner)
            }
        }
    }
}

@Composable
private fun ColumnScope.Time(state: GameState) {
    Text(
        modifier = Modifier.Companion.align(CenterHorizontally),
        text = formatTime(timeInSeconds = state.time.toLong()),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GameBoard(
    state: GameState,
    onMove: (Direction) -> Unit
) {
    Board(
        modifier = Modifier
            .fillMaxWidth()
            .detectDragDirection { onMove(it) },
        buildTiles = {
            items(state.tiles, key = { it.value }) { tile ->
                val mod = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(animationSpec = tween(durationMillis = 100))
                    .aspectRatio(1f)

                when (tile) {
                    is Number -> NumberTile(modifier = mod, number = tile.value)
                    else -> Spacer(modifier = mod)
                }
            }
        }
    )
}

@Composable
fun ColumnScope.VictoryMessage(onReset: () -> Unit) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.you_won),
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center
    )
    Button(
        modifier = Modifier.align(CenterHorizontally),
        onClick = onReset
    ) {
        ResetIcon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(R.string.action_reset))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewGameplay() {
    SlidingBlocksTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
            GameplayContent(
                modifier = Modifier,
                state = GameState(
                    tiles = (1..10).map { Number(it) } +
                            TileData.Blank +
                            (11..15).map { Number(it) },
                    isVictory = true,
                    time = 65
                ),
                {},
                {},
            )
        }
    }
}
