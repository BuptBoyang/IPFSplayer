package indi.boyang.ipfsplayer.util

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import indi.boyang.ipfsplayer.ui.send.SendViewModel
import indi.boyang.ipfsplayer.ui.videoui.VideoViewModel
import okhttp3.RequestBody

class VideoViewModelFactory(private val id: Long):
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = VideoViewModel(id) as T
}

class SendViewModelFactory(private val title: String,
                           private val picUri: Uri,
                           private val videoUri: Uri):
        ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T
            = SendViewModel(title, picUri, videoUri) as T
}