package com.toms.applications.countdownlooper.screens.init

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.databinding.FragmentTimerInitBinding
import com.toms.applications.countdownlooper.utils.hideKeyboard
import androidx.navigation.fragment.findNavController


class TimerInitFragment : Fragment() {

    private lateinit var binding: FragmentTimerInitBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_timer_init,
                container,
                false
        )

        setHasOptionsMenu(true)

        with(binding){
            reRunTIET.setText("0")

            with(numPickerHours){
                maxValue = 23
                setFormatter { String.format("%02d", it) }
                setOnScrollListener { picker, scrollState -> if(scrollState == SCROLL_STATE_IDLE) { onTimeSettled() } }
            }
            with(numPickerMinutes){
                maxValue = 59
                setFormatter { String.format("%02d", it) }
                setOnScrollListener { picker, scrollState -> if(scrollState == SCROLL_STATE_IDLE) { onTimeSettled() } }
            }
            with(numPickerSeconds){
                maxValue = 59
                setFormatter { String.format("%02d", it) }
                setOnScrollListener { picker, scrollState -> if(scrollState == SCROLL_STATE_IDLE) { onTimeSettled() } }
            }

            reRunTIET.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if(v.text.isNullOrEmpty()) v.text = "0"
                    v.clearFocus()
                    hideKeyboard()
                }
                return@setOnEditorActionListener true
            }

            startFAB.setOnClickListener {
                val action = TimerInitFragmentDirections.actionTimerSettingsFragmentToTimerFragment(
                    numPickerHours.value,
                    numPickerMinutes.value,
                    numPickerSeconds.value,
                    reRunTIET.text.toString().toInt()
                )
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(TimerInitFragmentDirections.actionTimerSettingsFragmentToSettingsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun onTimeSettled() {
        with(binding){
            if (numPickerHours.value > 0 || numPickerMinutes.value > 0 || numPickerSeconds.value > 0){
                startFAB.visibility = View.VISIBLE
            }else{
                startFAB.visibility = View.GONE
            }
        }
    }

}