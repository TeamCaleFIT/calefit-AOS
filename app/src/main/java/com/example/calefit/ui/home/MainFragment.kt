package com.example.calefit.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calefit.R
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.databinding.FragmentMainBinding
import com.example.calefit.ui.calendar.CalendarAdapter
import com.example.calefit.ui.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by viewModels()

    private val manager by lazy {
        GridLayoutManager(context, 7)
    }

    private val adapter by lazy {
        CalendarAdapter { position ->
            viewModel.changeBackgroundDate(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCalendar.adapter = adapter
        binding.rvCalendar.layoutManager = manager
//        binding.rvCalendar.itemAnimator = null

        observeCalendar()
        changeCalendar()
    }

    private fun observeCalendar() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.month.collect {
                Log.d("MainFragment", it.toString())
                adapter.submitList(it)
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