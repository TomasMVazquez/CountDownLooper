package com.toms.applications.countdownlooper.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toms.applications.countdownlooper.R

@ExperimentalFoundationApi
@Composable
fun NumericKeyboard(onClick: (String) -> Unit){
    val numbers = (1..9).map { it.toString() }.toMutableList().apply {
        add("00")
        add("0")
        add("")
    }

    Box(modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyVerticalGrid(
            modifier = Modifier,
            cells = GridCells.Fixed(3)
        ){
            items(numbers){ item ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if(numbers[numbers.size -1] != item) {
                        TextButton(
                            modifier = Modifier.size(50.dp),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.LightGray),
                            colors = textButtonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            ),
                            onClick = { onClick(item) }
                        ) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }else {
                        OutlinedButton(
                            modifier = Modifier.size(50.dp),
                            onClick = { onClick(item) },
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
    }
}