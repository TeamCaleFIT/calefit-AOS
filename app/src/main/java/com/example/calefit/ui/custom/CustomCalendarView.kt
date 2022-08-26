package com.example.calefit.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.calefit.R
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.databinding.CustomCalendarViewBinding
import com.example.calefit.ui.adapter.CalendarAdapter
import kotlinx.coroutines.flow.Flow

class CustomCalendarView(
    context: Context,
    attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {

    private var binding: CustomCalendarViewBinding

    private lateinit var adapter: CalendarAdapter

    private val viewModel by lazy {
        findViewTreeViewModelStoreOwner()?.let {
            ViewModelProvider(it)[CustomCalendarViewModel::class.java]
        }
    }

    init {
        val inflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.custom_calendar_view, this, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.viewModel = viewModel
        setAdapter()
        observeDataFromViewModel()
    }

    fun getClickedDate(): Flow<String>? = viewModel?.date

    private fun setAdapter() {
        adapter = CalendarAdapter{ position ->
            viewModel?.changeDateBackground(position)
        }
        binding.rvCustomCalendarView.adapter = adapter
    }

    private fun observeDataFromViewModel() {
        findViewTreeLifecycleOwner()?.repeatOnLifecycleExtension {
            viewModel?.month?.collect {
                adapter.submitList(it)
            }
        }
        findViewTreeLifecycleOwner()?.repeatOnLifecycleExtension {
            viewModel?.monthName?.collect {
                binding.tvDateDisplayDate.text = it
            }
        }
    }
}
