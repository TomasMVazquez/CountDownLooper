package com.toms.applications.countdownlooper.screens.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.databinding.FragmentTimerBinding
import com.toms.applications.countdownlooper.utils.BeepHelper
import com.toms.applications.countdownlooper.utils.getViewModel
import com.toms.applications.countdownlooper.utils.onGetTime
import com.toms.applications.countdownlooper.utils.toTimerStringFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel
    private val args by navArgs<TimerFragmentArgs>()

    private val beep = BeepHelper()

    private var repetition by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        repetition = args.repetition
        val hours = TimeUnit.HOURS.toMillis(args.hours.toLong())
        val minutes = TimeUnit.MINUTES.toMillis(args.minutes.toLong())
        val seconds = TimeUnit.SECONDS.toMillis(args.seconds.toLong())
        val timeInBetweenSetted = onGetTime(requireContext()).toLong() * 1000

        viewModel = getViewModel { TimerViewModel(timeInBetweenSetted,hours + minutes + seconds) }

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
            }
        }

        return binding.root
    }

    private fun onStartTimerInBetween() {
        viewModel.startTimer()
    }

    private fun onStartTimer(){
        if (repetition > 0){
            binding.timerText.text = getString(R.string.time_set,args.hours,args.minutes,args.seconds)
            repetition--
            binding.repetitions = repetition
            viewModel.startTimerInBetween()
        }else{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}