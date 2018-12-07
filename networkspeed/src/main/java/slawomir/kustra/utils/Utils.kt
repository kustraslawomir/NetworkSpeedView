package slawomir.kustra.utils

import android.content.Context

object Utils {

    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    const val LINE_RADIUS_MARGIN = 65
    const val INDICATOR_RADIUS_MARGIN = 80
}
