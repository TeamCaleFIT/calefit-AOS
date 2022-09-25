package com.example.calefit.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.R
import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.databinding.ItemExerciseTemplateBinding
import com.example.calefit.ui.home.template.TemplateFragmentDirections
import java.util.zip.Inflater

class ExerciseTemplateAdapter(
    private val navController: NavController,
    private val selectTemplate: (Int) -> Unit
) :
    ListAdapter<ExerciseTemplateSummary, ExerciseTemplateAdapter.ExerciseTemplateViewHolder>(
        ItemDiffUtil
    ) {

    class ExerciseTemplateViewHolder(
        private val binding: ItemExerciseTemplateBinding,
        private val navController: NavController,
        private val selectTemplate: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseTemplateSummary) {
            binding.item = item
            goToExerciseDetailFragment(item)
            getClickedTemplate()
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
                        item.exerciseDate
                    )
                navController.navigate(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseTemplateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseTemplateViewHolder(
            ItemExerciseTemplateBinding.inflate(inflater, parent, false),
            navController = navController,
            selectTemplate = selectTemplate
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