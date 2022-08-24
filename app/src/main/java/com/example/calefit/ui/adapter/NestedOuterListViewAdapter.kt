package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.calefit.data.ExerciseList
import com.example.calefit.databinding.ItemExercisePlanBinding

class NestedOuterListViewAdapter(
    private val addCycle: (Int) -> Boolean,
    private val removeCycle: (Int) -> Boolean,
    private val removeExercise: (Int) -> Unit,
) : ListAdapter<ExerciseList.Exercise, NestedOuterListViewAdapter.NestedOuterListViewViewHolder>(
    AsyncDifferConfig.Builder(ItemDiffUtil).build()
) {

    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    class NestedOuterListViewViewHolder(
        private val binding: ItemExercisePlanBinding,
        private val addCycle: (Int) -> Boolean,
        private val removeCycle: (Int) -> Boolean,
        private val removeExercise: (Int) -> Unit,
        private val innerLayoutManager: LinearLayoutManager,
        private val innerAdapter: NestedInnerListViewAdapter = NestedInnerListViewAdapter(),
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseList.Exercise) {
            binding.item = item
            binding.rvExerciseCycle.apply {
                adapter = innerAdapter
                layoutManager = innerLayoutManager
                setRecycledViewPool(viewPool)
            }
            innerAdapter.submitList(item.cycleList)
        }

        fun observeInnerRecyclerView() {
            with(binding) {
                btnCycleAdd.setOnClickListener {
                    btnCycleDelete.isEnabled = addCycle(adapterPosition)
                }
                btnCycleDelete.setOnClickListener {
                    btnCycleDelete.isEnabled = removeCycle(adapterPosition)
                }
                btnExerciseClose.setOnClickListener {
                    removeExercise(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NestedOuterListViewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        layoutManager.recycleChildrenOnDetach = true

        return NestedOuterListViewViewHolder(
            ItemExercisePlanBinding.inflate(inflater, parent, false),
            addCycle = addCycle,
            removeCycle = removeCycle,
            removeExercise = removeExercise,
            innerLayoutManager = layoutManager,
            viewPool = viewPool,
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
