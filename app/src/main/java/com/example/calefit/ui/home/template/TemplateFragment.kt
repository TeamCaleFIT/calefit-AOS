package com.example.calefit.ui.home.template

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
import com.example.calefit.databinding.FragmentTemplateBinding
import com.example.calefit.ui.adapter.ExerciseTemplateAdapter
import com.example.calefit.ui.decoration.NestedRecyclerDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateFragment : Fragment() {

    private var binding by autoCleared<FragmentTemplateBinding>()

    private val viewModel: TemplateViewModel by viewModels()

    private lateinit var templateAdapter: ExerciseTemplateAdapter

    private val recyclerDecoration by lazy {
        NestedRecyclerDecoration(NestedRecyclerDecoration.DEFAULT_INNER_RECYCLER_VIEW_ITEM_PADDING)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        templateAdapter = ExerciseTemplateAdapter(
            context = context,
            navController = findNavController()
        ) { position -> viewModel.selectTemplate(position) }

        binding.rvExerciseTemplate.apply {
            adapter = templateAdapter
            addItemDecoration(recyclerDecoration)
        }

        observeData()
        selectExerciseTemplateToPlanning()
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.templateSummaryList.collect {
                templateAdapter.submitList(it)
            }
        }
    }

    private fun selectExerciseTemplateToPlanning() {
        binding.btnSelectExerciseTemplate.setOnClickListener {
            val data = TemplateFragmentDirections.actionTemplateFragmentToPlannerFragment(
                viewModel.getSelectedDate()
            )
            findNavController().navigate(data)
        }
    }
}