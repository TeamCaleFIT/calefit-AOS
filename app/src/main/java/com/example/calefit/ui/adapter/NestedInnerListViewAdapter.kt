package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseList
import com.example.calefit.databinding.ItemExerciseCycleSetupBinding

class NestedInnerListViewAdapter(

) : ListAdapter<ExerciseList.Cycle, NestedInnerListViewAdapter.NestedInnerListViewHolder>(
    AsyncDifferConfig.Builder(ItemDiffUtil).build()
) {

    class NestedInnerListViewHolder(
        private val binding: ItemExerciseCycleSetupBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseList.Cycle) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedInnerListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NestedInnerListViewHolder(
            ItemExerciseCycleSetupBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NestedInnerListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseList.Cycle>() {
        override fun areItemsTheSame(
            oldItem: ExerciseList.Cycle,
            newItem: ExerciseList.Cycle
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExerciseList.Cycle,
            newItem: ExerciseList.Cycle
        ): Boolean {
            return oldItem == newItem
        }
    }
}
