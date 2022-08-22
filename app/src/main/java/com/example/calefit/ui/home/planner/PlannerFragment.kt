package com.example.calefit.ui.home.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.databinding.FragmentPlannerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class PlannerFragment : Fragment() {

    private var binding by autoCleared<FragmentPlannerBinding>()

    private val viewModel: PlannerViewModel by viewModels()

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

        selectExercise()
        observeResult()
    }

    private fun selectExercise() {
        binding.btnExercisePlan.setOnClickListener {
            findNavController().navigate(R.id.action_plannerFragment_to_exerciseSelectFragment)
        }
    }

    private fun observeResult() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow<List<String>>(
                "key", listOf()
            )?.collect {
                viewModel.getExerciseName(it)
            }
        }
    }
}