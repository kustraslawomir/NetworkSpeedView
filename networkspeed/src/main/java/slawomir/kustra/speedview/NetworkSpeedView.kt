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

    private var viewWidth = 0f
    private var viewHeight = 0f

    val paint = Paint()

    private val ovalBackgroundShape = RectF()
    var sweep = 0f

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
        viewWidth = width.toFloat()
        viewHeight = height.toFloat()

        setBackgroundColor(Color.WHITE)

        val radius = width / 3
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
        canvas.drawArc(ovalBackgroundShape, 160f, 220f, false, paint)

    }

    private fun init() {
        Log.d("NSV: ", "init")

    }

}