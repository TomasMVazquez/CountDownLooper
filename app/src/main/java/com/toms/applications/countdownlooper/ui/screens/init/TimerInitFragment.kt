package com.toms.applications.countdownlooper.ui.screens.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.ui.components.GenericSpacer
import com.toms.applications.countdownlooper.ui.components.NumericKeyboard
import com.toms.applications.countdownlooper.ui.components.PlusMinusNumber
import com.toms.applications.countdownlooper.ui.components.SpacerType
import com.toms.applications.countdownlooper.ui.components.StartFloatingActionButton
import com.toms.applications.countdownlooper.utils.composeView
import com.toms.applications.countdownlooper.utils.toTimeFormat
import com.toms.applications.countdownlooper.utils.toTimer

@ExperimentalAnimationApi
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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.toolbar_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_settings -> {
                            findNavController().navigate(TimerInitFragmentDirections.actionTimerSettingsFragmentToSettingsFragment())
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        return composeView {
            var timeSettled by rememberSaveable { mutableStateOf("00 : 00 : 00") }
            var timeClicked by rememberSaveable { mutableStateOf("") }
            var repeats by rememberSaveable { mutableStateOf(1) }

            Scaffold(
                floatingActionButton = {
                    if (timeClicked.isNotEmpty() && timeClicked.toInt() > 0) {
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
                    .padding(horizontal = 24.dp, vertical = 12.dp)
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
                        GenericSpacer(type = SpacerType.VERTICAL, padding = 4.dp)
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
                        GenericSpacer(type = SpacerType.VERTICAL, padding = 4.dp)
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

    private fun onStartCountDown(
        hours: Int,
        minutes: Int,
        seconds: Int,
        repetitions: Int
    ) {
        val action = TimerInitFragmentDirections.actionTimerSettingsFragmentToTimerFragment(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            repetition = repetitions
        )
        findNavController().navigate(action)
    }

}