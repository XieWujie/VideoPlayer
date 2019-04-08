package com.example.administrator.videoplayer.util

import java.util.concurrent.Executors

@Suppress("propertyName")
private val EXECUTOR = Executors.newSingleThreadExecutor()

fun runOnNewThread(f:()->Unit){
    EXECUTOR.submit(f)
}
