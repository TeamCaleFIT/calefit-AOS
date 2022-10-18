package com.example.calefit.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentNumberPickBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NumberPickFragment : BottomSheetDialogFragment() {

    private var binding by autoCleared<FragmentNumberPickBinding>()

    private var selectedNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_number_pick, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getSerializable("type") as InputCategory

        setDefaultNumberPicker(category)
        observeComponents()
    }

    private fun setDefaultNumberPicker(category: InputCategory) {
        var minNumber = 0
        var maxNumber = 0
        when (category) {
            InputCategory.CYCLE -> {
                binding.category = "회"
                minNumber = MIN_NUMBER
                maxNumber = CYCLE_MAX_NUMBER
            }
            InputCategory.WEIGHT -> {
                binding.category = "kg"
                minNumber = MIN_NUMBER
                maxNumber = WEIGHT_MAX_NUMBER
            }
        }
        binding.npUserInput.apply {
            minValue = minNumber
            maxValue = maxNumber
        }
    }

    private fun observeComponents() {
        with(binding) {
            npUserInput.setOnValueChangedListener { _, _, newVal ->
                selectedNumber = newVal
            }
            btnSaveNumber.setOnClickListener {
                setFragmentResult(
                    "request_key",
                    bundleOf("number" to selectedNumber)
                )
                dismiss()
            }
        }
    }

    companion object {
        private const val MIN_NUMBER = 0
        private const val CYCLE_MAX_NUMBER = 100
        private const val WEIGHT_MAX_NUMBER = 500

        fun newInstance(category: InputCategory): NumberPickFragment {
            return NumberPickFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("type", category)
                }
            }
        }
    }
}