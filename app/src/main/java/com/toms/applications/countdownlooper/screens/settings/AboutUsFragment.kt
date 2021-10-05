
package com.toms.applications.countdownlooper.screens.settings

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.toms.applications.countdownlooper.R
import com.toms.applications.countdownlooper.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false)

        binding.playstore.movementMethod = LinkMovementMethod.getInstance()
        binding.playstore.setLinkTextColor(Color.BLUE)

        return binding.root
    }

}