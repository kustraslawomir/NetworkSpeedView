package slawomir.kustra.networkviewapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_network_speed.*

class NetworkSpeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_speed)
        runInterval()
    }

    private fun runInterval() {
        var angle = 0
        val handler = Handler()
        val postDelayed = handler.postDelayed(object : Runnable {
            override fun run() {
                networkSpeedView.indicatorAngle = angle
                networkSpeedView.refreshUi()
                angle += 5
                if (angle >= 245)
                    angle = 0
                handler.postDelayed(
                    this, 50
                )
            }
        }, 1000)
    }
}
