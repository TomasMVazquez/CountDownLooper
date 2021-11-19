package com.toms.applications.countdownlooper.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class SpacerType {
    VERTICAL,
    HORIZONTAL
}

@Composable
fun GenericSpacer(type: SpacerType, padding: Dp = 0.dp, size: Dp = 1.dp, backgroundColor: Color = Color.Transparent) {
    when(type){
        SpacerType.VERTICAL -> {
            VerticalSpacer(verticalPadding = padding, size = size, backgroundColor = backgroundColor)
        }
        SpacerType.HORIZONTAL -> {
            HorizontalSpacer(horizontalPadding = padding, size = size, backgroundColor = backgroundColor)
        }
    }
}

@Composable
fun VerticalSpacer(verticalPadding: Dp, size: Dp, backgroundColor: Color) {
    Spacer(modifier = Modifier
        .padding(0.dp, verticalPadding)
        .fillMaxWidth()
        .size(size)
        .background(color = backgroundColor)
    )
}

@Composable
fun HorizontalSpacer(horizontalPadding: Dp, size: Dp, backgroundColor: Color) {
    Spacer(modifier = Modifier
        .padding(horizontalPadding, 0.dp)
        .fillMaxWidth()
        .size(size)
        .background(color = backgroundColor)
    )
}