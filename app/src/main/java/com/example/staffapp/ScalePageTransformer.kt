package com.example.staffapp

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ScalePageTransformer : ViewPager2.PageTransformer {

    private val scaleFactor = 0.85f
    private val minAlpha = 0.6f

    override fun transformPage(page: View, position: Float) {
        page.apply {
            val scale = if (position < 0) {
                (1 - scaleFactor) * abs(position) + scaleFactor
            } else {
                (scaleFactor - 1) * abs(position) + 1
            }

            scaleX = scale
            scaleY = scale

            alpha = minAlpha + (scale - scaleFactor) / (1 - scaleFactor) * (1 - minAlpha)
        }
    }
}
