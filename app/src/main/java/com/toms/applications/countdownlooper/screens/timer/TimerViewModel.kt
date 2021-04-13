package com.toms.applications.countdownlooper.screens.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toms.applications.countdownlooper.utils.Event

class TimerViewModel(private val countDownTime: Long): ViewModel() {

    enum class BeepType { IN_BETWEEN , SHORT, LONG }

    private lateinit var timer : CountDownTimer
    private lateinit var timerInBetween : CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> get() = _currentTime

    private val _currentTimeInBetween = MutableLiveData<Long>()
    val currentTimeInBetween: LiveData<Long> get() = _currentTimeInBetween

    private val _repetitionFinished = MutableLiveData<Event<Boolean>>()
    val repetitionFinished: LiveData<Event<Boolean>> = _repetitionFinished

    private val _inBetweenTimeFinished = MutableLiveData<Event<Boolean>>()
    val inBetweenTimeFinished: LiveData<Event<Boolean>> = _inBetweenTimeFinished

    private val _eventBeep = MutableLiveData<Event<BeepType>>()
    val eventBeep: LiveData<Event<BeepType>> get() = _eventBeep

    init {
        startTimerInBetween()
    }

    fun startTimer(){
        startMyTimer(countDownTime)
    }

    private fun startMyTimer(startingTime:Long){
        timer = object : CountDownTimer(startingTime, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventBeep.value = Event(BeepType.LONG)
                _repetitionFinished.value = Event(true)
            }

        }

        timer.start()
    }

    fun startTimerInBetween(){
        timerInBetween = object : CountDownTimer(COUNT_DOWN_TIME_IN_BETWEEN, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTimeInBetween.value = (millisUntilFinished / ONE_SECOND)
                _eventBeep.value = Event(BeepType.IN_BETWEEN)
            }

            override fun onFinish() {
                _currentTimeInBetween.value = DONE
                _eventBeep.value = Event(BeepType.SHORT)
                _inBetweenTimeFinished.value = Event(true)
            }

        }

        timerInBetween.start()
    }

    private fun stopMyTimer(){
        timer.cancel()
    }

    private fun stopTimerInBetween(){
        timerInBetween.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        stopMyTimer()
        stopTimerInBetween()
    }

    companion object{
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        const val COUNT_DOWN_TIME_IN_BETWEEN = 5000L
    }
}