package com.example.administrator.vidioplayer.view

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.administrator.vidioplayer.R
import com.example.administrator.vidioplayer.data.VideoBean
import com.example.administrator.vidioplayer.event.OrientationChangeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var isLandScape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.main_fragment)
        EventBus.getDefault().register(this)
    }

    private fun init(){
        if (isPad()){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        if (navController.navigateUp()){
            return true
        }else{
            return super.onSupportNavigateUp()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        isLandScape = newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if ((!isPad()) && isLandScape){
            supportActionBar?.hide()
        }
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    fun orientationChange(orientation:OrientationChangeEvent){
        if (orientation.isSensor) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    fun nav(bean: VideoBean){
        navController.navigate(R.id.videoFragment,Bundle().apply { putString("url",bean.src) })
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun isPad(): Boolean {
        return resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}
