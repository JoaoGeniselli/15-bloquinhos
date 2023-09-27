package com.dosei.game.blocks.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosei.game.blocks.screen.game.TileData
import com.dosei.game.blocks.screen.game.TileData.Number

@Composable
@OptIn(ExperimentalTextApi::class)
fun Board2(modifier: Modifier = Modifier, tiles: List<TileData>, boardSize: Int) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        val tileColor = MaterialTheme.colorScheme.background
        val measurer = rememberTextMeasurer()
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(8.dp)
        ) {
            Canvas(modifier = Modifier.padding(16.dp)) {
                val spacing = 8.dp.toPx()
                val cellWidth = (size.width / boardSize) - (spacing * 3 / 4)
                val slotWidth = cellWidth + spacing
                val cellSize = Size(width = cellWidth, height = cellWidth)
                val textSize = 32.sp
                val textSizePx = textSize.toPx()

                for (y in 0 until boardSize) {
                    for (x in 0 until boardSize) {
                        val tileIndex = y * boardSize + x
                        val tile = tiles.getOrNull(tileIndex)
                        val topLeft = Offset(x * slotWidth, y * slotWidth)

                        val textTopLeft = Offset(
                            x = topLeft.x,
                            y = topLeft.y + (cellWidth / 2) - (textSizePx / 1.5f),
                        )

                        if (tile is Number) {
                            drawRoundRect(
                                color = tileColor,
                                topLeft = topLeft,
                                size = cellSize,
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                            drawText(
                                text = tile.value.toString(),
                                textMeasurer = measurer,
                                topLeft = textTopLeft,
                                size = Size(cellWidth, Float.NaN),
                                style = TextStyle(fontSize = textSize, textAlign = TextAlign.Center)
                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun PreviewBoard2() {
    Board2(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        boardSize = 4,
        tiles = (1..10).map { Number(it) } +
                TileData.Blank +
                (11..15).map { Number(it) },
    )
}
