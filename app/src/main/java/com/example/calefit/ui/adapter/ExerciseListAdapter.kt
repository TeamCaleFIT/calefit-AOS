package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.Exercise
import com.example.calefit.databinding.ItemTodayExerciseBinding

class ExerciseListAdapter :
    ListAdapter<Exercise, ExerciseListAdapter.ExerciseListViewHolder>(ItemDiffUtil) {

    class ExerciseListViewHolder(
        private val binding: ItemTodayExerciseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Exercise) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseListViewHolder(
            ItemTodayExerciseBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(
            oldItem: Exercise,
            newItem: Exercise,
        ) = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: Exercise,
            newItem: Exercise,
        ) = oldItem == newItem
    }
}