package hh.school.lesson_11_zemskov

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class BarChartView : View {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private val columnStartHeight by lazy { resources.getDimension(R.dimen.bar_chart_column_start_height) }
    private val columnWidth by lazy { resources.getDimension(R.dimen.bar_chart_column_width) }
    private val columnCornerRadius by lazy { resources.getDimension(R.dimen.bar_chart_column_corner_radius) }
    private val columnMarginTop by lazy { resources.getDimension(R.dimen.bar_chart_column_top_margin) }
    private val columnMarginBottom by lazy { resources.getDimension(R.dimen.bar_chart_column_bottom_margin) }
    private val textSize by lazy { resources.getDimension(R.dimen.bar_chart_text_size) }
    private var animatorSet: AnimatorSet? = null

    var data = emptyList<Pair<String, Int>>()
        set(value) {
            field = value.takeLast(9)
            animatorSet?.cancel()
            animatorSet = null
            columnsRectF = List(field.size) { RectF() }
            setPositionsAndSize(width, height)
            invalidate()
        }
    private val xPositions = mutableListOf<Float>()

    private var columnsRectF = emptyList<RectF>()
    private val columnPaint = Paint().apply {
        textSize = this@BarChartView.textSize
        textAlign = Paint.Align.CENTER
    }
    private val subtitleTextPaint = Paint().apply {
        textSize = this@BarChartView.textSize
        textAlign = Paint.Align.CENTER
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarChartView, 0, 0)
        try {
            columnPaint.color = typedArray.getColor(
                R.styleable.BarChartView_columnColor,
                ContextCompat.getColor(context, R.color.light_deep_yellow_300)
            )
            subtitleTextPaint.color = typedArray.getColor(
                R.styleable.BarChartView_subtitleTextColor,
                ContextCompat.getColor(context, R.color.grey_400)
            )
        } finally {
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setPositionsAndSize(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isNotEmpty()) {
            columnsRectF.forEach { rectF ->
                canvas.drawRoundRect(rectF, columnCornerRadius, columnCornerRadius, columnPaint)
            }
            data.forEachIndexed { index, (_, amount) ->
                canvas.drawText(
                    "$amount",
                    xPositions[index],
                    columnsRectF[index].top - columnMarginTop,
                    columnPaint
                )
            }
            data.forEachIndexed { index, (date, _) ->
                canvas.drawText(
                    date,
                    xPositions[index],
                    height - paddingBottom.toFloat(),
                    subtitleTextPaint
                )
            }
        }
    }

    fun startAnimation() {
        if (data.isNotEmpty()) {
            val maxHeightColumn =
                height - paddingBottom - paddingTop - 2 * textSize - columnMarginTop - columnMarginBottom
            val maxLevel = data.maxOf { (_, amount) -> amount }.toFloat()
            val animators = data.map { (_, amount) ->
                amount / maxLevel
            }.mapIndexed { index, relativeHeight ->
                columnsRectF[index].top =
                    height - paddingBottom - textSize - columnMarginBottom - columnStartHeight
                ValueAnimator.ofFloat(
                    columnStartHeight / maxHeightColumn,
                    relativeHeight
                ).apply {
                    startDelay = (100 * index).toLong()
                    addUpdateListener {
                        columnsRectF[index].top =
                            maxHeightColumn * (1 - it.animatedValue as Float) + paddingTop + textSize + columnMarginTop
                        invalidate()
                    }
                }
            }
            animatorSet = AnimatorSet().apply {
                duration = 1_000
                playTogether(animators)
            }
            animatorSet?.start()
        }
    }

    private fun setPositionsAndSize(w: Int, h: Int) {
        if (data.isNotEmpty()) {
            xPositions.clear()
            val spaceEvenly = w / (data.size + 1).toFloat()
            columnsRectF.forEachIndexed { index, rectF ->
                val xPosition = spaceEvenly * (index + 1)
                xPositions.add(xPosition)
                rectF.set(
                    xPosition - columnWidth / 2,
                    h - paddingBottom - textSize - columnMarginBottom - columnStartHeight,
                    xPosition + columnWidth / 2,
                    h - paddingBottom - textSize - columnMarginBottom
                )
            }
        }
    }
}
