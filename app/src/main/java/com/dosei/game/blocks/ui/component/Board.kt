package com.dosei.game.blocks.ui.component

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Board(
    modifier: Modifier = Modifier,
    boardSize: Int = 4,
    gridState: LazyGridState = rememberLazyGridState(),
    buildTiles: LazyGridScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(boardSize),
            userScrollEnabled = false,
            verticalArrangement = spacedBy(8.dp),
            horizontalArrangement = spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
            state = gridState,
            content = buildTiles
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewBoard() {
    val numbers = (1..15).toList()
    Board(
        modifier = Modifier.fillMaxWidth(),
        buildTiles = {
            items(numbers) {
                NumberTile(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    number = it
                )
            }
        }
    )
}
