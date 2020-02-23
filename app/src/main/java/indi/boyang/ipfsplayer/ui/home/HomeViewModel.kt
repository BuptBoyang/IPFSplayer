package indi.boyang.ipfsplayer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import indi.boyang.ipfsplayer.api.ApiResponse
import indi.boyang.ipfsplayer.api.ApiSuccessResponse
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.repository.PlaylistRepository

class HomeViewModel : ViewModel() {

    private val repo = PlaylistRepository()
    val videos:LiveData<List<Video>> = Transformations.map(repo.videos) { videos -> (videos as ApiSuccessResponse<List<Video>>).body }
}