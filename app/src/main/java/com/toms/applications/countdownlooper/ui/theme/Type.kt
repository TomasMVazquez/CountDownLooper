package com.toms.applications.countdownlooper.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.toms.applications.countdownlooper.R

// Set of Material typography styles to start with
//ClockTypography
val ClockFontFamily = FontFamily(
    Font(R.font.orbitron_medium)
)

val Typography = Typography(
    h3 = TextStyle(
        fontFamily = ClockFontFamily,
        fontSize = 45.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = ClockFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
