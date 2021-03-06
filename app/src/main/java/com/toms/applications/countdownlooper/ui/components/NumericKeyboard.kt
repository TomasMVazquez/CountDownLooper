package com.toms.applications.countdownlooper.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toms.applications.countdownlooper.R

@Composable
fun NumericKeyboard(numbers: List<String>,onClick: (String) -> Unit){

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(0.dp,8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(numbers) { number ->
            if(number.isNotEmpty()) {
                TextButton(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = textButtonColors(
                        contentColor = Color.White,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ),
                    onClick = { onClick(number) }
                ) {
                    Text(
                        text = number,
                        style = MaterialTheme.typography.body2
                    )
                }
            }else {
                OutlinedButton(
                    modifier = Modifier.size(50.dp),
                    onClick = { onClick(number) },
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Backspace,
                        contentDescription = stringResource(
                            R.string.content_description_clear
                        )
                    )
                }
            }
        }
    }
}