package com.example.skptemp.common.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.skptemp.R
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX
import com.example.skptemp.common.util.ViewUtil.setHeightPx
import com.example.skptemp.databinding.BukiProgressBarBinding

class BukiProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private val binding by lazy {
        BukiProgressBarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    // styleAttr, styleRes, defStyleAttr, defStyleRes
    private val mTypedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BukiProgressBar, 0, 0
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        initializeViewAttrs(mTypedArray)
    }

    private fun initializeViewAttrs(typedArray: TypedArray) {
        val radius = typedArray.getInt(R.styleable.BukiProgressBar_radius, 12)
        draw(radius.toFloat().convertDPtoPX(context))
    }

    private fun draw(radiusPx: Float) {
        val background = GradientDrawable().apply {
            cornerRadius = radiusPx
            setColor(ColorUtil.getColor(context, BACKGROUND_COLOR))
        }

        val progress = GradientDrawable().apply {
            cornerRadius = radiusPx
            setColor(ColorUtil.getColor(context, PROGRESS_COLOR))
        }

        val scaleProgress = ScaleDrawable(
            progress, Gravity.START, 1f, 0.1f
        )

        val drawables = arrayOf(background, scaleProgress)
        val layers = LayerDrawable(drawables).apply {
            setId(0, android.R.id.background)
            setId(1, android.R.id.progress)
        }

        with(binding.progressBar) {
            progressDrawable = layers
            setHeightPx(radiusPx.toInt())
        }
    }

    fun progress(progress: Int) {
        binding.progressBar.progress = progress
    }

    companion object {
        private val BACKGROUND_COLOR = R.color.gray_200
        private val PROGRESS_COLOR = R.color.blue
    }

}