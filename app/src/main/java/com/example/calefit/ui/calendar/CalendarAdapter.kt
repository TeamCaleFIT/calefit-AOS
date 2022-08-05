package com.example.calefit.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calefit.data.CalendarDate
import com.example.calefit.databinding.ItemDayBinding
import com.example.calefit.databinding.ItemDayHeaderBinding

class CalendarAdapter(
    private val clickEvent: (Int) -> Unit,
) : ListAdapter<CalendarDate, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(ItemDiffUtil).build()
) {

    class CalendarDayViewHolder(
        val binding: ItemDayBinding,
        val clickEvent: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarDate.ItemDays) {
            binding.item = item

            binding.clDay.setOnClickListener {
                clickEvent(adapterPosition)
            }
        }
    }

    class CalendarDaysHeaderViewHolder(
        val binding: ItemDayHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarDate.ItemHeader) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            DAY -> CalendarDayViewHolder(
                ItemDayBinding.inflate(inflater, parent, false),
                clickEvent
            )
            HEADER -> CalendarDaysHeaderViewHolder(
                ItemDayHeaderBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        when (holder) {
            is CalendarDayViewHolder -> holder.bind(item as CalendarDate.ItemDays)
            is CalendarDaysHeaderViewHolder -> holder.bind(item as CalendarDate.ItemHeader)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is CalendarDate.ItemHeader -> HEADER
            is CalendarDate.ItemDays -> DAY
        }
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<CalendarDate>() {

        override fun areItemsTheSame(
            oldItem: CalendarDate,
            newItem: CalendarDate,
        ): Boolean {
            return if (oldItem is CalendarDate.ItemHeader && newItem is CalendarDate.ItemHeader) {
                oldItem.dateIndicator == newItem.dateIndicator
            } else if (oldItem is CalendarDate.ItemDays && newItem is CalendarDate.ItemDays) {
                oldItem.id == newItem.id
            } else {
                oldItem.hashCode() == newItem.hashCode()
            }
        }

        override fun areContentsTheSame(
            oldItem: CalendarDate,
            newItem: CalendarDate,
        ) = oldItem == newItem
    }

    companion object {
        const val HEADER = 0
        const val DAY = 1
    }
}