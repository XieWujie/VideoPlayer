package com.example.administrator.vidioplayer.view

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.administrator.vidioplayer.R
import com.example.administrator.vidioplayer.data.VideoBean
import com.example.administrator.vidioplayer.viewModel.MainViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
import android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK



class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var model:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.main_fragment)
        model = ViewModelProviders.of(this)[MainViewModel::class.java]
        EventBus.getDefault().register(this)
    }

    private fun init(){
        if (isPad()){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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
