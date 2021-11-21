package com.toms.applications.countdownlooper.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toms.applications.countdownlooper.R

@Composable
fun PlusMinusNumber(input: Int, onInputChange: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedButton(
            modifier = Modifier.size(50.dp),
            onClick = { if (input > 0) onInputChange(input.minus(1)) },
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = stringResource(
                    R.string.content_description_remove
                )
            )
        }

        Column(modifier = Modifier
            .size(50.dp)
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant
                )
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = input.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = Color.White
            )
        }

        OutlinedButton(
            modifier = Modifier.size(50.dp),
            onClick = { onInputChange(input.plus(1)) },
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(
                    R.string.content_description_add
                )
            )
        }

    }

}