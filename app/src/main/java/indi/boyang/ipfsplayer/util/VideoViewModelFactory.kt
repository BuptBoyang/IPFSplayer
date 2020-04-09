package indi.boyang.ipfsplayer.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import indi.boyang.ipfsplayer.ui.videoui.VideoViewModel

class VideoViewModelFactory(private  val id: Long,private val operator: String):
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = VideoViewModel(id,operator) as T
}

