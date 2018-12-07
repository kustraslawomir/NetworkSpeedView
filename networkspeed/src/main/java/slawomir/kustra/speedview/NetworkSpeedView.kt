package slawomir.kustra.speedview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.graphics.SweepGradient
import slawomir.kustra.utils.Gradients
import slawomir.kustra.utils.Utils.INDICATOR_RADIUS_MARGIN
import slawomir.kustra.utils.Utils.LINE_RADIUS_MARGIN


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

    private val arcLine = Paint()
    private val indicatorLine = Paint()
    private val ovalBackgroundShape = RectF()

    private var lineSize = (resources.displayMetrics.density * 14).toInt()
    private var indicatorSize = (resources.displayMetrics.density * 25).toInt()

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

        arcLine.strokeWidth = 7f
        arcLine.strokeCap = Paint.Cap.ROUND

        indicatorLine.strokeWidth = 9f
        indicatorLine.strokeCap = Paint.Cap.ROUND
        indicatorLine.color = Color.WHITE
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
        drawLineOnCanvas(canvas)
        drawIndicatorLine(canvas, indicatorAngle, indicatorLine)
    }

    private fun drawIndicatorLine(canvas: Canvas, angle: Int, paint: Paint) {
        drawLine(canvas, angle, paint, indicatorSize, INDICATOR_RADIUS_MARGIN)
    }

    private fun drawLineOnCanvas(canvas: Canvas) {
        arcLine.strokeWidth = 7f
        canvas.save()
        var counter = 46
        for (angle in 0..360) {
            if ((angle in 0..110 && angle % 5 == 0) || (angle in 250..360 && angle % 5 == 0)) {
                Log.e("NSV:", "counter : $counter")
                arcLine.color = colors[counter]
                drawLine(canvas, angle, arcLine, lineSize, LINE_RADIUS_MARGIN)
                counter--
            }
        }
        canvas.restore()
    }

    private fun drawLine(
        canvas: Canvas,
        angle: Int,
        paint: Paint,
        lineSize: Int,
        radiusMargin: Int
    ) {

        Log.d("NSV: ", "draw indicator: $angle")

        val angleRadius = Math.toRadians(angle.toDouble())
        val startX = centerX + (radius + radiusMargin) * Math.sin(angleRadius)
        val startY = centerY - (radius + radiusMargin) * Math.cos(angleRadius)
        val stopX = centerX + ((radius + radiusMargin) - lineSize) * Math.sin(angleRadius)
        val stopY = centerY - ((radius + radiusMargin) - lineSize) * Math.cos(angleRadius)
        Log.e("NSV:", "draw line on angle: $angle, startX: $startX  startY: $startY  stopX: $stopX  stopY: $stopY")
        canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), paint)
    }

    fun refreshUi() {
        invalidate()
    }
}