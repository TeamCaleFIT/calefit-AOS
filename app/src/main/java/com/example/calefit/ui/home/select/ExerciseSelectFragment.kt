package com.example.calefit.ui.home.select

import android.os.Bundle
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
import com.example.calefit.databinding.FragmentExerciseSelectBinding
import com.example.calefit.ui.adapter.ExerciseSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseSelectFragment : Fragment() {

    private var binding by autoCleared<FragmentExerciseSelectBinding>()

    private val viewModel: ExerciseSelectViewModel by viewModels()

    private val adapter by lazy {
        ExerciseSelectionAdapter(
            { position -> viewModel.addExercise(position) },
            { position -> viewModel.removeExercise(position) },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_select, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.rvExerciseList.adapter = adapter

        setResultToPlannerFragment(navController)
        observeExerciseList()
    }

    private fun observeExerciseList() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.exerciseDataList.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun setResultToPlannerFragment(navController: NavController) {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.selectedExerciseList.collect { list ->
                binding.btnExerciseSelect.setOnClickListener {
                    navController.previousBackStackEntry?.savedStateHandle?.set("key", list)
                    navController.popBackStack()
                }
            }
        }
    }
}