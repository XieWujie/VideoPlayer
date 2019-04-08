package com.example.administrator.videoplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.videoplayer.R
import com.example.administrator.videoplayer.data.VideoBean

class VideoListAdapter:RecyclerView.Adapter<VideoListHolder>(){

    private val mList = ArrayList<VideoBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_list_item,parent,false)
        return VideoListHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: VideoListHolder, position: Int) {
        val bean = mList[position]
        holder.bind(bean)
    }

    fun freshData(list:List<VideoBean>){
        mList.clear()
        mList.addAll(list)
    }

}