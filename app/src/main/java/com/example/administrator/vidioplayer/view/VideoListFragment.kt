package com.example.administrator.vidioplayer.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.vidioplayer.R
import com.example.administrator.vidioplayer.adapter.VideoListAdapter
import com.example.administrator.vidioplayer.viewModel.VideoListViewModel

class VideoListFragment:Fragment(){

    private lateinit var model: VideoListViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter = VideoListAdapter();
    val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list,container,false)
        recyclerView =view.findViewById(R.id.recyclerview)
        model = ViewModelProviders.of(this)[VideoListViewModel::class.java]
        initUI()
        return view
    }

    fun initUI(){
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        model.videos.observe(this, Observer {
            handler.post {
                adapter.freshData(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}