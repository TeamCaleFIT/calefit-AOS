package com.example.calefit.ui.home.planner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.databinding.FragmentPlannerBinding
import com.example.calefit.ui.adapter.NestedOuterListViewAdapter
import com.example.calefit.ui.home.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PlannerFragment : Fragment() {

    private var binding by autoCleared<FragmentPlannerBinding>()

    private val viewModel: PlannerViewModel by viewModels()

    private val args: PlannerFragmentArgs by navArgs<PlannerFragmentArgs>()

    private val plannerAdapter by lazy {
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
        args.item?.let { viewModel.setExerciseList(it) }
        val navController = findNavController()

        binding.rvExerciseListToday.apply {
            adapter = plannerAdapter
            itemAnimator = null
        }

        selectExercise(navController)
        observeSelectionFragmentResult(navController)
        observeData()
    }

    private fun selectExercise(navController: NavController) {
        binding.btnExercisePlan.setOnClickListener {
            navController.navigate(R.id.action_plannerFragment_to_exerciseSelectFragment)
        }
    }

    private fun observeSelectionFragmentResult(navController: NavController) {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<List<ExerciseSelection>>(
                "key", listOf()
            )?.onEach {
                viewModel.addAdditionalExercise(it)
            }?.onCompletion {
                Log.d("PlannerFragment", "done : for the flow")
            }?.collect()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.exercisePlan.collect { exerciseList ->
                plannerAdapter.submitList(exerciseList.list)
                binding.hasPlan = exerciseList.list.isNotEmpty()
            }
        }
    }
}
