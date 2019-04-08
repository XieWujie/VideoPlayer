package com.example.administrator.videoplayer.util

import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.example.administrator.videoplayer.event.IconCanSeeEvent
import com.example.administrator.videoplayer.event.IsPlayEvent
import com.example.administrator.videoplayer.event.OrientationChangeEvent
import com.example.administrator.videoplayer.event.SeekEvent
import org.greenrobot.eventbus.EventBus

class SensorListener(val isLand: Boolean) : SensorEventListener {

    private var isPlay = true
    private var isIconCanSee = false
    private var xx = 2
    private var yy = 1;

    init {
        if (isLand) {
            xx = 1
            yy = 2
        } else {
            xx = 2
            yy = 1
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        val x = values[xx]
        val y = values[yy]
        val absY = Math.abs(y)
        val absX = Math.abs(x)
        val bus = EventBus.getDefault()
        if (absX < 10) {
            if (absY > 60 && isPlay) {
                bus.post(IsPlayEvent(false))
                isPlay = false
                isIconCanSee = true
            } else if (absY <= 60 && !isPlay) {
                bus.post(IsPlayEvent(true))
                isPlay = true
                isIconCanSee = false
            }
            if (absY <= 60 && isIconCanSee) {
                bus.post(IconCanSeeEvent(false))
                isIconCanSee = false
            }
        } else if (absX >= 10 && absX < 60) {

            if (isReverse) {
                bus.post(SeekEvent(x > 0))
            }else{
                bus.post(SeekEvent(x<0))
            }
            isIconCanSee = true
        } else if (absX >= 60) {
            var orientation = Int.MIN_VALUE
            if (isLand) {
                if (x < 0) {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    isReverse = false
                } else {

                }
            } else {
                if (x > 0) {
                    isReverse = false
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    isReverse = true
                }
            }
            if (orientation != Int.MIN_VALUE)
                bus.post(OrientationChangeEvent(orientation))
        }
    }

    companion object {
        private var isReverse = false
    }
}