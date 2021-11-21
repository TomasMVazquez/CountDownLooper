package com.toms.applications.countdownlooper.screens.init

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.toms.applications.countdownlooper.R
import androidx.navigation.fragment.findNavController
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.toms.applications.countdownlooper.ui.components.*
import com.toms.applications.countdownlooper.utils.composeView
import com.toms.applications.countdownlooper.utils.toTimeFormat
import com.toms.applications.countdownlooper.utils.toTimer


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class TimerInitFragment : Fragment() {

    private val numbers = (1..9).map { it.toString() }.toMutableList().apply {
        add("00")
        add("0")
        add("")
    }.chunked(3)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        return composeView {
            var timeSettled by rememberSaveable { mutableStateOf("00 : 00 : 00") }
            var timeClicked by rememberSaveable { mutableStateOf("") }
            var repeats by rememberSaveable { mutableStateOf(0) }

            Scaffold(
                floatingActionButton = {
                   if (timeClicked.isNotEmpty() && timeClicked.toInt() > 0){
                       StartFloatingActionButton(onClick = {
                           timeClicked.toTimer().let {
                               onStartCountDown(
                                   it[0] ?: 0,
                                   it[1] ?: 0,
                                   it[2] ?: 0,
                                   repeats
                               )
                           }
                       })
                   }
               },
                floatingActionButtonPosition = FabPosition.Center,
                backgroundColor = MaterialTheme.colors.background
            ) { padding ->

                LazyColumn(modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp)
                ) {

                    item {
                        Text(
                            text = timeSettled,
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                    item {
                        GenericSpacer(type = SpacerType.VERTICAL, padding = 4.dp)
                    }

                    items(numbers){ rowList ->
                        NumericKeyboard(rowList, onClick = { clicked ->
                            if (clicked.isNotEmpty()){
                                clicked.forEach { numberClicked ->
                                    if (timeClicked.length <= 5) timeClicked += numberClicked
                                }
                            }else {
                                timeClicked = timeClicked.dropLast(1)
                            }
                            timeClicked = timeClicked.trimStart('0')
                            timeSettled = timeClicked.toTimeFormat()
                        })
                    }
                    item {
                        GenericSpacer(type = SpacerType.VERTICAL, padding = 2.dp)
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = getString(R.string.label_repeat),
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )

                        }
                    }
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            PlusMinusNumber(
                                input = repeats,
                                onInputChange = {
                                    repeats = it
                                }
                            )
                        }
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