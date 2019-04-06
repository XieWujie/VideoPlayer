package com.example.administrator.vidioplayer.util

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.example.administrator.vidioplayer.event.IsPlayEvent
import com.example.administrator.vidioplayer.event.SeekEvent
import org.greenrobot.eventbus.EventBus

class SensorListener:SensorEventListener{

    private var isPlay = true
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        val x = values[2]
        val y = Math.abs(values[1])
        val bus = EventBus.getDefault()
        if (Math.abs(x)<10){
            if (y>60&&isPlay){
                bus.post(IsPlayEvent(false))
                isPlay = false
            }else if (y<=60 &&!isPlay){
                bus.post(IsPlayEvent(true))
                isPlay = true
            }
        }else{
            bus.post(SeekEvent(x<0))
        }
    }

}