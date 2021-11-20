package com.toms.applications.countdownlooper.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toms.applications.countdownlooper.ui.theme.CountDownLooperTheme
import java.util.*
import java.util.concurrent.TimeUnit

fun Fragment.hideKeyboard() {
    val inputMethodManager = this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

/**
 * Reduce Boilerplate when creating view models
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T: ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory)[T::class.java]
}

fun Fragment.composeView(content: @Composable () -> Unit): ComposeView {
    return ComposeView(requireContext()).apply {
        setContent {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            CountDownLooperTheme {
                content()
            }
        }
    }
}

fun Long.toTimerStringFormat(): String{
    var elapsedSeconds = this
    // Break the elapsed seconds into hours, minutes, and seconds.
    var hours: Long = 0
    var minutes: Long = 0
    var seconds: Long = 0
    if (elapsedSeconds >= 3600) {
        hours = elapsedSeconds / 3600
        elapsedSeconds -= hours * 3600
    }
    if (elapsedSeconds >= 60) {
        minutes = elapsedSeconds / 60
        elapsedSeconds -= minutes * 60
    }
    seconds = elapsedSeconds
    val sb = StringBuilder(8)
    val f = Formatter(sb, Locale.getDefault())
    return f.format("%1\$02d : %2\$02d : %3\$02d", hours, minutes, seconds).toString()
}

fun String.toTimerLongFormat(): Long {
    var text = this

    var textSplitted = text.split(":")

    val hours = TimeUnit.HOURS.toMillis(textSplitted[0].trim().toLong())
    val minutes = TimeUnit.MINUTES.toMillis(textSplitted[1].trim().toLong())
    val seconds = TimeUnit.SECONDS.toMillis(textSplitted[2].trim().toLong())

    return hours + minutes + seconds
}

fun String.toTimeFormat(): String {
    var text = this
    while (text.length < 6){
        text = "0".plus(text)
    }

    val textChunked = text.chunked(2)

    return when(textChunked.size){
        1 -> "00 : 00 : ${textChunked[0]}"
        2 -> "00 : ${textChunked[0]} : ${textChunked[1]}"
        3 -> "${textChunked[0]} : ${textChunked[1]} : ${textChunked[2]}"
        else -> "00 : 00 : 00"
    }
}

fun String.toTimer(): Map<Int, Int> {
    val textChunked = this.trimStart('0').chunked(2)
    var timeSec: Long = 0

    when(textChunked.size){
        1 -> timeSec = textChunked[0].toLong()
        2 -> timeSec = (textChunked[1].toLong() * 60) + textChunked[0].toLong()
        3 -> timeSec = (textChunked[2].toLong() * 3600) + (textChunked[1].toLong() * 60) + textChunked[0].toLong()
    }

    val hs = timeSec.toInt() / 3600
    var temp = timeSec.toInt() - hs * 3600
    val mins = temp / 60
    temp -= mins * 60
    val secs = temp

    return mapOf(
        0 to hs,
        1 to mins,
        2 to secs
    )

}