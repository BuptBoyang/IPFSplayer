package indi.boyang.ipfsplayer.ui.videoui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.repository.VideoRepository

class VideoViewModel(_id: Long,operator:String): ViewModel() {
    val repo = VideoRepository(_id,operator)
    val id: LiveData<Long> = Transformations.map(repo.video){ video -> video.id}
    val videoUrl:LiveData<String> = Transformations.map(repo.video) { video -> video.videoURL}
    val picUrl:LiveData<String> = Transformations.map(repo.video){video -> video.picURL}
    val title:LiveData<String> = Transformations.map(repo.video){video -> video.title  }
}