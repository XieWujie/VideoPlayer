package com.example.administrator.videoplayer.view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.administrator.videoplayer.R
import com.example.administrator.videoplayer.event.IconCanSeeEvent
import com.example.administrator.videoplayer.event.IsPlayEvent
import com.example.administrator.videoplayer.event.SeekEvent
import com.example.administrator.videoplayer.util.SensorListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class VideoFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var sensorListener:SensorListener
    private lateinit var sensorManager: SensorManager
    private lateinit var icon_state: ImageView
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_play, container, false)
        videoView = view.findViewById(R.id.video_view)
        icon_state = view.findViewById(R.id.state_icon)
        position = savedInstanceState?.getInt("position") ?: 0
        init()
        return view
    }

    fun init() {
        val url = arguments?.getString("url")
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mediaController = MediaController(requireContext())
        videoView.setMediaController(mediaController)
        if (url != null)
            videoView.setVideoURI(Uri.parse(url))
        videoView.setOnPreparedListener {
            it.seekTo(position)
            it.start()
        }
        sensorListener = SensorListener(isLand())
    }

    private fun isLand():Boolean{
        val r = resources.displayMetrics
        return r.widthPixels>r.heightPixels
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        position = videoView.currentPosition - 1000
        outState.putInt("position", if (position > 0) position else 0)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(sensorListener)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun isPlay(isPlayEvent: IsPlayEvent) {
        if (isPlayEvent.isPlay) {
            icon_state.visibility = View.INVISIBLE
            videoView.start()
        } else {
            icon_state.visibility = View.VISIBLE
            icon_state.setImageLevel(3)
            videoView.pause()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun seek(seekEvent: SeekEvent) {
        icon_state.visibility = View.VISIBLE
        val seekPosition = if (seekEvent.isNext) {
            icon_state.setImageLevel(2)
            getNextTime()
        } else {
            icon_state.setImageLevel(1)
            getPreviousTime()
        }
        videoView.seekTo(seekPosition)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun iconCanSee(iconCanSeeEvent: IconCanSeeEvent) {
        if (!iconCanSeeEvent.isCanSee) {
            icon_state.visibility = View.INVISIBLE
        }
    }

    fun getPreviousTime(): Int {
        val time = videoView.currentPosition - 1000
        return if (time >= 0) {
            time
        } else {
            0
        }
    }

    fun getNextTime(): Int {
        val time = videoView.currentPosition + 1000
        return if (time >= videoView.duration) {
            videoView.duration
        } else {
            time
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}