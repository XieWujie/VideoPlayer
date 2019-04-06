package com.example.administrator.vidioplayer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.administrator.vidioplayer.data.VideoBean
import com.example.administrator.vidioplayer.data.VideoRequest

class VideoListViewModel:ViewModel(){

    private val request = VideoRequest()

    val videos = MutableLiveData<List<VideoBean>>().apply {
        value = request.getVideo()
    }
}