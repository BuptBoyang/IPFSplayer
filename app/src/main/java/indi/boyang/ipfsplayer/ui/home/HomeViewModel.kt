package indi.boyang.ipfsplayer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.repository.PlaylistRepository

class HomeViewModel : ViewModel() {

    private val repo = PlaylistRepository()
    val videos:LiveData<List<Video>> = repo.videos
}