package com.example.calefit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.ExerciseDailyDetail
import com.example.calefit.databinding.ItemTodayExerciseBinding

class ExerciseDailyDetailListAdapter :
    ListAdapter<ExerciseDailyDetail, ExerciseDailyDetailListAdapter.ExerciseListViewHolder>(
        AsyncDifferConfig.Builder(ItemDiffUtil).build()
    ) {

    class ExerciseListViewHolder(
        private val binding: ItemTodayExerciseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseDailyDetail) {
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

    private object ItemDiffUtil : DiffUtil.ItemCallback<ExerciseDailyDetail>() {
        override fun areItemsTheSame(
            oldItem: ExerciseDailyDetail,
            newItem: ExerciseDailyDetail,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ExerciseDailyDetail,
            newItem: ExerciseDailyDetail,
        ) = oldItem == newItem
    }
}
