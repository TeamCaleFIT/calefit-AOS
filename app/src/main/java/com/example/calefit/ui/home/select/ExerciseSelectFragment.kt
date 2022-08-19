package com.example.calefit.ui.home.select

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentExerciseSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseSelectFragment : Fragment() {

    private var binding by autoCleared<FragmentExerciseSelectBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_select, container, false)
        return binding.root
    }
}