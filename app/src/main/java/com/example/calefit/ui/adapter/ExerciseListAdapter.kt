package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseList
import com.example.calefit.databinding.ItemTodayExerciseBinding

class ExerciseListAdapter :
    ListAdapter<ExerciseList.Exercise, ExerciseListAdapter.ExerciseListViewHolder>(
        AsyncDifferConfig.Builder(ItemDiffUtil).build()
    ) {

    class ExerciseListViewHolder(
        private val binding: ItemTodayExerciseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseList.Exercise) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ExerciseListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseListViewHolder(
            ItemTodayExerciseBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ExerciseListViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseList.Exercise>() {
        override fun areItemsTheSame(
            oldItem: ExerciseList.Exercise,
            newItem: ExerciseList.Exercise,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ExerciseList.Exercise,
            newItem: ExerciseList.Exercise,
        ) = oldItem == newItem
    }
}