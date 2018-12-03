package slawomir.kustra.speedview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View


internal class NetworkSpeedView : View {

    private lateinit var paint: Paint

    private lateinit var circleView: RectF

    private val startAnglePoint = 180f
    private val endAnglePoint = 180f

    private val circleStrokeWidth = 200f

    private val sweepValue = 2
    private var sweep = 0

    private var isAlive: Boolean = false


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

    override fun onDraw(canvas: Canvas) {
        Log.d("NSV onDraw: ", "width: $width height: $height")

        circleView.left = width / 2f - 100f
        circleView.top = height / 2f - 100
        circleView.bottom = width / 2f + 100f
        circleView.right = height  / 2f + 100f

        canvas.drawArc(circleView, startAnglePoint, sweep.toFloat(), true, paint)

        sweep += sweepValue

        if (sweep > 180) {
            isAlive = false
        }
        if (sweep > endAnglePoint) {
            isAlive = false
        }

        if (isAlive) {
            invalidate()
        }
    }

    private fun init() {
        Log.d("NSV: ", "init")
        circleView = RectF()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleStrokeWidth
        paint.color = Color.RED
    }

}