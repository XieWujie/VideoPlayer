package com.example.administrator.vidioplayer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.administrator.vidioplayer.data.VideoBean

class MainViewModel:ViewModel(){

    val currentBean = MutableLiveData<VideoBean>()
}