package net.yanzm.mlkitsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class GraphicOverlay(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val lock = Any()

    private val graphics = mutableSetOf<GraphicData>()

    var targetWidth = 0
    var targetHeight = 0

    fun clear() {
        synchronized(lock) {
            graphics.clear()
        }
        postInvalidate()
    }

    fun add(graphic: GraphicData) {
        synchronized(lock) {
            graphics.add(graphic)
        }
        postInvalidate()
    }

    fun remove(graphic: GraphicData) {
        synchronized(lock) {
            graphics.remove(graphic)
        }
        postInvalidate()
    }

    private val rect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val offsetX = (canvas.width - targetWidth) * 0.5f
        val offsetY = (canvas.height - targetHeight) * 0.5f

        synchronized(lock) {
            for (graphic in graphics) {
                rect.set(graphic.boundingBox)
                rect.offset(offsetX, offsetY)

                canvas.drawRect(rect, graphic.rectPaint)

                if (graphic.text.isNotEmpty()) {
                    canvas.drawText(graphic.text, rect.left, rect.bottom, graphic.textPaint)
                }
            }
        }
    }
}
