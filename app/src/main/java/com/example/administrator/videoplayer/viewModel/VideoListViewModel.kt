package com.example.administrator.videoplayer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.administrator.videoplayer.data.VideoBean
import com.example.administrator.videoplayer.data.VideoRequest

class VideoListViewModel:ViewModel(){

    private val request = VideoRequest()

    val videos = MutableLiveData<List<VideoBean>>().apply {
        value = request.getVideo()
    }
}