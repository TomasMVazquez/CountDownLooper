package com.toms.applications.countdownlooper.screens.init

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.toms.applications.countdownlooper.R
import androidx.navigation.fragment.findNavController
import com.toms.applications.countdownlooper.ui.components.*
import com.toms.applications.countdownlooper.utils.composeView
import com.toms.applications.countdownlooper.utils.toTimeFormat
import com.toms.applications.countdownlooper.utils.toTimer


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class TimerInitFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        return composeView {
            var timeSettled by rememberSaveable { mutableStateOf("00 : 00 : 00") }
            var timeClicked by rememberSaveable { mutableStateOf("") }
            var repeats by rememberSaveable { mutableStateOf("0") }

            Scaffold(
                floatingActionButton = {
                   if (timeClicked.isNotEmpty() && timeClicked.toInt() > 0){
                       FloatingActionButton(
                           onClick = {
                               timeClicked.toTimer().let {
                                   onStartCountDown(
                                       it[0] ?: 0,
                                       it[1] ?: 0,
                                       it[2] ?: 0,
                                       repeats.toInt()
                                   )
                               }
                           }
                       ) {
                           Icon(
                               imageVector = Icons.Default.PlayArrow,
                               contentDescription = getString(R.string.content_description_start)
                           )
                       }
                   }
               },
                floatingActionButtonPosition = FabPosition.Center,
                backgroundColor = MaterialTheme.colors.background
            ) { padding ->

                Column(modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp)
                ) {

                    Text(
                        text = timeSettled,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    GenericSpacer(type = SpacerType.VERTICAL, padding = 4.dp)

                    NumericKeyboard { clicked ->
                        if (clicked.isNotEmpty()){
                            clicked.forEach { numberClicked ->
                                if (timeClicked.length <= 5) timeClicked += numberClicked
                            }
                        }else {
                            timeClicked = timeClicked.dropLast(1)
                        }
                        timeClicked = timeClicked.trimStart('0')
                        timeSettled = timeClicked.toTimeFormat()
                    }

                    GenericSpacer(type = SpacerType.VERTICAL, padding = 4.dp)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = getString(R.string.label_repeat),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        MyOutlinedTextField(
                            input = repeats,
                            onValueChange = { repeats = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(TimerInitFragmentDirections.actionTimerSettingsFragmentToSettingsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onStartCountDown(hours: Int, minutes: Int, seconds: Int, repetitions: Int){
        val action = TimerInitFragmentDirections.actionTimerSettingsFragmentToTimerFragment(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            repetition = repetitions
        )
        findNavController().navigate(action)
    }

}