package com.example.administrator.vidioplayer.view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.administrator.vidioplayer.R
import com.example.administrator.vidioplayer.event.IsPlayEvent
import com.example.administrator.vidioplayer.event.SeekEvent
import com.example.administrator.vidioplayer.util.SensorListener
import com.example.administrator.vidioplayer.viewModel.VideoPlayViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class VideoFragment:Fragment(){

    private lateinit var model:VideoPlayViewModel
    private lateinit var videoView:VideoView
    private lateinit var mediaController: MediaController
    private val sensorListener = SensorListener()
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_play,container,false)
        videoView = view.findViewById(R.id.video_view)
        model = ViewModelProviders.of(this)[VideoPlayViewModel::class.java]
        init()
        return view
    }
    fun init(){
        val src = arguments?.getString("url")
        model.source.value = src
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mediaController = MediaController(requireContext())
        videoView.setMediaController(mediaController)
        model.source.observe(this, Observer {
            if (it != null)
            videoView.setVideoURI(Uri.parse(it))
            videoView.start()
        })
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(sensorListener)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun isPlay(isPlayEvent: IsPlayEvent){
        if (isPlayEvent.isPlay){
            Log.d("event--","start")
            videoView.start()
        }else{
            Log.d("event--","pause")
            videoView.pause()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun seek(seekEvent: SeekEvent){
        if (seekEvent.isNext){
            Log.d("event--","next")
            videoView.seekTo(getNextTime())
        }else{
            Log.d("event--","previous")
            videoView.seekTo(getPreviousTime())
        }
    }

     fun getPreviousTime():Int{
         val time = videoView.currentPosition-1000
         return if (time>=0){
             time
         }else{
             0
         }
    }

    fun getNextTime():Int{
        val time = videoView.currentPosition+1000
        return if (time>=videoView.duration){
            videoView.duration
        }else{
            time
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}