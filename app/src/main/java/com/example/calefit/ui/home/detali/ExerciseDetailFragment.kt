package com.example.calefit.ui.home.detali

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.calefit.R
import com.example.calefit.databinding.FragmentExerciseDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailFragment : Fragment() {

    private lateinit var binding: FragmentExerciseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_detail, container, false)
        return binding.root
    }
}