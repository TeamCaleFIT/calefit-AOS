package com.example.calefit.ui.home.template

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentTemplateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateFragment : Fragment() {

    private var binding by autoCleared<FragmentTemplateBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false)
        return binding.root
    }
}