package com.example.calefit.ui.home.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentTemplateNameInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateNameInputDialogFragment : DialogFragment() {

    private var binding by autoCleared<FragmentTemplateNameInputBinding>()

    private var templateName: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_template_name_input,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonOperations()
        observeTemplateName()
    }

    private fun setButtonOperations() {
        with(binding) {
            btnCancelTemplateName.setOnClickListener {
                dismiss()
            }
            btnSaveTemplateName.setOnClickListener {
                setFragmentResult("request_key2", bundleOf("templateName" to templateName))
                dismiss()
            }
        }
    }

    private fun observeTemplateName() {
        binding.etTemplateTitle.addTextChangedListener { text ->
            templateName = text?.toString()
        }
    }
}