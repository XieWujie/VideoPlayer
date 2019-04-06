package com.example.administrator.vidioplayer.util

import android.graphics.Bitmap
import java.util.concurrent.Executors

@Suppress("propertyName")
private val EXECUTOR = Executors.newSingleThreadExecutor()

fun runOnNewThread(f:()->Unit){
    EXECUTOR.submit(f)
}
