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
import com.example.calefit.data.DataLoadInfo
import com.example.calefit.databinding.FragmentMainBinding
import com.example.calefit.ui.adapter.ExerciseDailyDetailListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine


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
            viewModel.exerciseMap.combine(viewModel.clickedDate) { map, date ->
                val exerciseList = map[date]
                if (exerciseList == null) {
                    binding.hasSchedule = false
                } else {
                    binding.hasSchedule = true
                    exerciseAdapter.submitList(exerciseList)
                }
            }.collect()
        }
    }

    private fun goToPlannerFragment(navController: NavController) {
        with(binding) {
            btnMakeExerciseList.setOnClickListener {
                viewLifecycleOwner.repeatOnLifecycleExtension {
                    viewModel.clickedDate.collect {
                        val date = MainFragmentDirections.actionMainFragmentToPlannerFragment(
                            DataLoadInfo(
                                initialClickedDate = it
                            )
                        )
                        navController.navigate(date)
                    }
                }
            }

            btnEditExercise.setOnClickListener {
                viewLifecycleOwner.repeatOnLifecycleExtension {
                    viewModel.clickedDate.collect {
                        val data = MainFragmentDirections.actionMainFragmentToPlannerFragment(
                            DataLoadInfo(
                                initialClickedDate = it
                            )
                        )
                        navController.navigate(data)
                    }
                }
            }
        }
    }
}
