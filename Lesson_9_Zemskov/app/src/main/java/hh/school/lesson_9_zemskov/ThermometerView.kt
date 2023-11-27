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

    var mercuryLevel = 0f
        set(value) {
            field = value + 50f
            invalidate()
        }

    private val basePaint = Paint().apply { color = Color.GRAY }
    private val baseRectangle = RectF()
    private val basePadding = 10f

    private val mercuryPaint = Paint()
    private val mercuryRectangle = RectF()

    private val flaskRectangle = RectF()
    private val glassPaint = Paint()
    private val glassTopPaint = Paint()
    private val glassRoundPaint = Paint()
    private val glassEdgeRoundPaint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        baseRectangle.set(
            width / 4f + basePadding / 2f,
            basePadding,
            width * 3f / 4f - basePadding / 2f,
            height - width / 2f
        )

        mercuryRectangle.set(
            width / 4f + basePadding * 3f / 2f,
            (height - 2f * basePadding) * (100f - mercuryLevel) / 100f,
            width * 3f / 4f - basePadding * 3f / 2f,
            height - 2f * basePadding
        )
        mercuryPaint.shader = LinearGradient(
            0f,
            2f * basePadding,
            0f,
            height - 2f * basePadding,
            intArrayOf(Color.RED, Color.BLUE, Color.CYAN),
            floatArrayOf(0.35f, 0.6f, 1f),
            Shader.TileMode.MIRROR
        )

        flaskRectangle.set(
            width / 4.0f + basePadding,
            (2 * basePadding),
            width * 3 / 4.0f - basePadding,
            (height - 2 * basePadding)
        )
        glassPaint.shader = LinearGradient(
            width / 4.0f + basePadding,
            0f,
            width * 3 / 4.0f - basePadding,
            0f,
            intArrayOf(
                Color.WHITE, Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT
            ),
            floatArrayOf(0f, 0.5f, 0.75f, 1f),
            Shader.TileMode.MIRROR
        )
        glassTopPaint.shader = RadialGradient(
            width / 2.0f,
            (2 * basePadding),
            width / 4.0f - basePadding,
            Color.WHITE,
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        glassRoundPaint.shader = RadialGradient(
            width / 2.0f + basePadding,
            height - width / 2.0f - basePadding,
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
        canvas.drawScale()
        canvas.drawMercury()
        canvas.drawFlask()
    }

    private fun Canvas.drawScale() {
        drawCircle(
            width / 2f,
            height - width / 2f,
            width / 2f - basePadding,
            basePaint
        )
        drawRoundRect(
            baseRectangle,
            width / 4f - basePadding / 2f,
            width / 4f - basePadding / 2f,
            basePaint
        )
        drawText("40", basePadding, height * 0.09f, basePaint)
        drawLine(
            basePadding,
            height * 0.1f,
            (basePadding * 3),
            height * 0.1f,
            basePaint
        )
        drawText("30", basePadding, height * 0.19f, basePaint)
        drawLine(
            basePadding,
            height * 0.2f,
            (basePadding * 3),
            height * 0.2f,
            basePaint
        )
        drawText("20", basePadding, height * 0.29f, basePaint)
        drawLine(
            basePadding,
            height * 0.3f,
            (basePadding * 3),
            height * 0.3f,
            basePaint
        )
        drawText("10", basePadding, height * 0.39f, basePaint)
        drawLine(
            basePadding,
            height * 0.4f,
            (basePadding * 3),
            height * 0.4f,
            basePaint
        )
        drawText("0", basePadding, height * 0.49f, basePaint)
        drawLine(
            basePadding,
            height * 0.5f,
            (basePadding * 3),
            height * 0.5f,
            basePaint
        )
        drawText("-10", basePadding / 2.0f, height * 0.59f, basePaint)
        drawLine(
            basePadding,
            height * 0.6f,
            (basePadding * 3),
            height * 0.6f,
            basePaint
        )
        drawText("-20", basePadding / 2.0f, height * 0.69f, basePaint)
        drawLine(
            basePadding,
            height * 0.7f,
            (basePadding * 3),
            height * 0.7f,
            basePaint
        )
        drawText("-30", basePadding / 2.0f, height * 0.79f, basePaint)
        drawLine(
            basePadding,
            height * 0.8f,
            (basePadding * 3),
            height * 0.8f,
            basePaint
        )
    }

    private fun Canvas.drawMercury() {
        drawRect(
            width / 4f + basePadding * 3f / 2f,
            (height - 2f * basePadding) * (100f - mercuryLevel) / 100f,
            width * 3f / 4f - basePadding * 3f / 2f,
            height - 2f * basePadding,
            mercuryPaint
        )
        drawRoundRect(
            flaskRectangle,
            width / 4.0f - basePadding,
            width / 4.0f - basePadding,
            glassPaint
        )
        drawCircle(
            width / 2f,
            height - width / 2f,
            width / 2f - 2 * basePadding,
            mercuryPaint
        )
    }

    private fun Canvas.drawFlask() {
        drawRoundRect(
            flaskRectangle,
            width / 4.0f - basePadding,
            width / 4.0f - basePadding,
            glassTopPaint
        )
        drawCircle(
            width / 2.0f,
            height - width / 2.0f,
            width / 2.0f - 1.5f * basePadding,
            glassRoundPaint
        )
        drawCircle(
            width / 2.0f,
            height - width / 2.0f,
            width / 2.0f - 1.5f * basePadding,
            glassEdgeRoundPaint
        )
    }
}
