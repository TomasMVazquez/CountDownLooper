package com.toms.applications.countdownlooper.ui.components

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.toms.applications.countdownlooper.ui.theme.Shapes

@ExperimentalComposeUiApi
@Composable
fun MyOutlinedTextField(modifier: Modifier = Modifier,input: String, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions){
    var text by rememberSaveable { mutableStateOf(input) }
    val keyboardController = LocalSoftwareKeyboardController.current

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = if (newText.isEmpty()) {
                    "0"
                } else {
                    val txt = newText
                        .replace("[^0-9 ]".toRegex(),"")
                        .trimStart { it == '0' || it == ' '}
                    if (txt.isEmpty()) "0" else txt
                }
                onValueChange(text)
            },
            modifier = modifier.widthIn(1.dp).navigationBarsWithImePadding(),
            shape = Shapes.medium,
            maxLines = 1,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            trailingIcon = {
                if (text.isNotEmpty()){
                    IconButton(onClick = { text = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = null
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                unfocusedBorderColor = MaterialTheme.colors.secondary
            )
        )
    }
}
