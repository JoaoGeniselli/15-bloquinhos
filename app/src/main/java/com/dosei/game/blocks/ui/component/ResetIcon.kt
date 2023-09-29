package com.dosei.game.blocks.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import com.dosei.game.blocks.R

@Composable
fun ResetIcon() {
    Icon(
        modifier = Modifier.scale(scaleX = -1f, scaleY = 1f),
        imageVector = Icons.Default.Refresh,
        contentDescription = stringResource(R.string.action_reset)
    )
}