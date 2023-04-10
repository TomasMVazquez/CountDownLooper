package com.toms.applications.countdownlooper.ui.screens.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.databinding.FragmentTimerBinding
import com.toms.applications.countdownlooper.utils.BeepHelper
import com.toms.applications.countdownlooper.utils.onGetTime
import com.toms.applications.countdownlooper.utils.toTimerLongFormat
import com.toms.applications.countdownlooper.utils.toTimerStringFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private val viewModel: TimerViewModel by viewModels()
    private val args by navArgs<TimerFragmentArgs>()

    private val beep = BeepHelper()
    private var countDownTime: Long = 0L
    private var timeInBetweenSetted: Long = 0L

    private var repetition by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        repetition = args.repetition - 1
        val hours = TimeUnit.HOURS.toMillis(args.hours.toLong())
        val minutes = TimeUnit.MINUTES.toMillis(args.minutes.toLong())
        val seconds = TimeUnit.SECONDS.toMillis(args.seconds.toLong())
        countDownTime = hours + minutes + seconds

        val timeSaved = onGetTime(requireContext())
        timeInBetweenSetted = if (timeSaved >= 0) (timeSaved.toLong() * 1000) else TimerViewModel.COUNT_DOWN_TIME_IN_BETWEEN
        viewModel.getStartedTimeInBetween(timeInBetweenSetted)

        with(binding){
            timerViewModel = viewModel
            repetitions = repetition
            timerText.text = getString(R.string.time_set,args.hours,args.minutes,args.seconds)

            with(viewModel){
                currentTimeInBetween.observe(viewLifecycleOwner) { newTime ->
                    timerInBetweenText.text =  newTime.toTimerStringFormat()
                }

                currentTime.observe(viewLifecycleOwner) { newTime ->
                    timerText.text = newTime.toTimerStringFormat()
                }

                repetitionFinished.observe(viewLifecycleOwner){
                    it.getContentIfNotHandled()?.let {
                        lifecycleScope.launch {
                            delay(500)
                            onStartTimer()
                        }
                    }
                }

                inBetweenTimeFinished.observe(viewLifecycleOwner){
                    it.getContentIfNotHandled()?.let {
                        onStartTimerInBetween()
                    }
                }

                eventBeep.observe(viewLifecycleOwner){ event ->
                    event.getContentIfNotHandled()?.let {
                        when(it){
                            TimerViewModel.BeepType.IN_BETWEEN -> beep.beep()
                            TimerViewModel.BeepType.SHORT -> beep.shortBeep()
                            TimerViewModel.BeepType.LONG -> beep.longBeep()
                        }
                    }
                }

                onMyTimerPause.observe(viewLifecycleOwner){ onPause ->
                    if (onPause){
                        fabBtnPause.setImageResource(R.drawable.ic_start)
                        stopMyTimer()
                    } else {
                        fabBtnPause.setImageResource(R.drawable.ic_pause_24)
                        startTimer(timerText.text.toString().toTimerLongFormat())
                    }
                }

                onBetweenTimerPause.observe(viewLifecycleOwner){ onPause ->
                    if (onPause){
                        fabMiniBtnPause.setImageResource(R.drawable.ic_start)
                        stopTimerInBetween()
                    } else {
                        fabMiniBtnPause.setImageResource(R.drawable.ic_pause_24)
                        startTimerInBetween(timerInBetweenText.text.toString().toTimerLongFormat())
                    }
                }
            }
        }

        return binding.root
    }

    private fun onStartTimerInBetween() {
        binding.fabMiniBtnPause.visibility = View.GONE
        viewModel.startTimer(countDownTime)
        binding.fabBtnPause.visibility = View.VISIBLE
    }

    private fun onStartTimer(){
        binding.fabMiniBtnPause.visibility = View.VISIBLE
        binding.fabBtnPause.visibility = View.GONE
        if (repetition > 0){
            binding.timerText.text = getString(R.string.time_set,args.hours,args.minutes,args.seconds)
            repetition--
            binding.repetitions = repetition
            viewModel.startTimerInBetween(timeInBetweenSetted)
        }else{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}