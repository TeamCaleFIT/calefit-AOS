package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseList
import com.example.calefit.databinding.ItemExercisePlanBinding

class NestedOuterListViewAdapter(

) : ListAdapter<ExerciseList.Exercise, NestedOuterListViewAdapter.NestedOuterListViewViewHolder>(
    AsyncDifferConfig.Builder(ItemDiffUtil).build()
) {

    class NestedOuterListViewViewHolder(
        private val binding: ItemExercisePlanBinding,
        private val innerAdapter: NestedInnerListViewAdapter = NestedInnerListViewAdapter()
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseList.Exercise) {
            binding.item = item
            binding.rvExerciseCycle.adapter = innerAdapter
            innerAdapter.submitList(item.cycleList)
        }

        fun observeInnerRecyclerView() {
            with(binding) {
                btnCycleAdd.setOnClickListener {

                }
                btnCycleDelete.setOnClickListener {

                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NestedOuterListViewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NestedOuterListViewViewHolder(
            ItemExercisePlanBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NestedOuterListViewViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.observeInnerRecyclerView()
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseList.Exercise>() {
        override fun areItemsTheSame(
            oldItem: ExerciseList.Exercise,
            newItem: ExerciseList.Exercise
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExerciseList.Exercise,
            newItem: ExerciseList.Exercise
        ): Boolean {
            return oldItem == newItem
        }
    }
}
