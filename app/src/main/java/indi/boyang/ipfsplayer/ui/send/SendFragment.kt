package indi.boyang.ipfsplayer.ui.send

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import indi.boyang.ipfsplayer.api.MyService
import kotlinx.android.synthetic.main.fragment_send.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class SendFragment : Fragment() {

    private val PHOTO_REQUEST_CODE = 1
    private val VIDEO_REQUEST_CODE = 2
    private var coverUri: Uri? = null
    private var videoUri: Uri? = null

    private lateinit var sendViewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProvider(this).get(SendViewModel::class.java)
        val root = inflater.inflate(indi.boyang.ipfsplayer.R.layout.fragment_send, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonChooseCover.setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK)
            pickPhotoIntent.type = "image/*"
            startActivityForResult(pickPhotoIntent,PHOTO_REQUEST_CODE)
        }

        buttonChooseVideo.setOnClickListener {
            val pickVideoIntent = Intent(Intent.ACTION_PICK)
            pickVideoIntent.type = "video/*"
            startActivityForResult(pickVideoIntent,VIDEO_REQUEST_CODE)
        }

        buttonUpload.setOnClickListener{
            if(coverUri!=null && videoUri!=null){
                MyService.create().uploadVideo(
                    editTitle.text.toString(),
                    uriToMultipart(coverUri!!),
                    uriToMultipart(videoUri!!)
                )
                uploadResultView.text = "finish"
                //println(editTitle.text.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            PHOTO_REQUEST_CODE -> if (data != null) {
                coverUri = data.data
                coverFileName.text = coverUri.toString()
            }
            VIDEO_REQUEST_CODE -> if (data != null) {
                videoUri = data.data
                videoFileName.text = videoUri.toString()
            }
        }
    }

    private fun uriToMultipart(uri: Uri): MultipartBody.Part {
        val file = File(uri.path)
        val requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }


}