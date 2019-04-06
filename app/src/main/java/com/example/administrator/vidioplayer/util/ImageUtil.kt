package com.example.administrator.vidioplayer.util

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Handler
import android.os.Looper
import android.widget.ImageView


object ImageUtil{

    val imageSaveMap = HashMap<String, Bitmap?>()
    val handler = Handler(Looper.getMainLooper())

    fun load(url:String,imageView: ImageView){
        if (imageSaveMap.containsKey(url)){
            imageView.setImageBitmap(imageSaveMap[url])
        }else {
            runOnNewThread {
                val bitmap = createVideoThumbnail(url)
                imageSaveMap[url] = bitmap
                handler.post {
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }

    fun createVideoThumbnail(filePath: String): Bitmap? {
        var bitmap: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(filePath, emptyMap())
            bitmap = retriever.getFrameAtTime(-1)
        } finally {
            try {
                retriever.release()
            } catch (ex: RuntimeException) {
                // Ignore failures while cleaning up.
            }

        }
        if (bitmap == null) return null

        // Scale down the bitmap if it's too large.
        val width = bitmap.width
        val height = bitmap.height
        val max = Math.max(width, height)
        if (max > 512) {
            val scale = 512f / max
            val w = Math.round(scale * width)
            val h = Math.round(scale * height)
            bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true)
        }
        return bitmap
    }
}