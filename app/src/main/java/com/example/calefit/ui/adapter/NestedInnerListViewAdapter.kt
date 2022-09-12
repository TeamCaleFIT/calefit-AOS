package com.example.calefit.ui.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseList
import com.example.calefit.databinding.ItemExerciseCycleSetupBinding
import com.example.calefit.ui.common.InputCategory

class NestedInnerListViewAdapter(
    private val userInput: (Int, Int, String, InputCategory) -> Unit,
    private val outerPosition: Int,
) : ListAdapter<ExerciseList.Sets, NestedInnerListViewAdapter.NestedInnerListViewHolder>(
    AsyncDifferConfig.Builder(ItemDiffUtil).build()
) {

    class NestedInnerListViewHolder(
        private val binding: ItemExerciseCycleSetupBinding,
        private val userInput: (Int, Int, String, InputCategory) -> Unit,
        private val outerPosition: Int
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseList.Sets) {
            binding.item = item
        }

        fun observeEditText() {
            binding.etExercisePlanCycleCount.addTextChangedListener {
                Log.d("InnerAdapter", "cycle ${it?.toString() ?: ""}")
                userInput(
                    outerPosition,
                    adapterPosition,
                    it.toString(),
                    InputCategory.CYCLE
                )
            }
            binding.etExercisePlanCycleWeight.addTextChangedListener {
                Log.d("InnerAdapter", "weight ${it?.toString() ?: ""}")
                userInput(
                    outerPosition,
                    adapterPosition,
                    it.toString(),
                    InputCategory.WEIGHT
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedInnerListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NestedInnerListViewHolder(
            ItemExerciseCycleSetupBinding.inflate(inflater, parent, false),
            userInput = userInput,
            outerPosition = outerPosition
        )
    }

    override fun onBindViewHolder(holder: NestedInnerListViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.observeEditText()
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseList.Sets>() {
        override fun areItemsTheSame(
            oldItem: ExerciseList.Sets,
            newItem: ExerciseList.Sets
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExerciseList.Sets,
            newItem: ExerciseList.Sets
        ): Boolean {
            return oldItem == newItem
        }
    }
}
