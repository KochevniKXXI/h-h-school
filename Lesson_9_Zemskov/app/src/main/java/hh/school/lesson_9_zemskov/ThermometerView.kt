package hh.school.lesson_9_zemskov

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class ThermometerView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {

    var temperature = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleBarRectangle = RectF()
    private val scaleBarPaint = Paint().apply { color = Color.GRAY }
    private val scaleBarPadding =
        resources.getDimension(R.dimen.thermometer_view_scale_bar_padding_default)
    private val levelRectangle = RectF()

    private val mercuryPaint = Paint()
    private val mercuryRectangle = RectF()

    private val capillaryRectangle = RectF()
    private val glassPaint = Paint()
    private val glassTopPaint = Paint()
    private val glassRoundPaint = Paint()
    private val glassEdgeRoundPaint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        levelRectangle.set(
            3 * scaleBarPadding,
            scaleBarPadding,
            width - 3 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 5 + scaleBarPadding + temperature / 90f
        )
        scaleBarRectangle.set(
            2 * scaleBarPadding,
            0f,
            width - 2 * scaleBarPadding,
            height - width / 2f
        )
        mercuryRectangle.set(
            3 * scaleBarPadding,
            scaleBarPadding,
            width - 3 * scaleBarPadding,
            height - width / 2f
        )
        mercuryPaint.shader = LinearGradient(
            0f,
            2f * scaleBarPadding,
            0f,
            height - 2f * scaleBarPadding,
            intArrayOf(Color.RED, Color.BLUE, Color.CYAN),
            floatArrayOf(0.35f, 0.6f, 1f),
            Shader.TileMode.MIRROR
        )
        capillaryRectangle.set(
            2.5f * scaleBarPadding,
            scaleBarPadding / 2,
            width - 2.5f * scaleBarPadding,
            height - width / 2f
        )
        glassPaint.shader = LinearGradient(
            2.5f * scaleBarPadding,
            0f,
            width - 2.5f * scaleBarPadding,
            0f,
            intArrayOf(
                Color.WHITE, Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT
            ),
            floatArrayOf(0f, 0.5f, 0.75f, 1f),
            Shader.TileMode.MIRROR
        )
        glassTopPaint.shader = RadialGradient(
            (width - 5 * scaleBarPadding) * 0.75f + 2.5f * scaleBarPadding,
            scaleBarPadding,
            width / 4f,
            Color.WHITE,
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        glassRoundPaint.shader = RadialGradient(
            width / 2.0f + scaleBarPadding,
            height - width / 2.0f - scaleBarPadding,
            width / 4.0f,
            Color.WHITE,
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        glassEdgeRoundPaint.shader = RadialGradient(
            width / 2.0f,
            height - width / 2.0f,
            width / 2.0f,
            Color.TRANSPARENT,
            Color.WHITE,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawScaleBar()
        canvas.drawCapillaryWithMercury()
        canvas.drawFlaskWithMercury()
    }

    private fun Canvas.drawScaleBar() {
        drawCircle(
            width / 2f,
            height - width / 2f,
            width / 2f,
            scaleBarPaint
        )
        drawRoundRect(
            scaleBarRectangle,
            width - 4 * scaleBarPadding,
            width - 4 * scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "40",
            0f,
            (height - width - scaleBarPadding / 2) * 0.1f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "30",
            0f,
            (height - width - scaleBarPadding / 2) * 0.21f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 2 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 2 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "20",
            0f,
            (height - width - scaleBarPadding / 2) * 0.32f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 3 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 3 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "10",
            0f,
            (height - width - scaleBarPadding / 2) * 0.43f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 4 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 4 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "0",
            0f,
            (height - width - scaleBarPadding / 2) * 0.54f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 5 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 5 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "-10",
            0f,
            (height - width - scaleBarPadding / 2) * 0.65f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 6 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 6 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "-20",
            0f,
            (height - width - scaleBarPadding / 2) * 0.76f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 7 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 7 + scaleBarPadding,
            scaleBarPaint
        )
        drawText(
            "-30",
            0f,
            (height - width - scaleBarPadding / 2) * 0.87f + scaleBarPadding,
            scaleBarPaint
        )
        drawLine(
            0f,
            (height - width - scaleBarPadding / 2) / 9 * 8 + scaleBarPadding,
            2 * scaleBarPadding,
            (height - width - scaleBarPadding / 2) / 9 * 8 + scaleBarPadding,
            scaleBarPaint
        )
    }

    private fun Canvas.drawCapillaryWithMercury() {
        drawRoundRect(
            mercuryRectangle,
            width - 6 * scaleBarPadding,
            width - 6 * scaleBarPadding,
            mercuryPaint
        )
        drawRect(
            levelRectangle,
            scaleBarPaint
        )
        drawRoundRect(
            capillaryRectangle,
            width - 5 * scaleBarPadding,
            width - 5 * scaleBarPadding,
            glassPaint
        )
    }

    private fun Canvas.drawFlaskWithMercury() {
        drawCircle(
            width / 2f,
            height - width / 2f,
            width / 2 - scaleBarPadding,
            mercuryPaint
        )
        drawRoundRect(
            capillaryRectangle,
            width - 5 * scaleBarPadding,
            width - 5 * scaleBarPadding,
            glassTopPaint
        )
        drawCircle(
            width / 2f,
            height - width / 2f,
            width / 2 - 0.5f * scaleBarPadding,
            glassRoundPaint
        )
        drawCircle(
            width / 2f,
            height - width / 2f,
            width / 2 - 0.5f * scaleBarPadding,
            glassEdgeRoundPaint
        )
    }
}
