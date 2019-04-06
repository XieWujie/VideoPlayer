package com.example.administrator.vidioplayer.viewModel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoPlayViewModel:ViewModel(){

    val source = MutableLiveData<String>()

}