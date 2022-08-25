package com.example.calefit.ui.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.databinding.FragmentMainBinding
import com.example.calefit.ui.adapter.ExerciseDailyDetailListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding by autoCleared<FragmentMainBinding>()

    private val viewModel: MainViewModel by viewModels()

    private val exerciseAdapter by lazy {
        ExerciseDailyDetailListAdapter()
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
        val navController = findNavController()

        binding.rvExerciseDetail.adapter = exerciseAdapter
        binding.rvExerciseDetail.itemAnimator = null

        getDate()
        observeData()
        goToPlannerFragment(navController)
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
            viewModel.exerciseMap.collect { map ->
                viewModel.clickedDate.collect { date ->
                    val exerciseList = map[date]
                    if (exerciseList == null) {
                        binding.hasSchedule = false
                    } else {
                        binding.hasSchedule = true
                        exerciseAdapter.submitList(exerciseList)
                    }
                }
            }
        }
    }

    private fun goToPlannerFragment(navController: NavController) {
        binding.btnMakeExerciseList.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_plannerFragment)
        }
    }
}