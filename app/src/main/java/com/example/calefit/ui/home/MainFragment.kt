package com.example.calefit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.databinding.FragmentMainBinding
import com.example.calefit.ui.adapter.ExerciseListAdapter
import com.example.calefit.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding by autoCleared<FragmentMainBinding>()

    private val viewModel: MainViewModel by viewModels()

    private val exerciseAdapter by lazy {
        ExerciseListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvExerciseDetail.adapter = exerciseAdapter
        binding.rvExerciseDetail.itemAnimator = null

        getDate()
        observeData()
    }

    private fun getDate() {
        binding.cvCustom.getClickedDate()?.let { flow ->
            viewLifecycleOwner.repeatOnLifecycleExtension {
                flow.collect {
                    viewModel.setDate(it)
                }
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.exerciseList.collect {
                if (it[0].name.isEmpty()) {
                    binding.hasSchedule = false
                } else {
                    binding.hasSchedule = true
                    exerciseAdapter.submitList(it)
                }
            }
        }
    }
}