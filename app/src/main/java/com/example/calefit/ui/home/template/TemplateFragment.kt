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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateFragment : Fragment() {

    private var binding by autoCleared<FragmentTemplateBinding>()

    private val viewModel: TemplateViewModel by viewModels()

    private val templateAdapter by lazy {
        ExerciseTemplateAdapter(
            navController = findNavController()
        ) { position -> viewModel.selectTemplate(position) }
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

        binding.rvExerciseTemplate.apply {
            adapter = templateAdapter
        }

        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.templateSummaryList.collect {
                templateAdapter.submitList(it)
            }
        }
    }
}