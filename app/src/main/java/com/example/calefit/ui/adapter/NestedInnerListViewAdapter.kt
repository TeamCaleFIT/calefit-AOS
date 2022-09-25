package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseList
import com.example.calefit.databinding.ItemExerciseCycleSetupBinding
import com.example.calefit.ui.common.InputCategory
import com.example.calefit.data.UserRecyclerviewClick

class NestedInnerListViewAdapter(
    private val userClickPosition: UserRecyclerviewClick,
    private val userSelect: (UserRecyclerviewClick) -> Unit,
    private val showBottomSheet: (InputCategory) -> Unit,
) : ListAdapter<ExerciseList.Sets, NestedInnerListViewAdapter.NestedInnerListViewHolder>(
    AsyncDifferConfig.Builder(ItemDiffUtil).build()
) {

    class NestedInnerListViewHolder(
        private val binding: ItemExerciseCycleSetupBinding,
        private val userClickPosition: UserRecyclerviewClick,
        private val userSelect: (UserRecyclerviewClick) -> Unit,
        private val showBottomSheet: (InputCategory) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseList.Sets) {
            binding.item = item
            observeEditText()
        }

        private fun observeEditText() {
            binding.tvExercisePlanCycleCount.setOnClickListener {
                val userClick = userClickPosition.copy(
                    innerPosition = adapterPosition,
                    category = InputCategory.CYCLE
                )
                userSelect(userClick)
                showBottomSheet(InputCategory.CYCLE)
            }

            binding.tvExercisePlanCycleWeight.setOnClickListener {
                val userClick = userClickPosition.copy(
                    innerPosition = adapterPosition,
                    category = InputCategory.WEIGHT
                )
                userSelect(userClick)
                showBottomSheet(InputCategory.WEIGHT)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedInnerListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NestedInnerListViewHolder(
            ItemExerciseCycleSetupBinding.inflate(inflater, parent, false),
            userClickPosition = userClickPosition,
            userSelect = userSelect,
            showBottomSheet = showBottomSheet
        )
    }

    override fun onBindViewHolder(holder: NestedInnerListViewHolder, position: Int) {
        holder.bind(currentList[position])
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
