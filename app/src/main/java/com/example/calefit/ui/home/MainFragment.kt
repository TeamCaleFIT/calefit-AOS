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
import com.example.calefit.ui.adapter.CalendarAdapter
import com.example.calefit.ui.adapter.ExerciseListAdapter
import com.example.calefit.ui.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding by autoCleared<FragmentMainBinding>()

    private val viewModel: MainFragmentViewModel by viewModels()

    private val calendarAdapter by lazy {
        CalendarAdapter { position ->
            viewModel.changeDateBackground(position)
        }
    }

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

        binding.rvCalendar.adapter = calendarAdapter
        binding.rvExerciseDetail.adapter = exerciseAdapter

        observeCalendar()
        changeCalendar()
    }

    private fun observeCalendar() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.month.collect {
                calendarAdapter.submitList(it)
            }
        }
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.monthName.collect {
                binding.tvDateDisplayDate.text = it
            }
        }
    }

    private fun changeCalendar() {
        with(binding) {
            btnCalendarPrev.setOnClickListener {
                viewModel.getPreviousMonth()
            }
            btnCalendarNext.setOnClickListener {
                viewModel.getNextMonth()
            }
        }
    }
}