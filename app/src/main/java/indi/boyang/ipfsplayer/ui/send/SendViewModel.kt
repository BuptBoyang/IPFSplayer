package indi.boyang.ipfsplayer.ui.send

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import indi.boyang.ipfsplayer.api.ApiSuccessResponse
import indi.boyang.ipfsplayer.api.MyService
import indi.boyang.ipfsplayer.models.Video
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SendViewModel(title: String, picUri: Uri, videoUri: Uri) : ViewModel() {
    private val picFile = uriToMultipart(picUri, "pic", "image/*")
    private val videoFile = uriToMultipart(videoUri, "video", "video/*")

    private val repo = MyService.create().uploadVideo(title, picFile, videoFile)

    val video: LiveData<Video> = Transformations.map(repo) {video ->
        (video as ApiSuccessResponse<Video>).body}

    private fun uriToMultipart(uri: Uri, name: String, type: String): MultipartBody.Part {
        val file = File(uri.path)
        val requestFile =
            file.asRequestBody(type.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }
}