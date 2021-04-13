package com.toms.applications.countdownlooper.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

fun Fragment.hideKeyboard() {
    val inputMethodManager = this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

/**
 * Reduce Boilerplate when creating view models
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object: ViewModelProvider.Factory{
        override fun <U : ViewModel?> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get(T::class.java)
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