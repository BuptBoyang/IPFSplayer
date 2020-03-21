package indi.boyang.ipfsplayer.ui.send

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import indi.boyang.ipfsplayer.R
import indi.boyang.ipfsplayer.api.MyService
import kotlinx.android.synthetic.main.fragment_send.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.lang.Exception


class SendFragment : Fragment() {

    private val PHOTO_REQUEST_CODE = 1
    private val VIDEO_REQUEST_CODE = 2
    private lateinit var coverPath:String
    private lateinit var videoPath:String
    private var fileFlag = BooleanArray(2) {false}

    private lateinit var sendViewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProvider(this).get(SendViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_send, container, false)
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

            if(fileFlag[0]&&fileFlag[1]){
                val text = "Uploading..."
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                MyService.create2().uploadVideo(
                    editTitle.text.toString(),
                    pathToMultipart(coverPath,"pic"),
                    pathToMultipart(videoPath,"video")
                ).enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        uploadResultView.text = getString(R.string.upload_success)
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        uploadResultView.text = t.message
                    }
                })
            } else {
                uploadResultView.text = getString(R.string.null_file)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            PHOTO_REQUEST_CODE -> if (data != null) {
                coverPath = uriToPath(data,PHOTO_REQUEST_CODE)
                coverFileName.text = coverPath
            }
            VIDEO_REQUEST_CODE -> if (data != null) {
                videoPath = uriToPath(data,VIDEO_REQUEST_CODE)
                videoFileName.text = videoPath
            }
        }
    }

    private fun uriToPath(data: Intent,type:Int):String{
        var resultPath = "no file"
        val resolver: ContentResolver = this.context!!.contentResolver
        try {
            val cursor: Cursor = resolver.query(data.data!!, null, null, null, null) ?: return resultPath
            if (cursor.moveToFirst()) {
                resultPath = cursor.getString(cursor.getColumnIndex("_data"))
                fileFlag[type-1] = true
            }
            cursor.close()
        }catch (e:Exception){
            resultPath = e.toString()
        } finally {
            return resultPath
        }
    }

    private fun pathToMultipart(path:String,name:String): MultipartBody.Part {
        val file = File(path)
        val requestFile =
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

}

