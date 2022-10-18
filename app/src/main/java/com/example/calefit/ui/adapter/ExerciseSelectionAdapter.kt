package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.databinding.ItemExerciseSelectBinding

class ExerciseSelectionAdapter(
    private val addExercise: (Int) -> Unit,
    private val removeExercise: (Int) -> Unit,
) :
    ListAdapter<ExerciseSelection, ExerciseSelectionAdapter.ExerciseSelectionViewHolder>(
        ItemDiffUtil
    ) {

    class ExerciseSelectionViewHolder(
        private val binding: ItemExerciseSelectBinding,
        private val addExercise: (Int) -> Unit,
        private val removeExercise: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseSelection) {
            binding.item = item
            observeCheckBox()
        }

        private fun observeCheckBox() {
            binding.cbSelectExercise.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addExercise(adapterPosition)
                } else {
                    removeExercise(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseSelectionViewHolder(
            ItemExerciseSelectBinding.inflate(inflater, parent, false),
            addExercise,
            removeExercise
        )
    }

    override fun onBindViewHolder(holder: ExerciseSelectionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseSelection>() {
        override fun areItemsTheSame(
            oldItem: ExerciseSelection,
            newItem: ExerciseSelection
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExerciseSelection,
            newItem: ExerciseSelection
        ): Boolean {
            return oldItem == newItem
        }
    }
}
