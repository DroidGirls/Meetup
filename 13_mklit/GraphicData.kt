package net.yanzm.mlkitsample

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect

data class GraphicData internal constructor(
        val text: String,
        val boundingBox: Rect,
        private val resources: Resources,
        private val textColor: Int
) {

    val rectPaint = Paint().apply {
        color = textColor
        style = Paint.Style.STROKE
        strokeWidth = 2 * resources.displayMetrics.density
    }

    val textPaint = Paint().apply {
        color = textColor
        textSize = 20 * resources.displayMetrics.density
    }
}
