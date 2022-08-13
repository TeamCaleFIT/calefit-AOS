package com.example.calefit.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class AutoClearRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {

    init {
        addOnAttachStateChangeListener()
    }

    private fun addOnAttachStateChangeListener() {
        this.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
            }

            override fun onViewDetachedFromWindow(v: View?) {
                this@AutoClearRecyclerView.adapter = null
            }
        })
    }
}