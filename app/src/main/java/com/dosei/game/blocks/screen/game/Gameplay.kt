package com.dosei.game.blocks.screen.game

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosei.game.blocks.R
import com.dosei.game.blocks.data.Direction
import com.dosei.game.blocks.domain.calculateSides
import com.dosei.game.blocks.domain.checkVictory
import com.dosei.game.blocks.domain.move
import com.dosei.game.blocks.ui.component.AdvertView
import com.dosei.game.blocks.ui.component.Board
import com.dosei.game.blocks.ui.component.NumberTile
import com.dosei.game.blocks.ui.theme.SlidingBlocksTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import kotlin.math.abs

@Composable
fun GameplayScreen(
    modifier: Modifier = Modifier
) {
    var state by rememberSaveable { mutableStateOf(GameplayState()) }
    val scope = rememberCoroutineScope { Dispatchers.IO }
    GameplayContent(
        modifier = modifier,
        state = state,
        onReset = { state = GameplayState() },
        onMove = { direction ->
            scope.launch {
                val updatedTiles = calculateSides(
                    move(state.tiles, direction),
                    state.boardSize
                )
                state = state.copy(tiles = updatedTiles, isVictory = checkVictory(updatedTiles))
            }
        },
    )
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
fun GameplayContent(
    modifier: Modifier = Modifier,
    state: GameplayState,
    onReset: () -> Unit = {},
    onMove: (Direction) -> Unit
) {
    Scaffold(
        modifier,
        topBar = { TopBar(onReset) },
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp)
    ) { padding ->
        var movementOffset by remember { mutableStateOf(Offset(.0f, .0f)) }

        Column(Modifier.padding(padding)) {
            Spacer(modifier = Modifier.weight(1f))
            Board(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(0) {
                        detectDragGestures(
                            onDragStart = { movementOffset = Offset(.0f, .0f) },
                            onDragCancel = { movementOffset = Offset(.0f, .0f) },
                            onDrag = { input, offset ->
                                input.consume()
                                movementOffset = Offset(
                                    movementOffset.x + offset.x,
                                    movementOffset.y + offset.y
                                )
                            },
                            onDragEnd = {
                                val direction = if (abs(movementOffset.x) > abs(movementOffset.y)) {
                                    if (movementOffset.x > 0) Direction.RIGHT else Direction.LEFT
                                } else {
                                    if (movementOffset.y > 0) Direction.DOWN else Direction.UP
                                }
                                onMove(direction)
                            }
                        )
                    },
                buildTiles = {

                    items(state.tiles, key = { it.value }) { tile ->
                        val mod = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(animationSpec = tween(durationMillis = 100))
                            .aspectRatio(1f)

                        when (tile) {
                            is TileData.Number -> {
                                NumberTile(
                                    modifier = mod,
                                    number = tile.value
                                )
                            }

                            else -> {
                                Spacer(
                                    modifier = mod
                                )
                            }
                        }
                    }
                }
            )

            if (state.isVictory) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.you_won),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.align(CenterHorizontally),
                    onClick = onReset
                ) {
                    ResetIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.action_reset))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AdvertView(unitId = R.string.admob_gameplay_banner)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onReset: () -> Unit) {
    val scope = rememberCoroutineScope()
    val helpState = remember { RichTooltipState() }
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = onReset) {
                ResetIcon()
            }
            RichTooltipBox(
                modifier = Modifier,
                tooltipState = helpState,
                title = { Text(stringResource(R.string.help)) },
                text = { Text(stringResource(R.string.gameplay_help)) },
                action = {
                    Button(
                        onClick = { scope.launch { helpState.dismiss() } },
                        content = { Text(text = stringResource(R.string.understood)) }
                    )
                }
            ) {
                IconButton(onClick = { scope.launch { helpState.show() } }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_help_outline_24),
                        contentDescription = stringResource(R.string.help)
                    )
                }
            }
        }
    )
}

@Composable
private fun ResetIcon() {
    Icon(
        modifier = Modifier.scale(scaleX = -1f, scaleY = 1f),
        imageVector = Icons.Default.Refresh,
        contentDescription = stringResource(R.string.action_reset)
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewGameplay() {
    SlidingBlocksTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
            GameplayContent(
                modifier = Modifier,
                state = GameplayState(
                    tiles = (1..10).map { TileData.Number(it) } +
                            TileData.Blank +
                            (11..15).map { TileData.Number(it) },
                    isVictory = true
                ),
                {},
                {},
            )
        }
    }
}
