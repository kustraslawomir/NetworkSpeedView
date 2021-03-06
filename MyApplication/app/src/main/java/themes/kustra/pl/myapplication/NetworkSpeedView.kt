package themes.kustra.pl.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

internal class NetworkSpeedView : View {

    private var colors =
        intArrayOf(Color.parseColor("#ffb3ba"), Color.parseColor("#ffb3ba"), Color.parseColor("#bae1ff"))

    private var linesColor =
        intArrayOf(Color.parseColor("#FFF17FBC"), Color.parseColor("#FFF17FBC"), Color.parseColor("#FF47C0E2"))

    private var arcShader = SweepGradient(1080f, 1863f, colors, null)
    private var linesShader = SweepGradient(1080f, 1080f, linesColor, null)

    private val paint = Paint()

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas) {

        paint.isAntiAlias = true
        paint.strokeWidth = 85f
        paint.style = Paint.Style.STROKE

        arcLines.color = Color.BLACK

        val radius = width / 2.5
        val centerX = width / 2
        val centerY = height / 2

        val left = centerX - radius.toFloat()
        val top = centerY - radius.toFloat()
        val right = centerX + radius.toFloat()
        val bottom = centerY + radius.toFloat()

        val scaleMarkSize = resources.displayMetrics.density * 16

        Log.e("NSV", "width: $width height: $height")
        Log.e("NSV", "radius: $radius")
        Log.e("NSV", "centerX: $centerX | centerY: $centerY")
        Log.e("NSV", "left: $left | top: $top | right: $right | bottom: $bottom")
        ovalBackgroundShape.set(left, top, right, bottom)
        paint.shader = arcShader
        canvas.drawArc(ovalBackgroundShape, 140f, 260f, false, paint)

        canvas.save()
        arcLines.strokeWidth = 7f
        arcLines.strokeCap = Paint.Cap.ROUND
        arcLines.isAntiAlias = true

        for (i in 0..360) {
            if ((i in 0..125 && i % 5 == 0) || (i in 235..360 && i % 5 == 0)) {
                val angle = Math.toRadians(i.toDouble())
                val startX = centerX + radius * Math.sin(angle)
                val startY = centerY - radius * Math.cos(angle)
                val stopX = centerX - 20 + (radius - scaleMarkSize) * Math.sin(angle)
                val stopY = centerY - 20 - (radius - scaleMarkSize) * Math.cos(angle)
                Log.e("NSV:", "draw line on angle: $i")

                canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), arcLines)
            }
        }
        canvas.restore()
    }

    private fun init() {
        Log.d("NSV: ", "init")
    }
}