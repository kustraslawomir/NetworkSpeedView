package slawomir.kustra.speedview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.graphics.SweepGradient
import android.os.Handler
import slawomir.kustra.utils.Gradients
import java.lang.Exception


class NetworkSpeedView : View {

    private var viewWidth = 0
    private var viewHeight = 0
    private var centerX = 0
    private var centerY = 0
    private var radius: Double = 0.0

    private var lineLeft = 0f
    private var lineTop = 0f
    private var lineRight = 0f
    private var lineBottom = 0f

    private val ovalPaint = Paint()

    private val arcLines = Paint()

    private val ovalBackgroundShape = RectF()

    private val scaleMarkSize = resources.displayMetrics.density * 14

    private val colors = Gradients.linesColor

    var indicatorAngle = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context, attributeSet,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        Log.d("NSV: ", "init")
        colors.reverse()

        ovalPaint.isAntiAlias = true
        ovalPaint.strokeWidth = 70f
        ovalPaint.style = Paint.Style.STROKE
        ovalPaint.strokeCap = Paint.Cap.ROUND

        arcLines.alpha = 0.6.toInt()
        arcLines.strokeWidth = 7f
        arcLines.strokeCap = Paint.Cap.ROUND
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w

        radius = viewWidth / 2.5
        centerX = viewWidth / 2
        centerY = viewHeight / 2

        lineLeft = centerX - radius.toFloat()
        lineTop = centerY - radius.toFloat()
        lineRight = centerX + radius.toFloat()
        lineBottom = centerY + radius.toFloat()

        val s2 = (Math.min(viewWidth, viewHeight) - ovalPaint.strokeWidth) / 2f

        val positions = floatArrayOf(0.0f, 0.75f)
        val shader = SweepGradient(centerX.toFloat(), centerY.toFloat(), Gradients.arcGradientColors, positions)
        val matrix = Matrix()
        matrix.setRotate(135f, centerX.toFloat(), centerY.toFloat())
        shader.setLocalMatrix(matrix)

        ovalPaint.shader = shader

        ovalBackgroundShape.set(centerX - s2, centerY - s2, centerX + s2, centerY + s2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.e("NSV: ", "onDraw called")
        canvas.drawArc(ovalBackgroundShape, 160f, 220f, false, ovalPaint)
        drawArcLines(canvas)
        drawIndicator(canvas, indicatorAngle, Color.BLACK)
    }

    private fun drawArcLines(canvas: Canvas) {
        canvas.save()
        var counter = 46
        for (angle in 0..360) {
            if ((angle in 0..110 && angle % 5 == 0) || (angle in 250..360 && angle % 5 == 0)) {
                Log.e("NSV:", "counter : $counter")
                drawIndicator(canvas, angle, colors[counter])
                counter--
            }
        }
        canvas.restore()
    }

    private fun drawIndicator(canvas: Canvas, angle: Int, color: Int) {
        try {
            arcLines.color = color
        } catch (e: Exception) {
            Log.e("NSV: ", "error ${e.message}")
        }

        Log.d("NSV: ", "draw indicator: $angle")

        val angleRadius = Math.toRadians(angle.toDouble())
        val startX = centerX + (radius + 65) * Math.sin(angleRadius)
        val startY = centerY - (radius + 65) * Math.cos(angleRadius)
        val stopX = centerX + ((radius + 65) - scaleMarkSize) * Math.sin(angleRadius)
        val stopY = centerY - ((radius + 65) - scaleMarkSize) * Math.cos(angleRadius)
        Log.e("NSV:", "draw line on angle: $angle, startX: $startX  startY: $startY  stopX: $stopX  stopY: $stopY")
        canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), arcLines)
    }

    fun refreshUi() {
        invalidate()
    }
}