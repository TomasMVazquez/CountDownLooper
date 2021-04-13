package com.toms.applications.countdownlooper.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.databinding.FragmentTimerSettingsBinding
import com.toms.applications.countdownlooper.utils.hideKeyboard


class TimerSettingsFragment : Fragment() {

    private lateinit var binding: FragmentTimerSettingsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_timer_settings,
                container,
                false
        )

        binding.reRunTIET.setText("0")

        with(binding.numPickerHours){
            maxValue = 23
            setFormatter { String.format("%02d", it) }
            setOnScrollListener { picker, scrollState -> if(scrollState == SCROLL_STATE_IDLE) { onTimeSettled() } }
        }
        with(binding.numPickerMinutes){
            maxValue = 59
            setFormatter { String.format("%02d", it) }
            setOnScrollListener { picker, scrollState -> if(scrollState == SCROLL_STATE_IDLE) { onTimeSettled() } }
        }
        with(binding.numPickerSeconds){
            maxValue = 59
            setFormatter { String.format("%02d", it) }
            setOnScrollListener { picker, scrollState -> if(scrollState == SCROLL_STATE_IDLE) { onTimeSettled() } }
        }

        binding.reRunTIET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                if(v.text.isNullOrEmpty()) v.text = "0"
                v.clearFocus()
                hideKeyboard()
            }
            return@setOnEditorActionListener true
        }

        binding.startFAB.setOnClickListener {
            val action = TimerSettingsFragmentDirections.actionTimerSettingsFragmentToTimerFragment(
                    binding.numPickerHours.value,
                    binding.numPickerMinutes.value,
                    binding.numPickerSeconds.value,
                    binding.reRunTIET.text.toString().toInt()
            )
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun onTimeSettled() {
        if (binding.numPickerHours.value > 0 || binding.numPickerMinutes.value > 0 || binding.numPickerSeconds.value > 0){
            binding.startFAB.visibility = View.VISIBLE
        }else{
            binding.startFAB.visibility = View.GONE
        }
    }

}