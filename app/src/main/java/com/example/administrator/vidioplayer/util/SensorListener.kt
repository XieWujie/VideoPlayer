package com.example.administrator.vidioplayer.util

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.example.administrator.vidioplayer.event.IconCanSeeEvent
import com.example.administrator.vidioplayer.event.IsPlayEvent
import com.example.administrator.vidioplayer.event.OrientationChangeEvent
import com.example.administrator.vidioplayer.event.SeekEvent
import org.greenrobot.eventbus.EventBus

class SensorListener:SensorEventListener{

    private var isPlay = true
    private var isSensor = false
    private var isIconCanSee = false
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        val x = values[2]
        val y = Math.abs(values[1])
        val bus = EventBus.getDefault()
        val absX = Math.abs(x)
        if (absX<10){
            if (y>60&&isPlay){
                bus.post(IsPlayEvent(false))
                isPlay = false
                isIconCanSee = true
            }else if (y<=60 &&!isPlay){
                bus.post(IsPlayEvent(true))
                isPlay = true
                isIconCanSee = false
            }
            if (y<=60 && isIconCanSee){
                bus.post(IconCanSeeEvent(false))
                isIconCanSee = false
            }
        }else if (absX>10&&absX<60){

            bus.post(SeekEvent(x<0))
            isIconCanSee = true
        }else{
            if (!isSensor) {
                bus.post(OrientationChangeEvent(true))
                isSensor = true
            }
        }
    }

}