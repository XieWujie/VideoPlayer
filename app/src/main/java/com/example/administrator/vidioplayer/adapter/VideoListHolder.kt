package com.example.administrator.vidioplayer.adapter

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.vidioplayer.R
import com.example.administrator.vidioplayer.data.VideoBean
import com.example.administrator.vidioplayer.util.ImageUtil
import org.greenrobot.eventbus.EventBus


class VideoListHolder(private val view:View) : RecyclerView.ViewHolder(view) {

    private val image = view.findViewById<ImageView>(R.id.image)
    private val titleText = view.findViewById<TextView>(R.id.title)

    fun bind(bean: VideoBean) {
        titleText.text = bean.title
        ImageUtil.load(bean.src,image)
        view.setOnClickListener {
            EventBus.getDefault().post(bean)
        }
    }
}