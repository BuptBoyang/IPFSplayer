package indi.boyang.ipfsplayer.ui.videoui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import indi.boyang.ipfsplayer.api.ApiSuccessResponse
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.repository.VideoRepository

class VideoViewModel(_id: Long): ViewModel() {
    private val repo = VideoRepository(_id)
    val id: LiveData<Long> = Transformations.map(repo.video) { video -> (video as ApiSuccessResponse<Video>).body.id }
    val videoUrl:LiveData<String> = Transformations.map(repo.video) { video -> (video as ApiSuccessResponse<Video>).body.videoURL }
    val picUrl:LiveData<String> = Transformations.map(repo.video) { video -> (video as ApiSuccessResponse<Video>).body.picURL }
    val title:LiveData<String> = Transformations.map(repo.video) { video -> (video as ApiSuccessResponse<Video>).body.title  }
}