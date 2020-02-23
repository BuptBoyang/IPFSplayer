package indi.boyang.ipfsplayer.ui.send

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class SendFragment : Fragment() {

    private val PHOTO_REQUEST_CODE = 1
    private val VIDEO_REQUEST_CODE = 2
    private var coverUri: Uri? = null
    private var videoUri: Uri? = null
    private lateinit var coverPath:String
    private lateinit var videoPath:String

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
            pickVideoIntent.type = "video/mp4"
            startActivityForResult(pickVideoIntent,VIDEO_REQUEST_CODE)
        }

        buttonUpload.setOnClickListener{

            if(coverUri!=null && videoUri!=null){
                MyService.create2().uploadVideo(
                    editTitle.text.toString(),
                    pathToMultipart(coverPath,"pic"),
                    pathToMultipart(videoPath,"video")
                ).enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        uploadResultView.text = "finish"
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        uploadResultView.text = t.message
                    }
                })
            } else {
                uploadResultView.text = "Please select cover and video file"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            PHOTO_REQUEST_CODE -> if (data != null) {
                coverUri = data.data
                val resolver: ContentResolver = this.context!!.contentResolver
                val cursor: Cursor = resolver.query(coverUri, null, null, null, null) ?: return
                if (cursor.moveToFirst()) {
                    coverPath = cursor.getString(cursor.getColumnIndex("_data"))
                }
                cursor.close()
                coverFileName.text = coverPath
            }
            VIDEO_REQUEST_CODE -> if (data != null) {
                videoUri = data.data
                val resolver: ContentResolver = this.context!!.contentResolver
                val cursor: Cursor = resolver.query(videoUri, null, null, null, null) ?: return
                if (cursor.moveToFirst()) {
                    videoPath = cursor.getString(cursor.getColumnIndex("_data"))
                }
                cursor.close()
                videoFileName.text = videoPath
            }
        }
    }

    private fun pathToMultipart(path:String,name:String): MultipartBody.Part {
        val file = File(path)
        val requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

    private fun uriToMultipart(uri: Uri,name:String): MultipartBody.Part {
        val file = File(uri.path)
        val requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

}

