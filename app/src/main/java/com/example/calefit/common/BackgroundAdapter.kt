package com.example.calefit.common

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.calefit.R

@BindingAdapter("iconBackground")
fun setDateIconBackground(view: View, isToday: Boolean) {
    if (isToday) {
        val drawable = ContextCompat.getDrawable(view.context, R.drawable.today_background)
        view.background = drawable
    }
}