package com.example.calefit.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.R
import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.databinding.ItemExerciseTemplateBinding
import com.example.calefit.ui.home.template.TemplateFragmentDirections
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.zip.Inflater

class ExerciseTemplateAdapter(
    private val context: Context?,
    private val navController: NavController,
    private val selectTemplate: (Int) -> Unit,
) :
    ListAdapter<ExerciseTemplateSummary, ExerciseTemplateAdapter.ExerciseTemplateViewHolder>(
        ItemDiffUtil
    ) {

    class ExerciseTemplateViewHolder(
        private val binding: ItemExerciseTemplateBinding,
        private val context: Context?,
        private val navController: NavController,
        private val selectTemplate: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseTemplateSummary) {
            binding.item = item
            goToExerciseDetailFragment(item)
            getClickedTemplate()
            changeBackground(item.isClicked, context)
        }

        private fun getClickedTemplate() {
            binding.clTemplate.setOnClickListener {
                Log.d("TemplateAdapter", "clicked")
                selectTemplate(adapterPosition)
            }
        }

        private fun goToExerciseDetailFragment(item: ExerciseTemplateSummary) {
            binding.btnExerciseListDetail.setOnClickListener {
                val data =
                    TemplateFragmentDirections.actionTemplateFragmentToExerciseDetailFragment(
                        item.templateTitle
                    )
                navController.navigate(data)
            }
        }

        private fun changeBackground(isClicked: Boolean, context: Context?) {
            if (isClicked) {
                context?.let {
                    binding.clTemplate.background =
                        ContextCompat.getDrawable(context, R.drawable.template_background_blue)
                }
            } else {
                context?.let {
                    binding.clTemplate.background =
                        ContextCompat.getDrawable(context, R.drawable.template_background)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseTemplateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseTemplateViewHolder(
            ItemExerciseTemplateBinding.inflate(inflater, parent, false),
            navController = navController,
            selectTemplate = selectTemplate,
            context = context
        )
    }

    override fun onBindViewHolder(holder: ExerciseTemplateViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseTemplateSummary>() {
        override fun areItemsTheSame(
            oldItem: ExerciseTemplateSummary,
            newItem: ExerciseTemplateSummary
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExerciseTemplateSummary,
            newItem: ExerciseTemplateSummary
        ): Boolean {
            return oldItem == newItem
        }
    }
}