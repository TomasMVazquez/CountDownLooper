package com.toms.applications.countdownlooper.ui.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.databinding.FragmentSettingsBinding
import com.toms.applications.countdownlooper.utils.onGetTime
import com.toms.applications.countdownlooper.utils.onSetTime

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_settings, container, false)

        val timeSettled = onGetTime(requireContext())

        with(binding) {
            with(numPickerTimeBetween){
                maxValue = 59
                value = if (timeSettled >= 0) timeSettled else 5
                setFormatter { String.format("%02d", it) }
                setOnScrollListener { picker, scrollState ->
                    if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                        onTimeSettled(picker.value)
                    }
                }
            }
            aboutUs.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToAboutUsFragment())
            }
        }

        return binding.root
    }

    private fun onTimeSettled(time: Int) {
        onSetTime(requireContext(),time)
    }
}