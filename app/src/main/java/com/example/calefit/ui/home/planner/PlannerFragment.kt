package com.example.calefit.ui.home.planner

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.databinding.FragmentPlannerBinding
import com.example.calefit.ui.adapter.NestedOuterListViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class PlannerFragment : Fragment() {

    private var binding by autoCleared<FragmentPlannerBinding>()

    private val viewModel: PlannerViewModel by viewModels()

    private val adapter by lazy {
        NestedOuterListViewAdapter(
            { position -> viewModel.addAdditionalCycle(position) },
            { position -> viewModel.removeCycle(position) },
            { position -> viewModel.removeExercise(position) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_planner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.rvExerciseListToday.adapter = adapter
        binding.rvExerciseListToday.itemAnimator = null

        selectExercise(navController)
        observeResult(navController)
        observeData()
    }

    private fun selectExercise(navController: NavController) {
        binding.btnExercisePlan.setOnClickListener {
            navController.navigate(R.id.action_plannerFragment_to_exerciseSelectFragment)
        }
    }

    private fun observeResult(navController: NavController) {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<List<ExerciseSelection>>(
                "key", listOf()
            )?.collect {
                viewModel.addAdditionalExercise(it)
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.exercisePlan.collect { exerciseList ->
                adapter.submitList(exerciseList.list)
                binding.hasPlan = exerciseList.list.isNotEmpty()
            }
        }
    }
}
