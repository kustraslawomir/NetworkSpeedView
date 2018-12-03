package slawomir.kustra.speedview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

internal class NetworkSpeedView : View {

    private var colors = intArrayOf(Color.parseColor("#FFD0E9"),Color.parseColor("#FFD0E9"),   Color.parseColor("#9AE9FF"))
    private var shader = SweepGradient(1080f, 1080f, colors, null)

    private val paint = Paint()

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


    override fun onDraw(canvas: Canvas) {

        val radius = width / 2.5
        val centerX = width / 2
        val centerY = height / 2

        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.strokeWidth = 50f
        paint.style = Paint.Style.STROKE

        val left = centerX - radius.toFloat()
        val top = centerY - radius.toFloat()
        val right = centerX + radius.toFloat()
        val bottom = centerY + radius.toFloat()


        ovalBackgroundShape.set(left, top, right, bottom)
        paint.shader = shader
        canvas.drawArc(ovalBackgroundShape, 140f, 260f, false, paint)

    }

    private fun init() {
        Log.d("NSV: ", "init")
    }
}