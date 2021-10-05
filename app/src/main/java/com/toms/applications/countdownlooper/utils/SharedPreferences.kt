package com.toms.applications.countdownlooper.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * To start or not the onBoarding when initializing the app
 */
fun getSharedPreferences(context: Context): SharedPreferences{
    return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}

object SharedPreferencesKeys{
    const val TIME_IN_BETWEEN = "timeInBettween"
}

fun onSetTime(context: Context, time: Int){
    getSharedPreferences(context).edit{ putInt(SharedPreferencesKeys.TIME_IN_BETWEEN,time) }
}

fun onGetTime(context: Context): Int{
    return getSharedPreferences(context).getInt(SharedPreferencesKeys.TIME_IN_BETWEEN,-1)
}