package com.toms.applications.countdownlooper.utils

import android.media.AudioManager
import android.media.ToneGenerator

class BeepHelper {
    private val toneG = ToneGenerator(AudioManager.STREAM_MUSIC, 100) //STREAM_MUSIC

    fun beep() {
        toneG.startTone(ToneGenerator.TONE_CDMA_PIP, 100) //TONE_CDMA_PIP //TONE_CDMA_EMERGENCY_RINGBACK //TONE_CDMA_ALERT_CALL_GUARD //TONE_PROP_BEEP2
    }

    fun shortBeep() {
        toneG.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 100) //TONE_CDMA_PIP //TONE_CDMA_EMERGENCY_RINGBACK //TONE_CDMA_ALERT_CALL_GUARD //TONE_PROP_BEEP2
    }

    fun longBeep(){
        toneG.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 300)
    }
}