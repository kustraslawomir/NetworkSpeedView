package slawomir.kustra.container

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import slawomir.kustra.speedview.NetworkSpeedView

class NetworkSpeedContainer(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val networkSpeedView: NetworkSpeedView = NetworkSpeedView(context, attrs)

    init {
        addView(networkSpeedView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}