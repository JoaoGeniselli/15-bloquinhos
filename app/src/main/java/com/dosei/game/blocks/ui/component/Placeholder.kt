package com.dosei.game.blocks.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Placeholder(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier) {
        val paths = Path().apply {
            moveTo(0f,0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            lineTo(0f, 0f)
            lineTo(size.width, size.height)
            moveTo(size.width, 0f)
            lineTo(0f, size.height)
        }
        drawPath(paths, Color.Black, style = Stroke(width = 2.dp.toPx()))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlaceholder() {
    Surface(modifier = Modifier, color = Color.White) {
        Placeholder(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(16.dp)
        )
    }
}
