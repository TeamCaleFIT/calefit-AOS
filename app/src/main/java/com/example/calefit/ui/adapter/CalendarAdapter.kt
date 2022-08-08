package com.example.calefit.ui.adapter

import android.os.Bundle
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

        fun update(bundle: Bundle) {
            if (bundle.containsKey(CLICK)) {
                val checked = bundle.getBoolean(CLICK)
                binding.isClicked = checked
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
            HEADER -> CalendarDaysHeaderViewHolder(
                ItemDayHeaderBinding.inflate(inflater, parent, false)
            )
            DAY -> CalendarDayViewHolder(
                ItemDayBinding.inflate(inflater, parent, false),
                clickEvent
            )
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        val item = getItem(position)
        when (holder) {
            is CalendarDayViewHolder -> {
                if (payloads.isEmpty() || payloads[0] !is Bundle) {
                    holder.bind(item as CalendarDate.ItemDays)
                } else {
                    val bundle = payloads[0] as Bundle
                    holder.update(bundle)
                }
            }

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
            return if (oldItem is CalendarDate.ItemHeader
                && newItem is CalendarDate.ItemHeader
            ) {
                oldItem.dateIndicator == newItem.dateIndicator
            } else if (oldItem is CalendarDate.ItemDays
                && newItem is CalendarDate.ItemDays
            ) {
                oldItem.id == newItem.id
            } else {
                oldItem.hashCode() == newItem.hashCode()
            }
        }

        override fun areContentsTheSame(
            oldItem: CalendarDate,
            newItem: CalendarDate,
        ) = oldItem == newItem

        override fun getChangePayload(
            oldItem: CalendarDate,
            newItem: CalendarDate,
        ): Any? {
            if (oldItem is CalendarDate.ItemDays
                && newItem is CalendarDate.ItemDays
            ) {
                if (oldItem.id == newItem.id) {
                    return if (oldItem.isClicked == newItem.isClicked) {
                        super.getChangePayload(oldItem, newItem)
                    } else {
                        val diff = Bundle()
                        diff.putBoolean(CLICK, newItem.isClicked)
                        diff
                    }
                }
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }

    companion object {
        const val HEADER = 0
        const val DAY = 1
        private const val CLICK = "done"
    }
}