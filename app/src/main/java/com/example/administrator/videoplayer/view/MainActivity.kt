package com.example.administrator.videoplayer.view

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.administrator.videoplayer.R
import com.example.administrator.videoplayer.data.VideoBean
import com.example.administrator.videoplayer.event.OrientationChangeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: FrameLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(R.layout.activity_main)
        mainLayout = findViewById(R.id.main_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "CU Hill Town"
        EventBus.getDefault().register(this)
        initUI()
    }

    private fun initUI() {
        val isLand = isLand()
        val isPad = isPad()
        if (supportFragmentManager.backStackEntryCount == 0) {
            var fragment = if (isLand &&isPad){
                Fragment()
            }else{
                VideoListFragment()
            }
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_layout, fragment, "list")
                commit()
            }
        }else{
            if (isLand&&!isPad()){
                supportActionBar?.hide()
            }
        }
    }


    private fun isLand():Boolean{
        val r = resources.displayMetrics
        return r.widthPixels>r.heightPixels
    }

    private fun init(){
        if (!isPad() &&supportFragmentManager.backStackEntryCount == 0){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun orientationChange(orientation:OrientationChangeEvent){
        if (resources.configuration.orientation != orientation.orientation){
            requestedOrientation = orientation.orientation
        }
    }


    override fun onBackPressed() {
      super.onBackPressed()
        if (isPad()) {
            initUI()
        }else{
            if (isLand()){
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun nav(bean: VideoBean){
       supportFragmentManager.beginTransaction().apply {
           val fragment = VideoFragment()
           val bundle = Bundle()
           bundle.putString("url",bean.src)
           fragment.arguments = bundle
           replace(R.id.main_layout,fragment,bean.src)
           addToBackStack(null)
           commit()
       }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun isPad(): Boolean {
        return resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}
