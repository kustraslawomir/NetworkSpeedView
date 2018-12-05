package slawomir.kustra.speedview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.graphics.SweepGradient


internal class NetworkSpeedView : View {

    private var arcGradientColors =
        intArrayOf(Color.parseColor("#FFFF76BD"), Color.parseColor("#FF71F0F0"))

    private var linesColor =
        intArrayOf(Color.parseColor("#FFF17FBC"), Color.parseColor("#FFF17FBC"), Color.parseColor("#FF47C0E2"))

    private val ovalPaint = Paint()

    private val arcLines = Paint()

    private val ovalBackgroundShape = RectF()

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val s2 = (Math.min(w, h) - ovalPaint.strokeWidth) / 2f
        val cx = w / 2f
        val cy = h / 2f
        ovalBackgroundShape.set(cx - s2, cy - s2, cx + s2, cy + s2)
        val positions = floatArrayOf(0.0f, 0.75f)
        val shader = SweepGradient(cx, cy, arcGradientColors, positions)
        val m = Matrix()
        m.setRotate(135f, cx, cy)
        shader.setLocalMatrix(m)
        ovalPaint.shader = shader
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        ovalPaint.isAntiAlias = true
        ovalPaint.strokeWidth = 70f
        ovalPaint.style = Paint.Style.STROKE
        ovalPaint.strokeCap = Paint.Cap.ROUND

        arcLines.alpha = 0.6.toInt()
        arcLines.color =Color.parseColor("#65FFFFFF")
        arcLines.strokeWidth = 6f
        arcLines.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {

        val radius = width / 2.5
        val centerX = width / 2
        val centerY = height / 2

        val left = centerX - radius.toFloat()
        val top = centerY - radius.toFloat()
        val right = centerX + radius.toFloat()
        val bottom = centerY + radius.toFloat()

        val scaleMarkSize = resources.displayMetrics.density * 14

        Log.e("NSV", "width: $width height: $height")
        Log.e("NSV", "radius: $radius")
        Log.e("NSV", "centerX: $centerX | centerY: $centerY")
        Log.e("NSV", "left: $left | top: $top | right: $right | bottom: $bottom")
        canvas.drawArc(ovalBackgroundShape, 160f, 220f, false, ovalPaint)

        canvas.save()

        for (i in 0..360) {
            if ((i in 0..110 && i % 5 == 0) || (i in 250..360 && i % 5 == 0)) {

                if (i % 10 == 0) {
                    arcLines.strokeWidth = 4f
                }

                val angle = Math.toRadians(i.toDouble())
                val startX = centerX + (radius + 65) * Math.sin(angle)
                val startY = centerY - (radius + 65) * Math.cos(angle)
                val stopX = centerX + ((radius + 65) - scaleMarkSize) * Math.sin(angle)
                val stopY = centerY - ((radius + 65) - scaleMarkSize) * Math.cos(angle)
                Log.e("NSV:", "draw line on angle: $i, startX: $startX  startY: $startY  stopX: $stopX  stopY: $stopY")

                canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), arcLines)
            }
        }
        canvas.restore()
    }

    private fun init() {
        Log.d("NSV: ", "init")
    }
}