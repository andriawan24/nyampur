package id.nisyafawwaz.nyampur.android.ui.camera.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CustomOverlayView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : View(context, attrs, defStyleAttr) {
        private val borderPaint =
            Paint().apply {
                color = 0xFFFFFFFF.toInt()
                style = Paint.Style.STROKE
                strokeWidth = 3f
                isAntiAlias = true
            }

        private val overlayPaint =
            Paint().apply {
                color = 0x99000000.toInt()
                style = Paint.Style.FILL
                isAntiAlias = true
            }

        private val transparentPaint =
            Paint().apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                isAntiAlias = true
            }

        private val middleRect = RectF()
        private val strokeRect = RectF()
        private val roundedPath = Path()
        private val strokePath = Path()

        var centerSquareSizeRatio: Float = 0.8f // Ratio of screen width/height (50% of screen)
            set(value) {
                field = value
                invalidate() // Redraw the view
            }

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val centerSquareSize = (width.coerceAtMost(height) * centerSquareSizeRatio).toInt()
            val left = (width - centerSquareSize) / 2
            val top = (height - centerSquareSize) / 2
            val right = left + centerSquareSize
            val bottom = top + centerSquareSize

            // Draw black overlay
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)

            // Draw transparent box
            roundedPath.reset()
            middleRect.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
            roundedPath.addRoundRect(middleRect, floatArrayOf(20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f), Path.Direction.CW)
            canvas.drawPath(roundedPath, transparentPaint)

            // Draw border box
            strokePath.reset()
            strokeRect.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
            strokePath.addRoundRect(strokeRect, floatArrayOf(20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f), Path.Direction.CW)
            canvas.drawPath(strokePath, borderPaint)
        }
    }
