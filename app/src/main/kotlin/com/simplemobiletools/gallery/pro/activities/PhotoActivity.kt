package com.simplemobiletools.gallery.pro.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.media3.common.util.UnstableApi

@SuppressLint("NewApi")
@UnstableApi
class PhotoActivity : PhotoVideoActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        mIsVideo = false
        super.onCreate(savedInstanceState)
    }
}
