package com.example.calefit.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import com.example.calefit.R
import com.example.calefit.databinding.CustomButtonBinding

class CustomLoginButton(
    context: Context,
    attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {

    private var binding: CustomButtonBinding

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_button,
            this,
            true
        )
    }

    fun setButtonTitle(@StringRes text: Int) {
        binding.tvLoginButtonTitle.text = context.getString(text)
    }

    fun setButtonLogo(@DrawableRes logo: Int) {
        binding.ivOauthLogo.setImageResource(logo)
    }

    fun setButtonColor(@ColorRes colorId: Int) {
        val color = ContextCompat.getColor(context, colorId)
        val mode = BlendModeCompat.DST_OVER
        changeColor(color, mode)
    }

    private fun changeColor(color: Int, mode: BlendModeCompat) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.login_background)
            ?.let { DrawableCompat.wrap(it) }
        drawable?.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, mode)
        binding.customLayout.background = drawable
    }
}